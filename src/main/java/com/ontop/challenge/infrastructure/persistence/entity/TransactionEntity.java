package com.ontop.challenge.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.ontop.challenge.domain.model.TransactionStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transactions")
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String transactionId;
    private Long userId;
    private BigDecimal amount;
    private BigDecimal fee;
    private String recipientAccountId;
    
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
    
    private LocalDateTime createdAt;
    private String paymentProviderId;
    private String walletTransactionId;
}