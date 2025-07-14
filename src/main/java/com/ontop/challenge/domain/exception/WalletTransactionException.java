package com.ontop.challenge.domain.exception;

public class WalletTransactionException extends RuntimeException {
    public WalletTransactionException(String message) {
        super(message);
    }
}