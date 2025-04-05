package com.springlessons.testproject.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class OrderException extends RuntimeException {

    private HttpStatus status;

    public OrderException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
