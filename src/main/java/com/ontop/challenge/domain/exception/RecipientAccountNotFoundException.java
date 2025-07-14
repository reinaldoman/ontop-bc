package com.ontop.challenge.domain.exception;

public class RecipientAccountNotFoundException extends RuntimeException {
    public RecipientAccountNotFoundException(String message) {
        super(message);
    }
}