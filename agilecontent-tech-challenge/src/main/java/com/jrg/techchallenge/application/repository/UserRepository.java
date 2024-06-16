package com.jrg.techchallenge.application.repository;

import com.jrg.techchallenge.domain.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Repository for managing user data.
 *
 * @author javier.rg@protonmail.com
 */
public interface UserRepository {

  /**
   * Retrieves a user by id.
   *
   * @return the user.
   */
  Optional<User> findById(String id);

  /**
   * Retrieves a list of all users.
   *
   * @return List of users.
   */
  Optional<List<User>> findAll();

  /**
   * Saves a user.
   *
   * @param user The user to be saved.
   * @return The saved user.
   */
  User save(User user);

  /**
   * Saves a list of users.
   *
   * @param user The users to be saved.
   * @return The saved users.
   */
  List<User> save(List<User> user);

  /**
   * Deletes a user by id.
   *
   * @param id The id of the user to be deleted.
   */
  void deleteById(String id);

  Set<String> userNameExistInDatabase(Set<String> userNames);

  Optional<List<User>> findPaged(int pageNumber, int pageSize);
}
