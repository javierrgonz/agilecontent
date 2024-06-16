package com.jrg.techchallenge.infrastructure.rest.randomuser.dto;

import lombok.Data;

@Data
public class ParserUser {
  private String gender;
  private Name name;
  private Location location;
  private String email;
  private Login login;
  private Picture picture;

  @Data
  public static class Name {
    private String title;
    private String first;
    private String last;

    public String getCompleteName() {
      return String.format("%s %s %s", this.title, this.first, this.last);
    }
  }

  @Data
  public static class Location {
    private Street street;
    private String city;
    private String state;
    private String country;
    private String postcode;
    private Coordinates coordinates;
    private Timezone timezone;

    @Data
    public static class Street {
      private int number;
      private String name;
    }

    @Data
    public static class Coordinates {
      private String latitude;
      private String longitude;
    }

    @Data
    public static class Timezone {
      private String offset;
      private String description;
    }
  }

  @Data
  public static class Login {
    private String uuid;
    private String username;
    private String password;
    private String salt;
    private String md5;
    private String sha1;
    private String sha256;
  }

  @Data
  public static class Picture {
    private String large;
    private String medium;
    private String thumbnail;
  }
}