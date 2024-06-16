package com.jrg.techchallenge.infrastructure.rest.randomuser.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jrg.techchallenge.domain.User;
import com.jrg.techchallenge.infrastructure.rest.randomuser.dto.RandomUserApiResponse;
import com.jrg.techchallenge.infrastructure.rest.randomuser.mapper.ParserUserMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.util.Map;

@RequiredArgsConstructor
public class UserParser {

  private final ParserUserMapper parserUserMapper;
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  @SneakyThrows
  public Map<String, User> parse(String apiResponse) {
    return parserUserMapper.toUserMap(OBJECT_MAPPER.readValue(apiResponse, RandomUserApiResponse.class).getResults());
  }
}
