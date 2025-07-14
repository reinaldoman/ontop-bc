package com.ontop.challenge.domain.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Transaction {
    private String transactionId;
    private Long userId;
    private BigDecimal amount;
    private BigDecimal fee;
    private String recipientAccountId;
    private TransactionStatus status;
    private LocalDateTime createdAt;
    private String paymentProviderId;
    private String walletTransactionId;
}