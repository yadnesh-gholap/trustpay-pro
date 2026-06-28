package com.trustpay.common;

import com.trustpay.common.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class HealthCheckController {

    @GetMapping("/health-check")
    public ResponseEntity<ApiResponse<Map<String, String>>> checkHealth() {
        Map<String, String> statusDetails = Map.of(
                "status", "UP",
                "database", "CONNECTED"
        );

        // Wrap the payload using our static success utility helper
        ApiResponse<Map<String, String>> response = ApiResponse.success(
                "System engine is fully operational.",
                statusDetails
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/protected-test")
    public ResponseEntity<String> testProtected() {
        return ResponseEntity.ok("If you can see this, the security wall is broken!");
    }
}