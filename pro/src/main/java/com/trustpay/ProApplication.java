package com.trustpay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.TimeZone;

@SpringBootApplication
public class ProApplication {

	public static void main(String[] args) {
		// Enforce global distributed systems consistency using UTC
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

		SpringApplication.run(ProApplication.class, args);
	}
}