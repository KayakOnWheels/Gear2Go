package com.gear2go.exception;

import org.springframework.http.HttpStatus;

public class FailedToSendEmailException extends ExceptionWithHttpStatusCode {
    public FailedToSendEmailException() {
        super("Failed to send e-mail", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
