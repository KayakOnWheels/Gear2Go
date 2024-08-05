package com.gear2go.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends ExceptionWithHttpStatusCode{
    public UserNotFoundException() {
        super("User not found", HttpStatus.NOT_FOUND);
    }
}
