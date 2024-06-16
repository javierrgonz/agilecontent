package com.jrg.techchallenge.infrastructure.rest.spring.dto;

import com.jrg.techchallenge.infrastructure.rest.spring.validation.GenderEnum;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

  @Id
  @NotBlank(message = "Username is mandatory")
  private String username;

  @NotBlank(message = "Name is mandatory")
  private String name;

  @NotBlank(message = "Email is mandatory")
  @Email(message = "Email not valid")
  private String email;

  @GenderEnum
  private String gender;

  @NotBlank(message = "Picture is mandatory")
  private String picture;

  @NotBlank(message = "Country is mandatory")
  private String country;

  @NotBlank(message = "State is mandatory")
  private String state;

  @NotBlank(message = "City is mandatory")
  private String city;

}