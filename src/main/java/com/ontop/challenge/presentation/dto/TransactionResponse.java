package com.ontop.challenge.presentation.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class TransactionResponse {
    private String transactionId;
    private BigDecimal amount;
    private BigDecimal fee;
    private String recipientAccountId;
    private String status;
    private LocalDateTime createdAt;
}