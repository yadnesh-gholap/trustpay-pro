package com.trustpay.common.dto;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import org.slf4j.MDC;

public class ValidationErrorResponse {
    private boolean success;
    private String message;
    private Map<String, String> errors; // Holds field names and their specific error messages
    private String timestamp;
    private String correlationId;

    public ValidationErrorResponse(String message, Map<String, String> errors) {
        this.success = false;
        this.message = message;
        this.errors = errors;
        this.timestamp = ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT);

        String activeCorrelationId = MDC.get("correlationId");
        this.correlationId = (activeCorrelationId != null) ? activeCorrelationId : "SYSTEM-UNKNOWN";
    }

    // Getters and Setters
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Map<String, String> getErrors() { return errors; }
    public void setErrors(Map<String, String> errors) { this.errors = errors; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    public String getCorrelationId() { return correlationId; }
    public void setCorrelationId(String correlationId) { this.correlationId = correlationId; }
}