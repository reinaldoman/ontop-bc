package com.ontop.challenge.application.ports.out;

import com.ontop.challenge.domain.model.Transaction;

public interface TransactionPersistencePort {
    Transaction saveTransaction(Transaction transaction);
}