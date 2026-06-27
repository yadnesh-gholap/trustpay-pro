package com.trustpay.common.dto;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import org.slf4j.MDC;

public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private String timestamp;
    private String correlationId;

    // No-args constructor for JSON serialization frameworks
    public ApiResponse() {
        this.timestamp = ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT);
        this.correlationId = MDC.get("correlationId");
    }

    // Comprehensive constructor
    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.timestamp = ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT);

        // Extract the active tracking ID automatically
        String activeCorrelationId = MDC.get("correlationId");
        this.correlationId = (activeCorrelationId != null) ? activeCorrelationId : "SYSTEM-UNKNOWN";
    }

    // Static utility helpers to quickly build success wrappers
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data);
    }

    public static <T> ApiResponse<T> success(String message) {
        return new ApiResponse<>(true, message, null);
    }

    // Getters and Setters
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public T getData() { return data; }
    public void setData(T data) { this.data = data; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    public String getCorrelationId() { return correlationId; }
    public void setCorrelationId(String correlationId) { this.correlationId = correlationId; }
}