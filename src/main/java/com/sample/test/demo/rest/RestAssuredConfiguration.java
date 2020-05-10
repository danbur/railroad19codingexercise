package com.sample.test.demo.rest;

import com.sample.test.demo.config.ServiceConfiguration;
import io.restassured.builder.RequestSpecBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;

/**
 * Spring dependency injection configuration for Rest Assured. Provides a RequestSpecBuilder populated with the service
 * base URI.
 */
@Configuration()
public class RestAssuredConfiguration {
    @Inject
    ServiceConfiguration serviceConfiguration;

    @Bean
    public RequestSpecBuilder getRequestSpecBuilder() {
        return new RequestSpecBuilder().setBaseUri(serviceConfiguration.getBaseUri());
    }
}
