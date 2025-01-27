package com.teamhide.template.api.common.exception;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.teamhide.template.core.exception.CommonErrorCodes;
import com.teamhide.template.core.exception.CustomException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

class GlobalExceptionHandlerTest {
    public static class TestException extends CustomException {
        public TestException() {
            super(HttpStatus.BAD_REQUEST, "TEST__EXCEPTION", "");
        }
    }

    public static class TestHttpInputMessage implements HttpInputMessage {

        @Override
        public InputStream getBody() throws IOException {
            byte[] ret = "error".getBytes();
            return new ByteArrayInputStream(ret);
        }

        @Override
        public HttpHeaders getHeaders() {
            return null;
        }
    }

    public class TestRequest {
        @NotNull private Long customerId;
    }

    @RestController
    class TestController {

        @GetMapping("/custom-exception")
        public void customException() {
            throw new TestException();
        }

        @GetMapping("/http-message-not-readable")
        public void httpMessageNotReadable() {
            throw new HttpMessageNotReadableException("error", new TestHttpInputMessage());
        }

        @GetMapping("/no-handler-found")
        public void noHandlerFound() throws NoHandlerFoundException {
            throw new NoHandlerFoundException("GET", "URL", HttpHeaders.EMPTY);
        }

        @GetMapping("/method-not-supported")
        public void methodNotSupported() throws HttpRequestMethodNotSupportedException {
            throw new HttpRequestMethodNotSupportedException("error");
        }

        @GetMapping("/unknown-error")
        public void unknownError() throws Exception {
            throw new Exception("error");
        }

        @PostMapping("/kakao-channel/v1/mapping")
        public void methodArgumentNotValid(@RequestBody @Valid final TestRequest request) {}

        @DeleteMapping("/kakao-channel/v1/mapping")
        public void methodArgumentNotValidDelete(@RequestBody @Valid final TestRequest request) {}

        @PutMapping("/kakao-channel/v1/mapping")
        public void methodArgumentNotValidGet(@RequestBody @Valid final TestRequest request) {}

        @PostMapping("/kakao-channel/v1/mappings")
        public void methodArgumentNotValidNotTargetUrl(@RequestBody @Valid final TestRequest request) {}

        @GetMapping("/missing-http-header")
        public void missingHttpHeader(@RequestHeader("Authorization") final String authorization)
                throws MissingRequestHeaderException {}

        @GetMapping("/missing-servlet-request-parameter")
        public void missingServletRequestParameter(@RequestParam("status") String status) {}

        @GetMapping("/method-argument-type-mismatch/{vendorId}")
        public void methodArgumentTypeMismatch(@PathVariable("vendorId") final Long vendorId) {}

        @GetMapping("/illegal-argument")
        public void methodIllegalArgument() {
            throw new IllegalArgumentException();
        }

        @GetMapping("/illegal-state")
        public void methodIllegalState() {
            throw new IllegalStateException();
        }

        @GetMapping("/no-resource-found")
        public void methodNoResourceFound() throws NoResourceFoundException {
            throw new NoResourceFoundException(HttpMethod.GET, "");
        }
    }

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc =
                MockMvcBuilders.standaloneSetup(new TestController())
                        .setControllerAdvice(new GlobalExceptionHandler())
                        .build();
    }

    @Test
    @DisplayName("CustomException을 상속받은 예외가 발생하면 정의한 응답 코드와 에러 메시지가 출력된다")
    void testCustomExceptionHandler() throws Exception {
        // Given, When, then
        mockMvc
                .perform(get("/custom-exception").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value("TEST__EXCEPTION"))
                .andExpect(jsonPath("message").value(""));
    }

    @Test
    @DisplayName("HttpMessageNotReadableException 예외가 발생하면 정의한 응답 코드와 에러 메시지가 출력된다")
    void testHttpMessageNotReadableExceptionHandler() throws Exception {
        // Given, When, then
        mockMvc
                .perform(get("/http-message-not-readable").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(
                        jsonPath("errorCode").value(CommonErrorCodes.HTTP_MESSAGE_NOT_READABLE.getErrorCode()))
                .andExpect(
                        jsonPath("message").value(CommonErrorCodes.HTTP_MESSAGE_NOT_READABLE.getMessage()));
    }

    @Test
    @DisplayName("NoHandlerFoundException 예외가 발생하면 정의한 응답 코드와 에러 메시지가 출력된다")
    void testNoHandlerFoundExceptionHandler() throws Exception {
        // Given, When, then
        mockMvc
                .perform(get("/no-handler-found").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("errorCode").value(CommonErrorCodes.NO_HANDLER_FOUND.getErrorCode()))
                .andExpect(jsonPath("message").value(CommonErrorCodes.NO_HANDLER_FOUND.getMessage()));
    }

    @Test
    @DisplayName("HttpRequestMethodNotSupportedException 예외가 발생하면 정의한 응답 코드와 에러 메시지가 출력된다")
    void testHttpRequestMethodNotSupportedExceptionHandler() throws Exception {
        // Given, When, then
        mockMvc
                .perform(get("/method-not-supported").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(
                        jsonPath("errorCode")
                                .value(CommonErrorCodes.HTTP_REQUEST_METHOD_NOT_SUPPORTED.getErrorCode()))
                .andExpect(
                        jsonPath("message")
                                .value(CommonErrorCodes.HTTP_REQUEST_METHOD_NOT_SUPPORTED.getMessage()));
    }

    @Test
    @DisplayName("NoResourceFoundException 예외가 발생하면 정의한 응답 코드와 에러 메시지가 출력된다")
    void testHandleNoResourceFoundException() throws Exception {
        // Given, When, then
        mockMvc
                .perform(get("/no-resource-found").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("errorCode").value(CommonErrorCodes.NO_RESOURCE_FOUND.getErrorCode()))
                .andExpect(jsonPath("message").value(CommonErrorCodes.NO_RESOURCE_FOUND.getMessage()));
    }

    @Test
    @DisplayName("IllegalArgumentException 예외가 발생하면 정의한 응답 코드와 에러 메시지가 출력된다")
    void testIllegalArgumentException() throws Exception {
        // Given, When, then
        mockMvc
                .perform(get("/illegal-argument").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(CommonErrorCodes.ILLEGAL_ARGUMENT.getErrorCode()))
                .andExpect(jsonPath("message").value(CommonErrorCodes.ILLEGAL_ARGUMENT.getMessage()));
    }

    @Test
    @DisplayName("IllegalStateException 예외가 발생하면 정의한 응답 코드와 에러 메시지가 출력된다")
    void testIllegalStateException() throws Exception {
        // Given, When, then
        mockMvc
                .perform(get("/illegal-state").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value(CommonErrorCodes.ILLEGAL_STATE.getErrorCode()))
                .andExpect(jsonPath("message").value(CommonErrorCodes.ILLEGAL_STATE.getMessage()));
    }

    @Test
    @DisplayName("Exception 예외가 발생하면 정의한 응답 코드와 에러 메시지가 출력된다")
    void testUnknownExceptionHandler() throws Exception {
        // Given, When, then
        mockMvc
                .perform(get("/unknown-error").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("errorCode").value(CommonErrorCodes.UNKNOWN.getErrorCode()))
                .andExpect(jsonPath("message").value(CommonErrorCodes.UNKNOWN.getMessage()));
    }
}
