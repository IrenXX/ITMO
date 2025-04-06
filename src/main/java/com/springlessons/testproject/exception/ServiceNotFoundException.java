package com.springlessons.testproject.exception;

public class ServiceNotFoundException extends Throwable {
    public ServiceNotFoundException(String s) {
        super(s);
    }
}
