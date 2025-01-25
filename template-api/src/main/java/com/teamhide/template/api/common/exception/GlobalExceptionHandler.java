package com.teamhide.template.api.common.exception;

import com.teamhide.template.api.common.response.ApiResponse;
import com.teamhide.template.api.common.response.FailBody;
import com.teamhide.template.core.exception.CommonErrorCodes;
import com.teamhide.template.core.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ApiResponse<FailBody> handleCustomException(final CustomException e) {
        final FailBody body = new FailBody(e.getErrorCode(), e.getMessage());
        return ApiResponse.fail(body, e.getStatusCode());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ApiResponse<FailBody> handleHttpMessageNotReadableException(final HttpMessageNotReadableException e) {
        final CommonErrorCodes errorConst = CommonErrorCodes.HTTP_MESSAGE_NOT_READABLE;
        final FailBody body = new FailBody(errorConst.getErrorCode(), errorConst.getMessage());
        return ApiResponse.fail(body, errorConst.getStatusCode());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ApiResponse<FailBody> handleHttpRequestMethodNotSupportedException(final HttpRequestMethodNotSupportedException e) {
        final CommonErrorCodes errorConst = CommonErrorCodes.HTTP_REQUEST_METHOD_NOT_SUPPORTED;
        final FailBody body = new FailBody(errorConst.getErrorCode(), errorConst.getMessage());
        return ApiResponse.fail(body, errorConst.getStatusCode());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ApiResponse<FailBody> handleNoHandlerFoundException(final NoHandlerFoundException e) {
        final CommonErrorCodes errorConst = CommonErrorCodes.NO_HANDLER_FOUND;
        final FailBody body = new FailBody(errorConst.getErrorCode(), errorConst.getMessage());
        return ApiResponse.fail(body, errorConst.getStatusCode());
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ApiResponse<FailBody> handleNoResourceFoundException(final NoResourceFoundException e) {
        final CommonErrorCodes errorConst = CommonErrorCodes.AUTHENTICATION_ERROR;
        final FailBody body = new FailBody(errorConst.getErrorCode(), errorConst.getMessage());
        return ApiResponse.fail(body, errorConst.getStatusCode());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResponse<FailBody> handleIllegalArgumentException(final IllegalArgumentException e) {
        final CommonErrorCodes errorConst = CommonErrorCodes.ILLEGAL_ARGUMENT;
        final FailBody body = new FailBody(errorConst.getErrorCode(), errorConst.getMessage());
        return ApiResponse.fail(body, errorConst.getStatusCode());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ApiResponse<FailBody> handleIllegalStateException(final IllegalStateException e) {
        final CommonErrorCodes errorConst = CommonErrorCodes.ILLEGAL_STATE;
        final FailBody body = new FailBody(errorConst.getErrorCode(), errorConst.getMessage());
        return ApiResponse.fail(body, errorConst.getStatusCode());
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<FailBody> handleException(final Exception e) {
        final CommonErrorCodes errorConst = CommonErrorCodes.UNKNOWN;
        final FailBody body = new FailBody(errorConst.getErrorCode(), errorConst.getMessage());
        log.error("GlobalExceptionHandler | Unhandled exception {}", e);
        return ApiResponse.fail(body, errorConst.getStatusCode());
    }
}
