package com.jrg.techchallenge.infrastructure.config.spring;


import com.jrg.techchallenge.application.generator.RandomUser;
import com.jrg.techchallenge.application.repository.UserRepository;
import com.jrg.techchallenge.application.service.UserService;
import com.jrg.techchallenge.infrastructure.rest.randomuser.builder.RandomUserImpl;
import com.jrg.techchallenge.infrastructure.rest.randomuser.mapper.ParserUserMapper;
import com.jrg.techchallenge.infrastructure.rest.randomuser.parser.UserParser;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.io.IOException;

@Configuration
public class SpringBootServiceConfig {

  @Bean
  public UserService userService(UserRepository userRepository, RandomUser randomUser) {
    return new UserService(userRepository, randomUser);
  }

  @Bean
  public WebClient webClient() throws IOException {

    // Not secure, only for test
    SslContext sslContext = SslContextBuilder
      .forClient()
      .trustManager(InsecureTrustManagerFactory.INSTANCE)
      .build();

    return WebClient
      .builder()
      .clientConnector(new ReactorClientHttpConnector(HttpClient.create().secure(t -> t.sslContext(sslContext))))
      .build();
  }

  @Bean
  public RandomUserImpl randomUser(ParserUserMapper parserUserMapper, WebClient webClient) {
    return new RandomUserImpl(new UserParser(parserUserMapper), webClient);
  }

}