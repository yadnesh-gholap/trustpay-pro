package com.trustpay.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotBlank(message = "Email field cannot be blank.")
    @Email(message = "Please provide a valid structural email address.")
    private String email;

    @NotBlank(message = "Password field cannot be blank.")
    private String password;
}