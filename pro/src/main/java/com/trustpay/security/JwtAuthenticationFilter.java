package com.trustpay.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    // Corporate Standard: Constructor Injection
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

                // 🛠️ FIX: Build the authenticated profile token wrapper principal
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                username,
                                null,
                                Collections.emptyList() // Staging an empty list of granted authorities/roles for now
                        );

                // Enforce tracking metadata from the current web servlet request context wrapper
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Commit the identity profile directly to the thread-local SecurityContextHolder log sheet
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception ex) {
                // Scrubbed raw exceptions to eliminate details disclosure risks in production metrics
                log.warn("JWT validation failed");
            }
        }

        // Passes the request down the chain to the next security component or controller mapping
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