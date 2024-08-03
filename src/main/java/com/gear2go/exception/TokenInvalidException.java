package com.gear2go.exception;

import org.springframework.http.HttpStatus;

public class TokenInvalidException extends  ExceptionWithHttpStatusCode{

    public TokenInvalidException() {
        super("Token invalid", HttpStatus.FORBIDDEN);
    }
}