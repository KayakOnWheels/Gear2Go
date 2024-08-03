package com.gear2go.exception;

import org.springframework.http.HttpStatus;

public class ProductNotAvailable extends ExceptionWithHttpStatusCode {

    public ProductNotAvailable() {
        super("Product not Available", HttpStatus.NOT_FOUND);
    }
}