package com.springlessons.testproject.exception;

public class OrdersNotFoundException extends Throwable {
    public OrdersNotFoundException(String s) {
        super(s);
    }
}
