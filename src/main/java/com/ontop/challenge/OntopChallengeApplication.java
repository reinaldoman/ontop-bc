package com.ontop.challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.ontop.challenge.infrastructure.persistence.jpa")
@EntityScan(basePackages = "com.ontop.challenge.infrastructure.persistence.entity")
public class OntopChallengeApplication {
    public static void main(String[] args) {
        SpringApplication.run(OntopChallengeApplication.class, args);
    }
}