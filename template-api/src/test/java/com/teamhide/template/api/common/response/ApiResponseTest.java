package com.teamhide.template.api.common.response;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class ApiResponseTest {

    @Test
    @DisplayName("statusCode만 인자로 가진 success 객체를 만든다")
    void testSuccessOnlyStatusCode() {
        // Given
        final HttpStatus statusCode = HttpStatus.OK;

        // When
        final ApiResponse<Void> response = ApiResponse.success(statusCode);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(statusCode);
        assertThat(response.getBody()).isNull();
    }

    @Test
    @DisplayName("body와 statusCode를 가진 success 객체를 만든다")
    void testSuccessWithBodyAndStatusCode() {
        // Given
        final String body = "Success";
        final HttpStatus statusCode = HttpStatus.CREATED;

        // When
        final ApiResponse<String> response = ApiResponse.success(body, statusCode);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(statusCode);
        assertThat(response.getBody()).isEqualTo(body);
    }

    @Test
    @DisplayName("statusCode만 가진 fail 객체를 만든다")
    void testFailOnlyStatusCode() {
        // Given
        final HttpStatus statusCode = HttpStatus.BAD_REQUEST;

        // When
        final ApiResponse<Void> response = ApiResponse.fail(statusCode);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(statusCode);
        assertThat(response.getBody()).isNull();
    }

    @Test
    @DisplayName("body와 statusCode를 가진 fail 객체를 만든다")
    void testFailWithBodyAndStatusCode() {
        // Given
        final FailBody failBody = new FailBody("Error code", "Error message");
        final HttpStatus statusCode = HttpStatus.UNPROCESSABLE_ENTITY;

        // When
        final ApiResponse<FailBody> response = ApiResponse.fail(failBody, statusCode);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(statusCode);
        assertThat(response.getBody()).isEqualTo(failBody);
    }
}
