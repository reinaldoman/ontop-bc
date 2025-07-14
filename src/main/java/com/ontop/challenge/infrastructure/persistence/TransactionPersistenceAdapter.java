package com.ontop.challenge.infrastructure.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.ontop.challenge.application.ports.out.TransactionPersistencePort;
import com.ontop.challenge.domain.model.Transaction;
import com.ontop.challenge.infrastructure.persistence.entity.TransactionEntity;
import com.ontop.challenge.infrastructure.persistence.jpa.TransactionJpaRepository;

@Component
@RequiredArgsConstructor
public class TransactionPersistenceAdapter implements TransactionPersistencePort {

    private final TransactionJpaRepository transactionJpaRepository;

    @Override
    public Transaction saveTransaction(Transaction transaction) {
        TransactionEntity entity = TransactionMapper.toEntity(transaction);
        TransactionEntity savedEntity = transactionJpaRepository.save(entity);
        return TransactionMapper.toDomain(savedEntity);
    }
}