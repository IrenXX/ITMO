package com.springlessons.testproject.exception;

import org.springframework.http.HttpStatus;

public class InvalidDateRangeException extends RuntimeException {

    public InvalidDateRangeException(String message, HttpStatus status) {
        super(message);
    }
}
