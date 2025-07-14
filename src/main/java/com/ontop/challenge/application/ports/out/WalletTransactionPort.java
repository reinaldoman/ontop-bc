package com.ontop.challenge.application.ports.out;

import java.math.BigDecimal;

import com.ontop.challenge.domain.model.WalletTransactionResponse;

public interface WalletTransactionPort {
    WalletTransactionResponse createWalletWithdrawal(Long userId, BigDecimal amount);
}