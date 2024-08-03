package com.gear2go.exception;

import org.springframework.http.HttpStatus;

public class BadCredentialsException extends ExceptionWithHttpStatusCode {

    public BadCredentialsException() {
        super("Bad credentials", HttpStatus.UNAUTHORIZED);
    }
}
