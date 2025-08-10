package com.example.SodokuBrainBackend.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Allows Endpoint request form frontend
     * @param registry  CorsRegistry for mapping
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Apply to all endpoints
                .allowedOrigins("http://localhost:5173") // Add all allowed origins
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Specify HTTP methods
                .allowedHeaders("*") // Allow all headers
                .allowCredentials(true); // Optional, for credentials support
    }
}