package com.ontop.challenge.infrastructure.adapters;

import java.math.BigDecimal;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.ontop.challenge.application.ports.out.WalletTransactionPort;
import com.ontop.challenge.domain.model.WalletTransactionResponse;

@Component
@Profile("!prod")
public class MockWalletTransactionAdapter implements WalletTransactionPort {

    @Override
    public WalletTransactionResponse createWalletWithdrawal(Long userId, BigDecimal amount) {
        WalletTransactionResponse response = new WalletTransactionResponse();
        response.setTransactionId("mock-txn-" + System.currentTimeMillis());
        response.setUserId(userId);
        response.setAmount(amount);
        response.setStatus("COMPLETED");
        return response;
    }
}