package com.jrg.techchallenge.application.generator;

import com.jrg.techchallenge.domain.User;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface RandomUser {

  Mono<Map<String, User>> generateRandomUserAsync(int i);


}
