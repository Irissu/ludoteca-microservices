package com.ccsw.tutorial_loan.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LoanNotFoundException.class)
    public ResponseEntity<String> handleLoanNotFoundException(LoanNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(LoanNotValidException.class)
    public ResponseEntity<ErrorResponse> handleLoanNotValidException(LoanNotValidException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "NOT_VALID_LOAN");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(InvalidReturnDateException.class)
    public ResponseEntity<ErrorResponse> handleInvalidReturnDateException(InvalidReturnDateException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "INVALID_RETURN_DATE");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

}
