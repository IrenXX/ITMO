package com.springlessons.testproject.exceptions;

public class MessageValidationException extends RuntimeException {
    private final String field;

    public MessageValidationException(String field, String message) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return field;
    }
} 