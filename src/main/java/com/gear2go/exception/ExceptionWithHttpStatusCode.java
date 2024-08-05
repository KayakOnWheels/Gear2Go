package com.gear2go.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public abstract class ExceptionWithHttpStatusCode extends Exception {
    private HttpStatus httpStatus;

    public ExceptionWithHttpStatusCode(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
