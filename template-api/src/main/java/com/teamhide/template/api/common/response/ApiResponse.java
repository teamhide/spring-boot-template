package com.teamhide.template.api.common.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiResponse<T> extends ResponseEntity<T> {
    public ApiResponse(final HttpStatus statusCode) {
        super(statusCode);
    }

    public ApiResponse(final T body, final HttpStatus statusCode) {
        super(body, statusCode);
    }

    public static ApiResponse<Void> success(final HttpStatus statusCode) {
        return new ApiResponse<>(statusCode);
    }

    public static <T> ApiResponse<T> success(final T body, final HttpStatus statusCode) {
        return new ApiResponse<>(body, statusCode);
    }

    public static ApiResponse<Void> fail(final HttpStatus statusCode) {
        return new ApiResponse<>(statusCode);
    }

    public static ApiResponse<FailBody> fail(final FailBody body, final HttpStatus statusCode) {
        return new ApiResponse<>(body, statusCode);
    }
}
