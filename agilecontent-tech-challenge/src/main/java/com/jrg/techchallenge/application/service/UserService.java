package com.jrg.techchallenge.application.service;

import com.jrg.techchallenge.application.generator.RandomUser;
import com.jrg.techchallenge.application.repository.UserRepository;
import com.jrg.techchallenge.domain.User;
import com.jrg.techchallenge.infrastructure.rest.spring.exception.UserAlreadyExistsException;
import com.jrg.techchallenge.infrastructure.rest.spring.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service for managing user-related operations.
 *
 * @author javier.rg@protonmail.com
 */
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final RandomUser randomUser;

  /**
   * Retrieves a user by his id.
   *
   * @return the user by id.
   */
  public User getById(String id) {
    return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
  }

  /**
   * Retrieves a list of all users.
   *
   * @return List of users.
   */
  public List<User> getAllUsers() {
    return userRepository.findAll().orElseThrow(UserNotFoundException::new);
  }

  /**
   * Creates a new user.
   *
   * @param user The user to be created.
   * @return The created user.
   */
  public User createUser(User user) {
    return (User) userRepository.findById(user.getUsername())
      .map(existingUser -> {
        throw new UserAlreadyExistsException();
      })
      .orElseGet(() -> userRepository.save(user));
  }

  /**
   * Creates a list of users.
   *
   * @param users The users to be created.
   * @return The created users.
   */
  public List<User> createUsers(List<User> users) {
    return userRepository.save(users);
  }

  /**
   * Updates the information of an existing user.
   *
   * @param userToUpdate The user with updated information.
   * @return The updated user.
   */
  public User updateUser(String userName, User userToUpdate) {
    return userRepository.findById(userName)
      .map(user -> userRepository.save(userToUpdate))
      .orElseThrow(UserNotFoundException::new);
  }

  /**
   * Deletes a user by id.
   *
   * @param id The id of the user to be deleted.
   */
  public void deleteById(String id) {
    userRepository.findById(id)
      .ifPresentOrElse(
        user -> userRepository.deleteById(id),
        () -> {
          throw new UserNotFoundException();
        });
  }

  /**
   * Generate a number, provided as a parameter, of random users using Random User Generator service.
   * Users will be added to the collection of existing users
   *
   * @param numberOfUsersToCreate The number of users to create.
   */
  public List<User> generateRandomUsersAsync(int numberOfUsersToCreate) {
    return (List<User>) randomUser
      .generateRandomUserAsync(numberOfUsersToCreate)
      .flatMap(usersMap -> {
          if (usersMap.isEmpty()) {
            return Mono.just(Collections.emptyList());
          }
          updateUserNamesToAvoidRepeatedUserNames(usersMap, usersMap.keySet());
          List<User> usersToSave = new ArrayList<>(usersMap.values());
          userRepository.save(usersToSave);
          return Mono.just(usersToSave);
        }
      ).blockOptional()
      .orElse(Collections.emptyList());
  }

  private void updateUserNamesToAvoidRepeatedUserNames(Map<String, User> retrievedUsers, Set<String> userNameToTest) {
    int iterator = 1;
    while (!userNameToTest.isEmpty()) {
      userNameToTest = userRepository.userNameExistInDatabase(userNameToTest);
      if (!userNameToTest.isEmpty()) {
        int finalIterator = iterator;
        userNameToTest.forEach(username -> {
          User user = retrievedUsers.get(username);
          retrievedUsers.remove(username);
          user.setUsername(username + finalIterator);
          retrievedUsers.put(user.getUsername(), user);
        });
        userNameToTest = retrievedUsers.keySet();
      }
      iterator++;
    }
  }

  /**
   * Return a tree with the users grouped by country, state and city
   *
   * @return the users.
   */
  public Map<String, Map<String, Map<String, List<User>>>> getUsersAsTree() {
    return userRepository.findAll()
      .orElseThrow(UserNotFoundException::new)
      .stream()
      .collect(Collectors.groupingBy(User::getCountry,
        Collectors.groupingBy(User::getState,
          Collectors.groupingBy(User::getCity))));
  }


  public List<User> getUsersPaged(int pageNumber, int pageSize) {
    return userRepository.findPaged(pageNumber, pageSize).orElseThrow(UserNotFoundException::new);
  }
}