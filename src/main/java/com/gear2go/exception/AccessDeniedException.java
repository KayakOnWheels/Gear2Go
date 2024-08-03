package com.gear2go.exception;

import org.springframework.http.HttpStatus;

public class AccessDeniedException extends ExceptionWithHttpStatusCode {
    public AccessDeniedException() {
        super("Unauthorised", HttpStatus.FORBIDDEN);
    }
}
