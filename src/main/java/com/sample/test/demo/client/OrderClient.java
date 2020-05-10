package com.sample.test.demo.client;

import com.sample.test.demo.config.ServiceConfiguration;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

import static io.restassured.RestAssured.given;

@Component
@Slf4j
public class OrderClient {
    private static final String ORDERS_URL = "/orders";

    @Inject
    private ServiceConfiguration serviceConfiguration;

    public Response getAllOrders() {
        log.info("Getting all orders");
        return given().baseUri(serviceConfiguration.getBaseUri()).when().get(ORDERS_URL);
    }
}
