package com.jrg.techchallenge.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a user in the application.
 *
 * @author javier.rg@protonmail.com
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

  @Id
  private String username;

  private String name;
  private String email;
  private Gender gender;
  private String picture;
  private String country;
  private String state;
  private String city;

  /**
   * Enumerates the possible gender values for a user.
   */
  public enum Gender {
    MALE,
    FEMALE
  }
}

