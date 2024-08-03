package com.gear2go.exception;

import org.springframework.http.HttpStatus;

public class AddressNotFoundException extends ExceptionWithHttpStatusCode {

    public AddressNotFoundException(Long id) {
        super(String.format("Address with id: %s not found", id), HttpStatus.BAD_REQUEST);
    }
}