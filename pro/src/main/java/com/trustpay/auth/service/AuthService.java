package com.trustpay.auth.service;

import com.trustpay.auth.dto.AuthResponse;
import com.trustpay.auth.dto.LoginRequest;
import com.trustpay.auth.dto.RegisterRequest;
import com.trustpay.security.JwtTokenProvider;
import com.trustpay.user.User;
import com.trustpay.user.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    // Corporate Constructor Dependency Injection
    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Transactional
    public void signup(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new IllegalArgumentException("An account with this email address already exists.");
        }

        String encryptedPassword = passwordEncoder.encode(registerRequest.getPassword());

        // Aligned with the app_user schema profile requirements
        User user = User.builder()
                .email(registerRequest.getEmail())
                .passwordHash(encryptedPassword)
                .mobile(registerRequest.getMobile())       // Map mobile string
                .firstName(registerRequest.getFirstName()) // Map first name string
                .lastName(registerRequest.getLastName())   // Map last name string
                .role("BUYER")
                .emailVerified(false)
                .mobileVerified(false)
                .kycStatus("PENDING")
                .isDeleted(false)
                .build();

        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Invalid email or password credentials provided."));

        // FIX: reading from user.getPasswordHash() instead of user.getPassword()
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPasswordHash())) {
            throw new BadCredentialsException("Invalid email or password credentials provided.");
        }

        String token = jwtTokenProvider.generateToken(user.getEmail());
        return new AuthResponse(user.getEmail(), token);
    }

}