package com.trustpay.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "Email field cannot be blank.")
    @Email(message = "Please provide a valid structural email address.")
    private String email;

    @NotBlank(message = "Password field cannot be blank.")
    @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters long.")
    private String password;

    @NotBlank(message = "Mobile number cannot be blank.")
    @Size(min = 10, max = 15, message = "Mobile number must be between 10 and 15 digits.")
    private String mobile;

    @NotBlank(message = "First name cannot be blank.")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank.")
    private String lastName;
}