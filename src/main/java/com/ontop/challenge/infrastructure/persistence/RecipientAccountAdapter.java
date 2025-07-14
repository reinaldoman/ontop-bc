package com.ontop.challenge.infrastructure.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.ontop.challenge.application.ports.out.RecipientAccountPort;
import com.ontop.challenge.domain.model.RecipientAccount;
import com.ontop.challenge.infrastructure.persistence.jpa.RecipientAccountJpaRepository;

@Component
@RequiredArgsConstructor
public class RecipientAccountAdapter implements RecipientAccountPort {

    private final RecipientAccountJpaRepository recipientAccountJpaRepository;

    @Override
    public RecipientAccount getRecipientAccountById(String accountId, Long userId) {
        return recipientAccountJpaRepository.findByAccountIdAndUserId(accountId, userId)
                .map(RecipientAccountMapper::toDomain)
                .orElse(null);
    }
}