package com.gear2go.exception;

import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends ExceptionWithHttpStatusCode {
    public ProductNotFoundException() {
        super("Product not found", HttpStatus.NOT_FOUND);
    }
}
