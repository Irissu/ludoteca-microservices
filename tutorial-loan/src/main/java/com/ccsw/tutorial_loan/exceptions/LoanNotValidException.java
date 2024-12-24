package com.ccsw.tutorial_loan.exceptions;

public class LoanNotValidException extends RuntimeException {
    public LoanNotValidException(String message) {
        super(message);
    }
}
