package com.trustpay.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
public class HealthCheckController {

    @GetMapping("/api/v1/health-check")
    public Map<String, String> healthCheck() {
        log.info("Health check API invoked");
        return Map.of("status", "TrustPay API is running");
    }
}