package com.jrg.techchallenge.infrastructure.rest.randomuser.dto;

import lombok.Data;

import java.util.List;

@Data
public class RandomUserApiResponse {
  private List<ParserUser> results;
  private Info info;

  @Data
  public static class Info {
    private String seed;
    private int results;
    private int page;
    private String version;
  }
}
