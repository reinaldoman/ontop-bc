package com.ontop.challenge.infrastructure.persistence;

import com.ontop.challenge.domain.model.Transaction;
import com.ontop.challenge.infrastructure.persistence.entity.TransactionEntity;

public class TransactionMapper {

    public static TransactionEntity toEntity(Transaction transaction) {
        return TransactionEntity.builder()
                .transactionId(transaction.getTransactionId())
                .userId(transaction.getUserId())
                .amount(transaction.getAmount())
                .fee(transaction.getFee())
                .recipientAccountId(transaction.getRecipientAccountId())
                .status(transaction.getStatus())
                .createdAt(transaction.getCreatedAt())
                .paymentProviderId(transaction.getPaymentProviderId())
                .walletTransactionId(transaction.getWalletTransactionId())
                .build();
    }

    public static Transaction toDomain(TransactionEntity entity) {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(entity.getTransactionId());
        transaction.setUserId(entity.getUserId());
        transaction.setAmount(entity.getAmount());
        transaction.setFee(entity.getFee());
        transaction.setRecipientAccountId(entity.getRecipientAccountId());
        transaction.setStatus(entity.getStatus());
        transaction.setCreatedAt(entity.getCreatedAt());
        transaction.setPaymentProviderId(entity.getPaymentProviderId());
        transaction.setWalletTransactionId(entity.getWalletTransactionId());
        return transaction;
    }
}