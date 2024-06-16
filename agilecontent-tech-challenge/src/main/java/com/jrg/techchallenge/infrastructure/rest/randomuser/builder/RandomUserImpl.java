package com.jrg.techchallenge.infrastructure.rest.randomuser.builder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jrg.techchallenge.application.generator.RandomUser;
import com.jrg.techchallenge.domain.User;
import com.jrg.techchallenge.infrastructure.rest.randomuser.dto.ParseUserError;
import com.jrg.techchallenge.infrastructure.rest.randomuser.parser.UserParser;
import lombok.AllArgsConstructor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@AllArgsConstructor
public class RandomUserImpl implements RandomUser {

  private final UserParser userParser;
  private final WebClient webClient;
  public static final String END_POINT = "https://randomuser.me/api/1.4/?inc=login,email,picture,gender,name,location&results=";
  public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  @Override
  public Mono<Map<String, User>> generateRandomUserAsync(int numberOfUsersToCreate) {

    if (numberOfUsersToCreate < 1) {
      throw new IllegalArgumentException("numberOfUsersToCreate should be greater than zero");
    }

    return webClient.get()
      .uri(END_POINT + numberOfUsersToCreate)
      .retrieve()
      .bodyToMono(String.class)
      .flatMap(response -> {
        if (isError(response)) throw new RestClientException("Error retrieving users from randomUser API");
        return Mono.just(userParser.parse(response));
      });
  }

  private boolean isError(String randomUserApiResponse) {
    try {
      OBJECT_MAPPER.convertValue(randomUserApiResponse, ParseUserError.class);
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}

