package com.sample.test.demo.client;

import com.sample.test.demo.data.model.Order;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

import static io.restassured.RestAssured.given;

/**
 * Test client for orders. This class abstracts all order-related REST interactions.
 */
@Component
@Slf4j
public class OrderClient {
    private static final String ORDERS_URL = "/orders";
    private static final String ORDERS_BY_ID_URL = ORDERS_URL + "/{id}";

    @Inject
    private RequestSpecBuilder requestSpecBuilder;

    /**
     * Sends a get all orders request
     *
     * @return Response
     */
    public Response getAllOrders() {
        log.info("Getting all orders");
        return given(requestSpecBuilder.build()).get(ORDERS_URL);
    }

    /**
     * Sends a create a new order request
     *
     * @param order Order
     * @return Response
     */
    public Response createNewOrder(Order order) {
        log.info("Creating new order");
        log.debug("Order info: {}", order);
        return given(requestSpecBuilder.build()).body(order).post(ORDERS_URL);
    }

    /**
     * Sends a get all orders request
     *
     * @return Response
     */
    public Response getOrderById(String id) {
        log.info("Getting order by ID");
        return given(requestSpecBuilder.build()).get(ORDERS_BY_ID_URL, id);
    }
}
