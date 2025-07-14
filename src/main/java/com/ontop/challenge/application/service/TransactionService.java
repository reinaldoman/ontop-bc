package com.ontop.challenge.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ontop.challenge.application.ports.in.TransactionUseCase;
import com.ontop.challenge.application.ports.out.*;
import com.ontop.challenge.domain.exception.BalanceServiceException;
import com.ontop.challenge.domain.exception.InsufficientFundsException;
import com.ontop.challenge.domain.exception.RecipientAccountNotFoundException;
import com.ontop.challenge.domain.exception.WalletTransactionException;
import com.ontop.challenge.domain.model.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService implements TransactionUseCase {

    private final BalancePort balancePort;
    private final WalletTransactionPort walletTransactionPort;
    private final PaymentPort paymentPort;
    private final TransactionPersistencePort transactionPersistencePort;
    private final RecipientAccountPort recipientAccountPort;

    private static final BigDecimal FEE_PERCENTAGE = BigDecimal.valueOf(0.10);

    @Override
    @Transactional
    public Transaction executeTransaction(Long userId, BigDecimal amount, String recipientAccountId) {
        // 1. Validate recipient account
        RecipientAccount recipientAccount = recipientAccountPort.getRecipientAccountById(recipientAccountId, userId);
        if (recipientAccount == null) {
            throw new RecipientAccountNotFoundException("Recipient account not found");
        }
        
        // 2. Check wallet balance
        BigDecimal balance = balancePort.getBalance(userId);
        validateBalance(balance, amount);
        
        // 3. Calculate fee
        BigDecimal fee = calculateFee(amount);
        BigDecimal netAmount = amount.subtract(fee);
        
        // 4. Create transaction record
        Transaction transaction = createInitialTransaction(userId, amount, fee, recipientAccountId);
        
        try {
            // 5. Create wallet withdrawal
            WalletTransactionResponse walletTransaction = walletTransactionPort.createWalletWithdrawal(userId, amount);
            transaction.setWalletTransactionId(walletTransaction.getTransactionId());
            
            // 6. Create payment to recipient
            PaymentResponse paymentResponse = paymentPort.createPayment(userId, netAmount, recipientAccount);
            transaction.setPaymentProviderId(paymentResponse.getPaymentId());
            
            // 7. Update transaction status
            transaction.setStatus(TransactionStatus.COMPLETED);
            
        } catch (Exception e) {
            log.error("Transaction failed: {}", e.getMessage());
            transaction.setStatus(TransactionStatus.FAILED);
            throw new WalletTransactionException("Transaction processing failed: " + e.getMessage());
        } finally {
            // 8. Persist transaction
            return transactionPersistencePort.saveTransaction(transaction);
        }
    }

    private void validateBalance(BigDecimal balance, BigDecimal amount) {
    	if (balance == null) {
            throw new BalanceServiceException("Could not retrieve wallet balance");
        }
        if (balance.compareTo(amount) < 0) {
            throw new InsufficientFundsException("Insufficient funds in wallet");
        }
    }

    private BigDecimal calculateFee(BigDecimal amount) {
        return amount.multiply(FEE_PERCENTAGE);
    }

    private Transaction createInitialTransaction(Long userId, BigDecimal amount, BigDecimal fee, String recipientAccountId) {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(UUID.randomUUID().toString());
        transaction.setUserId(userId);
        transaction.setAmount(amount);
        transaction.setFee(fee);
        transaction.setRecipientAccountId(recipientAccountId);
        transaction.setStatus(TransactionStatus.PENDING);
        transaction.setCreatedAt(LocalDateTime.now());
        return transaction;
    }
}