package com.teamhide.template.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {
    private final HttpStatus statusCode;
    private final String errorCode;
    private final String message;

    public CustomException(final HttpStatus statusCode, final String errorCode, final String message) {
        this.statusCode = statusCode;
        this.errorCode = errorCode;
        this.message = message;
    }
}
