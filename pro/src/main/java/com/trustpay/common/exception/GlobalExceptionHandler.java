package com.trustpay.common.exception;

import com.trustpay.common.dto.ErrorResponse;
import com.trustpay.common.dto.ValidationErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.FieldError;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private static final String CORRELATION_ID_KEY = "correlationId";

    // 1. Specific Handler for IllegalArgumentException (HTTP 400 Bad Request)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
        String correlationId = getCorrelationIdOrFallback();
        log.error("[{}] IllegalArgumentException intercepted: {}", correlationId, ex.getMessage());

        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage(), // This will show "Simulated invalid argument exception"
                request.getRequestURI(),
                correlationId
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // 2. Specific Handler for RuntimeException (HTTP 500 Internal Server Error)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex, HttpServletRequest request) {
        String correlationId = getCorrelationIdOrFallback();
        log.error("[{}] RuntimeException intercepted: {}", correlationId, ex.getMessage());

        ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                ex.getMessage(), // This will show "Simulated internal runtime failure"
                request.getRequestURI(),
                correlationId
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 3. Absolute Fallback for all other checked Exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, HttpServletRequest request) {
        String correlationId = getCorrelationIdOrFallback();
        log.error("[{}] Unhandled Exception wrapper caught: {}", correlationId, ex.getMessage(), ex);

        ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                ex.getMessage() != null ? ex.getMessage() : "An unexpected platform error occurred.",
                request.getRequestURI(),
                correlationId
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 0. Handle Request Body Validation Failures (HTTP 400 Bad Request)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        String correlationId = getCorrelationIdOrFallback();
        log.error("[{}] Request payload validation failed.", correlationId);

        // Map to hold our field-level validation errors
        Map<String, String> validationErrors = new HashMap<>();

        // Loop through all validation errors caught by Spring
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            validationErrors.put(fieldName, errorMessage);
        });

        ValidationErrorResponse errorBody = new ValidationErrorResponse(
                "Validation failed for incoming request parameters.",
                validationErrors
        );

        return new ResponseEntity<>(errorBody, HttpStatus.BAD_REQUEST);
    }

    


    private String getCorrelationIdOrFallback() {
        String correlationId = MDC.get(CORRELATION_ID_KEY);
        return (correlationId != null) ? correlationId : "SYSTEM-UNKNOWN";
    }
}