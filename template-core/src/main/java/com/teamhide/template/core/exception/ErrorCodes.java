package com.teamhide.template.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCodes {
    ARTICLE_NOT_FOUND("ARTICLE__NOT_FOUND", "article not found", HttpStatus.NOT_FOUND),
    ;

    private final String errorCode;
    private final String message;
    private final HttpStatus statusCode;

    ErrorCodes(final String errorCode, final String message, final HttpStatus statusCode) {
        this.errorCode = errorCode;
        this.message = message;
        this.statusCode = statusCode;
    }
}
