package com.ontop.challenge.application.ports.in;

import java.math.BigDecimal;

import com.ontop.challenge.domain.model.Transaction;

public interface TransactionUseCase {
    Transaction executeTransaction(Long userId, BigDecimal amount, String recipientAccountId);
}