package com.trustpay.auth.controller;

import com.trustpay.auth.dto.AuthResponse;
import com.trustpay.auth.dto.LoginRequest;
import com.trustpay.auth.dto.RegisterRequest;
import com.trustpay.auth.service.AuthService;
import com.trustpay.common.dto.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<String>> signup(@Valid @RequestBody RegisterRequest registerRequest) {
        authService.signup(registerRequest);

        // FIX: Passed 'true' as the first parameter to match (boolean success, String message, T data)
        ApiResponse<String> response = new ApiResponse<>(
                true,
                "User registered successfully. Cryptographic profile initialized.",
                "Account created successfully."
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        AuthResponse authResponse = authService.login(loginRequest);

        // FIX: Passed 'true' as the first parameter here as well
        ApiResponse<AuthResponse> response = new ApiResponse<>(
                true,
                "Authentication successful. Signed access bearer token issued.",
                authResponse
        );

        return ResponseEntity.ok(response);
    }
}