package com.trustpay.user;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "app_user") // Corporate Standard: Mapping directly to the pre-existing V1 schema table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    private String mobile;

    @Column(name = "password_hash", nullable = false) // Maps to password_hash column
    private String passwordHash;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String role;

    @Column(name = "email_verified")
    private Boolean emailVerified;

    @Column(name = "mobile_verified")
    private Boolean mobileVerified;

    @Column(name = "kyc_status")
    private String kycStatus;

    @Column(name = "is_deleted")
    private Boolean isDeleted;
}