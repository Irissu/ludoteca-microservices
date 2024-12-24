package com.ccsw.tutorial_loan.exceptions;

public class InvalidReturnDateException extends RuntimeException {
    public InvalidReturnDateException(String message) {
        super(message);
    }
}
