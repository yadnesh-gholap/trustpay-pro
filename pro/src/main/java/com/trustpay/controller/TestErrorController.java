package com.trustpay.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
public class TestErrorController {

   @GetMapping("/error")
    public void triggerSimulatedError(@RequestParam(value = "type", defaultValue = "runtime") String type) {
        String normalizedType = type != null ? type.trim() : "";

        if ("illegal".equalsIgnoreCase(normalizedType)) {
            throw new IllegalArgumentException("Simulated invalid argument exception");
        }

        throw new RuntimeException("Simulated internal runtime failure");
    }
}