package com.gear2go.exception;

import org.springframework.http.HttpStatus;

public class TokenExpiredException extends ExceptionWithHttpStatusCode {
    public TokenExpiredException() {
        super("Token has expired", HttpStatus.UNAUTHORIZED);
    }
}
