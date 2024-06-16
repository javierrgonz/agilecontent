package com.jrg.techchallenge.infrastructure.db.springdata.repository;


import com.jrg.techchallenge.infrastructure.db.springdata.dbo.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data repository for managing UserEntity.
 * This repository extends JpaRepository, providing convenient methods for CRUD operations.
 *
 * @param <UserEntity> The entity type managed by this repository (UserEntity in this case).
 * @param <String>     The type of the entity's primary key (String in this case).
 *                     <p>
 *                     Note: This interface doesn't require any additional methods as it inherits the necessary CRUD methods
 *                     from JpaRepository.
 * @author javier.rg@protonmail.com
 */
public interface SpringDataUserRepository extends JpaRepository<UserEntity, String> {
  // No additional methods needed as JpaRepository provides the necessary CRUD methods
}