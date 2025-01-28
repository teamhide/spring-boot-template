package com.teamhide.template.core.exception;

public class NotFoundException extends CustomException {
    public NotFoundException(final ErrorCodes errorCodes) {
        super(errorCodes.getStatusCode(), errorCodes.getErrorCode(), errorCodes.getMessage());
    }
}
