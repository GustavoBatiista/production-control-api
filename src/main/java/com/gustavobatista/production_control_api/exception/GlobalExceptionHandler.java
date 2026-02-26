package com.gustavobatista.production_control_api.exception;

import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.gustavobatista.production_control_api.logging.CorrelationIdFilter;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;

@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler {

        private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<ApiErrorResponse> handleNotFound(
                        ResourceNotFoundException ex,
                        HttpServletRequest request) {

                ApiErrorResponse error = new ApiErrorResponse(
                                HttpStatus.NOT_FOUND.value(),
                                ex.getMessage(),
                                request.getRequestURI(),
                                MDC.get(CorrelationIdFilter.TRACE_ID));

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }

        @ExceptionHandler(BusinessException.class)
        public ResponseEntity<ApiErrorResponse> handleBusiness(
                        BusinessException ex,
                        HttpServletRequest request) {

                ApiErrorResponse error = new ApiErrorResponse(
                                HttpStatus.BAD_REQUEST.value(),
                                ex.getMessage(),
                                request.getRequestURI(),
                                MDC.get(CorrelationIdFilter.TRACE_ID));

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ApiErrorResponse> handleGeneric(
                        Exception ex,
                        HttpServletRequest request) {

                log.error("Unexpected error", ex);

                ApiErrorResponse error = new ApiErrorResponse(
                                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                "Internal server error",
                                request.getRequestURI(),
                                MDC.get(CorrelationIdFilter.TRACE_ID));

                return ResponseEntity
                                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(error);
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ApiErrorResponse> handleValidation(
                        MethodArgumentNotValidException ex,
                        HttpServletRequest request) {

                String message = ex.getBindingResult().getFieldErrors().stream()
                                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                                .collect(Collectors.joining("; "));

                ApiErrorResponse error = new ApiErrorResponse(
                                HttpStatus.BAD_REQUEST.value(),
                                message,
                                request.getRequestURI(),
                                MDC.get(CorrelationIdFilter.TRACE_ID));

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
}