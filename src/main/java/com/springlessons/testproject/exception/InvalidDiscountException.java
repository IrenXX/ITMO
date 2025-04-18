package com.springlessons.testproject.exception;

import org.springframework.http.HttpStatus;

public class InvalidDiscountException extends RuntimeException {

    public InvalidDiscountException(String message, HttpStatus status) {
        super(message);
    }
}
