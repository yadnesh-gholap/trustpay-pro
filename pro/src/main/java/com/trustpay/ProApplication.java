package com.trustpay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Collections;
import java.util.TimeZone;

@SpringBootApplication
public class ProApplication {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kolkata"));
		SpringApplication app = new SpringApplication(ProApplication.class);
		app.setDefaultProperties(Collections.singletonMap("spring.profiles.active", "local"));
		app.run(args);
	}
}