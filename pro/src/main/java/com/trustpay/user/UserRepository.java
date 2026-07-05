package com.trustpay.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Crucial query method for our login validation checks
    Optional<User> findByEmail(String email);

    // Crucial query method for our sign-up duplicate check alerts
    boolean existsByEmail(String email);
}