package com.jrg.techchallenge.infrastructure.db.springdata.repository;

import com.jrg.techchallenge.application.repository.UserRepository;
import com.jrg.techchallenge.domain.User;
import com.jrg.techchallenge.infrastructure.db.springdata.dbo.UserEntity;
import com.jrg.techchallenge.infrastructure.db.springdata.mapper.UserEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of the UserRepository interface using Spring Data repository and entity mapping.
 * This repository class interacts with the underlying database through Spring Data repositories.
 *
 * @author javier.rg@protonmail.com
 */
@Repository
@RequiredArgsConstructor
public class UserDboRepository implements UserRepository {

  private final SpringDataUserRepository userRepository;
  private final UserEntityMapper userMapper;

  @Override
  public Optional<User> findById(String id) {
    return userRepository.findById(id)
      .map(userMapper::toDomain)
      .or(Optional::empty);
  }

  @Override
  public Optional<List<User>> findAll() {
    return Optional.ofNullable(
      userRepository
        .findAll()
        .stream()
        .map(userMapper::toDomain)
        .collect(Collectors.toList())
    );
  }

  @Override
  public User save(User user) {
    UserEntity userEntity = userMapper.toDbo(user);
    UserEntity savedUserEntity = userRepository.save(userEntity);
    return userMapper.toDomain(savedUserEntity);
  }

  @Override
  public List<User> save(List<User> users) {
    return (userRepository.saveAll(users.stream()
      .map(userMapper::toDbo)
      .collect(Collectors.toList())))
      .stream()
      .map(userMapper::toDomain)
      .collect(Collectors.toList());
  }

  @Override
  public void deleteById(String username) {
    userRepository.deleteById(username);
  }

  @Override
  public Set<String> userNameExistInDatabase(Set<String> userNames) {
    return userNames.stream()
      .filter(userName -> userRepository.existsById(userName))
      .collect(Collectors.toSet());
  }

  @Override
  public Optional<List<User>> findPaged(int pageNumber, int pageSize) {
    return Optional.ofNullable(userRepository.findAll(PageRequest.of(pageNumber, pageSize)).stream().map(userMapper::toDomain).collect(Collectors.toList()));
  }
}