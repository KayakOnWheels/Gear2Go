package com.gear2go.exception;

import org.springframework.http.HttpStatus;

public class CartNotFoundException extends ExceptionWithHttpStatusCode {

    public CartNotFoundException() {
        super("Cart not found", HttpStatus.NOT_FOUND);
    }
}