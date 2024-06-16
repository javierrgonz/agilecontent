package com.jrg.techchallenge.infrastructure.db.springdata.dbo;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Represents the user entity for persistence.
 *
 * @author javier.rg@protonmail.com
 */
@Entity
@Table(name = "users")
@Data
public class UserEntity {

  @Id
  @Column(name = "username")
  private String username;

  @Column(name = "_name")
  private String name;

  @Column(name = "email")
  private String email;

  @Enumerated(EnumType.STRING)
  @Column(name = "gender")
  private Gender gender;

  @Column(name = "picture")
  private String picture;

  @Column(name = "country")
  private String country;

  @Column(name = "state")
  private String state;

  @Column(name = "city")
  private String city;

  /**
   * Enumerates the gender of a user.
   * This enum is specific to the persistence layer.
   *
   * @author javier.rg@protonmail.com
   */
  public enum Gender {
    MALE,
    FEMALE
  }
}