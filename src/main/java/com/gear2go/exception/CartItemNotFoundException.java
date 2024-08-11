package com.gear2go.exception;

import org.springframework.http.HttpStatus;

public class CartItemNotFoundException extends ExceptionWithHttpStatusCode {

    public CartItemNotFoundException() {
        super("Cart item not found", HttpStatus.NOT_FOUND);
    }
}
