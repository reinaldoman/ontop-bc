package com.ontop.challenge.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WalletTransactionRequest {
    private Long userId;
    private BigDecimal amount;
    private String currency;
    private String transactionType;  // "DEPOSIT" or "WITHDRAWAL"
    private String transactionReference;
    
    // Optional fields for additional context
    private String description;
    private String source;          // e.g., "ONTOP_PAYMENT"
    private String destination;     // e.g., "USER_WALLET"
    
    public static WalletTransactionRequest createWithdrawalRequest(
            Long userId, 
            BigDecimal amount, 
            String currency, 
            String reference) {
        return WalletTransactionRequest.builder()
                .userId(userId)
                .amount(amount.negate())  // Negative for withdrawals
                .currency(currency)
                .transactionType("WITHDRAWAL")
                .transactionReference(reference)
                .source("ONTOP_PAYMENT")
                .destination("USER_WALLET")
                .description("Withdrawal to bank account")
                .build();
    }
    
    public static WalletTransactionRequest createDepositRequest(
            Long userId, 
            BigDecimal amount, 
            String currency, 
            String reference) {
        return WalletTransactionRequest.builder()
                .userId(userId)
                .amount(amount)  // Positive for deposits
                .currency(currency)
                .transactionType("DEPOSIT")
                .transactionReference(reference)
                .source("EXTERNAL")
                .destination("USER_WALLET")
                .description("Deposit from external source")
                .build();
    }
}