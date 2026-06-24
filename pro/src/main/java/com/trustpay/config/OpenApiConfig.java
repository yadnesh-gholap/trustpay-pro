package com.trustpay.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI trustPayOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("TrustPay Pro API")
                        .description("Backend API documentation for TrustPay Pro escrow platform")
                        .version("v1")
                        .contact(new Contact()
                                .name("TrustPay Backend Team")));
    }
}