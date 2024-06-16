package com.jrg.techchallenge.infrastructure.rest.spring.controller;

import com.jrg.techchallenge.application.service.UserService;
import com.jrg.techchallenge.infrastructure.rest.spring.dto.TreeResponseDto;
import com.jrg.techchallenge.infrastructure.rest.spring.dto.UserDto;
import com.jrg.techchallenge.infrastructure.rest.spring.mapper.UserDtoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for handling user-related requests.
 *
 * @author javier.rg@protonmail.com
 */
@RequiredArgsConstructor
@RestController("/api/users")
public class UserController {

  private final UserService userService;
  private final UserDtoMapper userDtoMapper;

  /**
   * Get all users as a list
   *
   * @return ResponseEntity with the user converted to UserDto in the response body.
   */
  @GetMapping("/")
  public ResponseEntity<List<UserDto>> getAll() {
    return new ResponseEntity<>(userDtoMapper.toDtoList(userService.getAllUsers()), HttpStatus.OK);
  }

  /**
   * Get all users as a list
   *
   * @return ResponseEntity with the user converted to UserDto in the response body.
   */
  @GetMapping("/paged")
  public ResponseEntity<List<UserDto>> getPaged(@RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize) {
    return new ResponseEntity<>(userDtoMapper.toDtoList(userService.getUsersPaged(pageNumber, pageSize)), HttpStatus.OK);
  }


  /**
   * Get a user by ID.
   *
   * @param id The ID of the user.
   * @return ResponseEntity with the user converted to UserDto in the response body.
   */
  @GetMapping("/{id}")
  public ResponseEntity<UserDto> get(@PathVariable String id) {
    return new ResponseEntity<>(userDtoMapper.toDto(userService.getById(id)), HttpStatus.OK);
  }

  /**
   * Create a new user.
   *
   * @param userDto The user data in UserDto format.
   * @return ResponseEntity with the created user converted to UserDto in the response body.
   */
  @PostMapping(value = "/")
  public ResponseEntity<UserDto> post(@Valid @RequestBody UserDto userDto) {
    return new ResponseEntity<>(
      userDtoMapper.toDto(userService.createUser(userDtoMapper.toDomain(userDto))),
      HttpStatus.CREATED);
  }

  /**
   * Update an existing user.
   *
   * @param id      The ID of the user to update.
   * @param userDto The updated user data in UserDto format.
   * @return ResponseEntity with the updated user converted to UserDto in the response body.
   */
  @PutMapping("/{id}")
  public ResponseEntity<UserDto> put(@PathVariable String id, @Valid @RequestBody UserDto userDto) {
    return new ResponseEntity<>(
      userDtoMapper.toDto(userService.updateUser(id, userDtoMapper.toDomain(userDto))),
      HttpStatus.OK);
  }

  /**
   * Delete a user by ID.
   *
   * @param id The ID of the user to delete.
   * @return ResponseEntity with null in the response body.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<UserDto> delete(@PathVariable String id) {
    userService.deleteById(id);
    return new ResponseEntity<>(null, HttpStatus.OK);
  }

  /**
   * Generate a specified number of random users.
   *
   * @param number The number of users to generate.
   * @return ResponseEntity with the list of generated users converted to UserDto in the response body.
   */
  @GetMapping("/generate/{number}")
  public ResponseEntity<List<UserDto>> generate(@PathVariable Integer number) {
    return new ResponseEntity<>(
      userDtoMapper.toDtoList(userService.generateRandomUsersAsync(number)),
      HttpStatus.OK);
  }

  /**
   * Get a tree of users grouped by country, state, and city.
   *
   * @return ResponseEntity with the user tree in the response body.
   */
  @GetMapping("/tree")
  public ResponseEntity<TreeResponseDto> getTree() {
    return new ResponseEntity<>(
      new TreeResponseDto(userDtoMapper.toUserDtoMap(userService.getUsersAsTree())),
      HttpStatus.OK);
  }

}