package com.jrg.techchallenge.infrastructure.db.springdata.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import lombok.extern.slf4j.Slf4j;

@Configuration
@ConfigurationProperties("spring.datasource")
@Slf4j
@Data
@EnableJpaAuditing
@EntityScan(basePackages = "com.jrg.techchallenge.infrastructure.db.springdata.dbo")
@EnableJpaRepositories(basePackages = "com.jrg.techchallenge.infrastructure.db.springdata.repository")
public class SpringDataConfig { }