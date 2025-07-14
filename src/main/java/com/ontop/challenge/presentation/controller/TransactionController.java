package com.ontop.challenge.presentation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ontop.challenge.application.ports.in.TransactionUseCase;
import com.ontop.challenge.domain.model.Transaction;
import com.ontop.challenge.presentation.dto.TransactionRequest;
import com.ontop.challenge.presentation.dto.TransactionResponse;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionUseCase transactionUseCase;

    @PostMapping
    public ResponseEntity<TransactionResponse> executeTransaction(
            @Valid @RequestBody TransactionRequest request) {
        Transaction transaction = transactionUseCase.executeTransaction(
                request.getUserId(),
                request.getAmount(),
                request.getRecipientAccountId()
        );
        
        return ResponseEntity.ok(mapToResponse(transaction));
    }

    private TransactionResponse mapToResponse(Transaction transaction) {
        return TransactionResponse.builder()
                .transactionId(transaction.getTransactionId())
                .amount(transaction.getAmount())
                .fee(transaction.getFee())
                .recipientAccountId(transaction.getRecipientAccountId())
                .status(transaction.getStatus().name())
                .createdAt(transaction.getCreatedAt())
                .build();
    }
}