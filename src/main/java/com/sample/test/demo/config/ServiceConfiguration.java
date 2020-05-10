package com.sample.test.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Service configuration. Provides access to the test properties such as the service base URI.
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "service.config")
public class ServiceConfiguration {
    private String baseUri;
}
