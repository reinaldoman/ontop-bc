package com.ontop.challenge.infrastructure.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.ontop.challenge.infrastructure.persistence.entity.RecipientAccountEntity;
import com.ontop.challenge.infrastructure.persistence.jpa.RecipientAccountJpaRepository;

@Configuration
@RequiredArgsConstructor
@Profile("!test") // Don't run during tests
public class DatabaseInitializer {

    private final RecipientAccountJpaRepository recipientAccountJpaRepository;

    @PostConstruct
    public void init() {
        if (recipientAccountJpaRepository.count() == 0) {
            RecipientAccountEntity account = RecipientAccountEntity.builder()
                .accountId("acc-123")
                .firstName("John")
                .lastName("Doe")
                .routingNumber("123456789")
                .nationalIdNumber("ID123456")
                .accountNumber("987654321")
                .bankName("Test Bank")
                .userId(1000L)
                .build();
            recipientAccountJpaRepository.save(account);
        }
    }
}