package com.ontop.challenge.domain.exception;

public class BalanceServiceException extends RuntimeException {
    public BalanceServiceException(String message) {
        super(message);
    }
    public BalanceServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}