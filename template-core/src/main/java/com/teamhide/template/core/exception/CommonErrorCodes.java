package com.teamhide.template.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CommonErrorCodes {
    HTTP_MESSAGE_NOT_READABLE(
            "HTTP_MESSAGE_NOT_READABLE", "Message not readable", HttpStatus.BAD_REQUEST),
    HTTP_REQUEST_METHOD_NOT_SUPPORTED(
            "HTTP_REQUEST_METHOD_NOT_SUPPORTED",
            "Http request method not supported",
            HttpStatus.BAD_REQUEST),
    METHOD_ARGUMENT_NOT_VALID(
            "METHOD_ARGUMENT_NOT_VALID", "Method argument not valid", HttpStatus.BAD_REQUEST),
    AUTHENTICATION_ERROR("AUTHENTICATION_ERROR", "Authentication error", HttpStatus.UNAUTHORIZED),
    NO_HANDLER_FOUND("NO_HANDLER_FOUND", "No endpoint GET URL", HttpStatus.NOT_FOUND),
    UNKNOWN("UNKNOWN", "Unknown", HttpStatus.INTERNAL_SERVER_ERROR),
    ILLEGAL_ARGUMENT("ILLEGAL_ARGUMENT", "Illegal argument", HttpStatus.BAD_REQUEST),
    ILLEGAL_STATE("ILLEGAL_STATE", "Illegal state", HttpStatus.BAD_REQUEST);

    private final String errorCode;
    private final String message;
    private final HttpStatus statusCode;

    CommonErrorCodes(final String errorCode, final String message, final HttpStatus statusCode) {
        this.errorCode = errorCode;
        this.message = message;
        this.statusCode = statusCode;
    }
}
