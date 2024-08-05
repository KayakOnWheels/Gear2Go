package com.gear2go.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalHttpErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ExceptionWithHttpStatusCode.class)
    public ResponseEntity<Object> handleException(ExceptionWithHttpStatusCode exception) {
        return new ResponseEntity<>(exception.getMessage(), exception.getHttpStatus());
    }
}