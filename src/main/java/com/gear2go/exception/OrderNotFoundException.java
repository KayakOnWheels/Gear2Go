package com.gear2go.exception;

import org.springframework.http.HttpStatus;

public class OrderNotFoundException extends ExceptionWithHttpStatusCode {
    public OrderNotFoundException() {
        super("Order not found", HttpStatus.NOT_FOUND);
    }
}
