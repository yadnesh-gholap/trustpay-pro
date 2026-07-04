package com.trustpay.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trustpay.common.dto.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    public JwtAuthenticationEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws ServletException, IOException {

        String correlationId = MDC.get("correlationId");
        String activeCorrelationId = (correlationId != null) ? correlationId : "SYSTEM-UNKNOWN";

        // Fixed: Provided exactly 5 arguments to match your (int, String, String, String, String) constructor signature
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                "Authentication is required to access this resource.",
                request.getRequestURI(),
                Instant.now().toString()
        );

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");

        objectMapper.writeValue(response.getOutputStream(), errorResponse);
    }
}