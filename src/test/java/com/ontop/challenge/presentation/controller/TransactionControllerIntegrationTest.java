package com.ontop.challenge.presentation.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.ontop.challenge.application.ports.in.TransactionUseCase;
import com.ontop.challenge.domain.model.Transaction;
import com.ontop.challenge.domain.model.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test") // Load test-specific properties
public class TransactionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private TransactionUseCase transactionUseCase;

    @Test
    public void executeTransaction_ValidRequest_ReturnsCreated() throws Exception {
        // Arrange
        Transaction transaction = new Transaction();
        transaction.setTransactionId("txn-123");
        transaction.setUserId(1000L);
        transaction.setAmount(BigDecimal.valueOf(100));
        transaction.setFee(BigDecimal.valueOf(10));
        transaction.setRecipientAccountId("acc-123");
        transaction.setStatus(TransactionStatus.COMPLETED);
        transaction.setCreatedAt(LocalDateTime.now());
        
        when(transactionUseCase.executeTransaction(any(), any(), anyString()))
                .thenReturn(transaction);
        
        // Act & Assert
        mockMvc.perform(post("/api/v1/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "userId": 1000,
                        "amount": 100,
                        "recipientAccountId": "acc-123"
                    }
                    """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionId").value("txn-123"))
                .andExpect(jsonPath("$.amount").value(100))
                .andExpect(jsonPath("$.fee").value(10))
                .andExpect(jsonPath("$.status").value("COMPLETED"));
    }
}