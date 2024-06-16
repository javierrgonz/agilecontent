package com.jrg.techchallenge.infrastructure.db.springdata.mapper;

import com.jrg.techchallenge.domain.User;
import org.mapstruct.Mapper;

import com.jrg.techchallenge.infrastructure.db.springdata.dbo.UserEntity;

/**
 * Mapper interface for converting between {@link UserEntity} and {@link User}.
 * This mapper uses MapStruct and is configured to work with Spring.
 *
 * @author javier.rg@protonmail.com
 */
@Mapper(componentModel = "spring")
public interface UserEntityMapper {

  /**
   * Converts a {@link UserEntity} object to a {@link User} object.
   *
   * @param userEntity The UserEntity object to be converted.
   * @return The corresponding User object.
   */
  User toDomain(UserEntity userEntity);

  /**
   * Converts a {@link User} object to a {@link UserEntity} object.
   *
   * @param user The User object to be converted.
   * @return The corresponding UserEntity object.
   */
  UserEntity toDbo(User user);
}
