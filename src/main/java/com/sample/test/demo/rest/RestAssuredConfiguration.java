package com.sample.test.demo.rest;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.test.demo.config.ServiceConfiguration;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
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
    ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
        // Disable fail on unknown properties, since we have specific tests to validate the JSON schema
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

    @Bean
    public RequestSpecBuilder getRequestSpecBuilder(ObjectMapper objectMapper) {
        ObjectMapperConfig objectMapperConfig = new ObjectMapperConfig().jackson2ObjectMapperFactory((cls, charset) ->
                objectMapper);
        RestAssuredConfig restAssuredConfig = RestAssuredConfig.newConfig().objectMapperConfig(objectMapperConfig);
        return new RequestSpecBuilder().setBaseUri(serviceConfiguration.getBaseUri())
                .setConfig(restAssuredConfig);
    }
}
