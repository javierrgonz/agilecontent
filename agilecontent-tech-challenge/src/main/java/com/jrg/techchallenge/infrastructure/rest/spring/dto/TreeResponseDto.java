package com.jrg.techchallenge.infrastructure.rest.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class TreeResponseDto {
  private Map<String, Map<String, Map<String, List<UserDto>>>> users;
}
