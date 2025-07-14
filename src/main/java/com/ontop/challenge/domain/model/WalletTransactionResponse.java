package com.ontop.challenge.domain.model;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class WalletTransactionResponse {
    private String transactionId;  // Changed from walletTransactionId to transactionId
    private Long userId;
    private BigDecimal amount;
    private String status;
}