package com.gear2go.exception;

public class AddressNotFoundException extends  Exception{

    public AddressNotFoundException(Long id) {
        super(String.format("Address with id: %s not found", id));
    }
}