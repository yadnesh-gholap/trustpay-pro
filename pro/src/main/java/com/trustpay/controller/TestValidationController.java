package com.trustpay.controller;

import com.trustpay.common.dto.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/test")
public class TestValidationController {

    @PostMapping("/validate")
    public ResponseEntity<ApiResponse<String>> testRequestValidation(@Valid @RequestBody SampleTransactionDto request) {
        return ResponseEntity.ok(ApiResponse.success("Payload passed schema validations cleanly!", request.getEmail()));
    }

    // Inner DTO for quick testing purposes
    public static class SampleTransactionDto {
        @NotBlank(message = "Email address is a required field")
        @Email(message = "Invalid email formatting provided")
        private String email;

        @NotNull(message = "Transaction amount cannot be null")
        @Min(value = 1, message = "Transaction amount must be greater than or equal to 1")
        private Integer amount;

        // Getters and Setters
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public Integer getAmount() { return amount; }
        public void setAmount(Integer amount) { this.amount = amount; }
    }
}