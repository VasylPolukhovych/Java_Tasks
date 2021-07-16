package com.db.awmd.challenge.exception;

public class AccountIdIsNotExists extends RuntimeException {
    public AccountIdIsNotExists(String message) {
        super(message);
    }
}
