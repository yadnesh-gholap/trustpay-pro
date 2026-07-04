package com.trustpay.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {



        String token = extractToken(request);

        if (token != null) {
            try {
                jwtTokenProvider.validateToken(token);
                String username = jwtTokenProvider.getUsernameFromToken(token);
                log.info("Valid JWT received for username={}", username);
            } catch (Exception ex) {
                // FIX: Scrubbed raw exception message tracking for production security standard compliance
                log.warn("JWT validation failed");
            }
        }

        // 💡 Passes the request down the chain to the next security component or controller mapping
        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {

        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }
}