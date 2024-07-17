package com.gear2go.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalHttpErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<Object> handleInvalidCredentialsException(AddressNotFoundException addressNotFoundException) {
        return new ResponseEntity<>(addressNotFoundException.getMessage(), HttpStatus.BAD_REQUEST);
    }
}