package com.jrg.techchallenge.application.service;

import com.jrg.techchallenge.application.generator.RandomUser;
import com.jrg.techchallenge.application.repository.UserRepository;
import com.jrg.techchallenge.domain.User;
import com.jrg.techchallenge.infrastructure.rest.spring.exception.UserNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.jrg.techchallenge.domain.User.Gender.FEMALE;
import static com.jrg.techchallenge.domain.User.Gender.MALE;
import static org.mockito.Mockito.*;

public class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private RandomUser randomUser;

  @InjectMocks
  private UserService userService;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  public List<User> createRandomUsers(int count) {
    return IntStream.range(0, count).mapToObj(i -> new User(
      "randomUser" + i,
      "randomName",
      "random@mail.com",
      MALE,
      "http://randompicture.com/1",
      "randomCountry" + i,
      "randomState" + i,
      "randomCity" + i
    )).collect(Collectors.toList());
  }

  @Test
  void testGetById_ExistingUser_ReturnsUser() {

    User expectedUser = createRandomUsers(1).get(0);
    String userId = expectedUser.getUsername();

    when(userRepository.findById(userId)).thenReturn(Optional.of(expectedUser));

    User result = userService.getById(userId);
    Assertions.assertEquals(expectedUser, result);
  }

  @Test
  void testGetById_NonExistingUser_ThrowsException() {

    User expectedUser = createRandomUsers(1).get(0);
    String userId = expectedUser.getUsername();
    when(userRepository.findById(userId)).thenReturn(Optional.empty());

    Assertions.assertThrows(UserNotFoundException.class, () -> userService.getById(userId));
  }

  @Test
  void testGetAllUsers_ExistingUsers_ReturnsListOfUsers() {

    List<User> expectedUsers = createRandomUsers(2);
    when(userRepository.findAll()).thenReturn(Optional.of(expectedUsers));

    List<User> result = userService.getAllUsers();
    Assertions.assertEquals(expectedUsers, result);
  }

  @Test
  void testGetAllUsers_NoUsers_ThrowsException() {

    when(userRepository.findAll()).thenReturn(Optional.empty());
    Assertions.assertThrows(UserNotFoundException.class, () -> userService.getAllUsers());
  }

  @Test
  void testCreateUser_NewUser_ReturnsCreatedUser() {

    User newUser = createRandomUsers(1).get(0);
    when(userRepository.findById(newUser.getUsername())).thenReturn(Optional.empty());
    when(userRepository.save(newUser)).thenReturn(newUser);

    User result = userService.createUser(newUser);

    Assertions.assertEquals(newUser, result);
    verify(userRepository, times(1)).findById(newUser.getUsername());
    verify(userRepository, times(1)).save(newUser);
  }

  @Test
  void testCreateUser_ExistingUser_ThrowsException() {

    User existingUser = createRandomUsers(1).get(0);
    when(userRepository.findById(existingUser.getUsername())).thenReturn(Optional.of(existingUser));

    Assertions.assertThrows(com.jrg.techchallenge.infrastructure.rest.spring.exception.UserAlreadyExistsException.class, () -> userService.createUser(existingUser));
    verify(userRepository, times(1)).findById(existingUser.getUsername());
    verify(userRepository, times(0)).save(existingUser);
  }

  @Test
  void testCreateUsers_NewUsers_ReturnsCreatedUsers() {

    List<User> newUsers = createRandomUsers(2);
    when(userRepository.save(newUsers)).thenReturn(newUsers);

    List<User> result = userService.createUsers(newUsers);

    Assertions.assertEquals(newUsers, result);
    verify(userRepository, times(1)).save(newUsers);
  }

  @Test
  void testUpdateUser_ExistingUser_ReturnsUpdatedUser() {

    User existingUser = createRandomUsers(1).get(0);
    User updatedUser = createRandomUsers(2).get(1);

    when(userRepository.findById(existingUser.getUsername())).thenReturn(Optional.of(existingUser));
    when(userRepository.save(updatedUser)).thenReturn(updatedUser);

    User result = userService.updateUser(existingUser.getUsername(), updatedUser);

    Assertions.assertEquals(updatedUser, result);
    verify(userRepository, times(1)).findById(existingUser.getUsername());
    verify(userRepository, times(1)).save(updatedUser);
  }

  @Test
  void testUpdateUser_NonExistingUser_ThrowsException() {

    String username = "user1";
    User updatedUser = createRandomUsers(1).get(0);
    when(userRepository.findById(username)).thenReturn(Optional.empty());

    Assertions.assertThrows(UserNotFoundException.class, () -> userService.updateUser(username, updatedUser));
    verify(userRepository, times(1)).findById(username);
    verify(userRepository, times(0)).save(updatedUser);
  }

  @Test
  void testDeleteById_ExistingUser_DeletesUser() {

    User existingUser = createRandomUsers(1).get(0);
    when(userRepository.findById(existingUser.getUsername())).thenReturn(Optional.of(existingUser));

    userService.deleteById(existingUser.getUsername());

    // Assert
    verify(userRepository, times(1)).findById(existingUser.getUsername());
    verify(userRepository, times(1)).deleteById(existingUser.getUsername());
  }

  @Test
  void testDeleteById_NonExistingUser_ThrowsException() {

    String userId = "user1";
    when(userRepository.findById(userId)).thenReturn(Optional.empty());

    Assertions.assertThrows(UserNotFoundException.class, () -> userService.deleteById(userId));
    verify(userRepository, times(1)).findById(userId);
    verify(userRepository, times(0)).deleteById(userId);
  }

  @Test
  void testGenerateRandomUsersAsync_ValidNumberOfUsers_ReturnsListOfUsers() {

    int numberOfUsersToCreate = 5;
    List<User> users = createRandomUsers(numberOfUsersToCreate);
    Map<String, User> randomUsersMap = IntStream.range(0, numberOfUsersToCreate).boxed().collect(Collectors.toMap(i -> users.get(i).getUsername(), users::get, (a, b) -> b));

    when(randomUser.generateRandomUserAsync(numberOfUsersToCreate)).thenReturn(Mono.just(randomUsersMap));
    when(userRepository.userNameExistInDatabase(any())).thenReturn(Collections.emptySet());
    List<User> savedUsers = new ArrayList<>(randomUsersMap.values());
    when(userRepository.save(savedUsers)).thenReturn(savedUsers);

    List<User> result = userService.generateRandomUsersAsync(numberOfUsersToCreate);

    Assertions.assertEquals(savedUsers, result);
    verify(randomUser, times(1)).generateRandomUserAsync(numberOfUsersToCreate);
    verify(userRepository, times(1)).userNameExistInDatabase(any());
    verify(userRepository, times(1)).save(savedUsers);
  }

  @Test
  void testGetUsersAsTree_ExistingUsers_ReturnsUsersAsTree() {
    // Arrange
    List<User> existingUsers = Arrays.asList(
      new User("user1", "John Doe", "doe@mail.com", MALE, "pictureUrl1", "USA", "California", "Los Angeles"),
      new User("user2", "Jane Smith", "smith@mail.com", FEMALE, "pictureUrl2", "USA", "California", "San Francisco"),
      new User("user3", "Michael Johnson", "johnson@mail.com", MALE, "pictureUrl1", "USA", "New York", "New York City")
    );

    when(userRepository.findAll()).thenReturn(Optional.of(existingUsers));

    // Act
    Map<String, Map<String, Map<String, List<User>>>> result = userService.getUsersAsTree();

    // Assert
    Assertions.assertNotNull(result);
    Assertions.assertEquals(1, result.size());

    Map<String, Map<String, List<User>>> countryMap = result.get("USA");
    Assertions.assertNotNull(countryMap);
    Assertions.assertEquals(2, countryMap.size());

    Map<String, List<User>> stateMap = countryMap.get("California");
    Assertions.assertNotNull(stateMap);
    Assertions.assertEquals(2, stateMap.size());
    Assertions.assertTrue(stateMap.containsKey("Los Angeles"));
    Assertions.assertTrue(stateMap.get("Los Angeles").get(0).getUsername().equals("user1"));
    Assertions.assertTrue(stateMap.containsKey("San Francisco"));
    Assertions.assertTrue(stateMap.get("San Francisco").get(0).getUsername().equals("user2"));

    List<User> cityList = stateMap.get("Los Angeles");
    Assertions.assertNotNull(cityList);
    Assertions.assertEquals(1, cityList.size());
    Assertions.assertTrue(cityList.get(0).getUsername().equals("user1"));
    verify(userRepository, times(1)).findAll();
  }

  @Test
  void testGetUsersAsTree_NoExistingUsers_ThrowsException() {
    // Arrange
    when(userRepository.findAll()).thenReturn(Optional.empty());

    // Act and Assert
    Assertions.assertThrows(UserNotFoundException.class, () -> userService.getUsersAsTree());
    verify(userRepository, times(1)).findAll();
  }

  @Test
  void testGetUsersPaged_ExistingUsers_ReturnsPagedUsers() {

    int pageNumber = 0;
    int pageSize = 10;
    List<User> existingUsers = createRandomUsers(3);
    when(userRepository.findPaged(pageNumber, pageSize)).thenReturn(Optional.of(existingUsers));

    List<User> result = userService.getUsersPaged(pageNumber, pageSize);

    Assertions.assertEquals(existingUsers, result);
    verify(userRepository, times(1)).findPaged(pageNumber, pageSize);
  }

  @Test
  void testGetUsersPaged_NoExistingUsers_ThrowsException() {

    int pageNumber = 1;
    int pageSize = 10;
    when(userRepository.findPaged(pageNumber, pageSize)).thenReturn(Optional.empty());


    Assertions.assertThrows(UserNotFoundException.class, () -> userService.getUsersPaged(pageNumber, pageSize));
    verify(userRepository, times(1)).findPaged(pageNumber, pageSize);
  }
}