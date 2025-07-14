package com.ontop.challenge.application.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ontop.challenge.application.ports.out.*;
import com.ontop.challenge.application.service.TransactionService;
import com.ontop.challenge.domain.exception.InsufficientFundsException;
import com.ontop.challenge.domain.model.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private BalancePort balancePort;
    
    @Mock
    private WalletTransactionPort walletTransactionPort;
    
    @Mock
    private PaymentPort paymentPort;
    
    @Mock
    private TransactionPersistencePort transactionPersistencePort;
    
    @Mock
    private RecipientAccountPort recipientAccountPort;
    
    @InjectMocks
    private TransactionService transactionService;
    
    private final Long userId = 1000L;
    private final BigDecimal amount = BigDecimal.valueOf(100);
    private final String recipientAccountId = "acc-123";
    private final RecipientAccount recipientAccount = new RecipientAccount();

    @Test
    void executeTransaction_Success() {
        // Arrange
        when(balancePort.getBalance(userId)).thenReturn(BigDecimal.valueOf(1000));
        when(recipientAccountPort.getRecipientAccountById(recipientAccountId, userId))
                .thenReturn(recipientAccount);
        
        WalletTransactionResponse walletResponse = new WalletTransactionResponse();
        walletResponse.setTransactionId("wallet-txn-123");
        walletResponse.setAmount(BigDecimal.valueOf(-100));
        walletResponse.setUserId(userId);
        
        when(walletTransactionPort.createWalletWithdrawal(userId, amount))
                .thenReturn(walletResponse);
        
        when(paymentPort.createPayment(any(), any(BigDecimal.class), any()))
                .thenReturn(new PaymentResponse("payment-123"));
        when(transactionPersistencePort.saveTransaction(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));
        
        // Act
        Transaction result = transactionService.executeTransaction(userId, amount, recipientAccountId);
        
        // Assert
        assertNotNull(result);
        assertEquals(0, BigDecimal.valueOf(10).compareTo(result.getFee()));
        assertEquals(TransactionStatus.COMPLETED, result.getStatus());
        assertEquals("wallet-txn-123", result.getWalletTransactionId());
        assertEquals("payment-123", result.getPaymentProviderId());
    }

    @Test
    void executeTransaction_InsufficientFunds() {
        // Arrange
        BigDecimal insufficientBalance = BigDecimal.valueOf(50);
        when(balancePort.getBalance(userId)).thenReturn(insufficientBalance);
        when(recipientAccountPort.getRecipientAccountById(recipientAccountId, userId))
                .thenReturn(recipientAccount);
        
        // Act & Assert
        InsufficientFundsException exception = assertThrows(
            InsufficientFundsException.class,
            () -> transactionService.executeTransaction(userId, amount, recipientAccountId)
        );
        
        assertEquals("Insufficient funds in wallet", exception.getMessage());
        
        // Verify
        verify(balancePort).getBalance(userId);
        verify(recipientAccountPort).getRecipientAccountById(recipientAccountId, userId);
        verifyNoInteractions(walletTransactionPort, paymentPort, transactionPersistencePort);
    }
}