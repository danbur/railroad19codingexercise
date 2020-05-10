package com.sample.test.demo.tests;

import com.sample.test.demo.client.OrderClient;
import com.sample.test.demo.data.model.CreateOrderResponse;
import com.sample.test.demo.data.model.Order;
import com.sample.test.demo.data.model.Pizza;
import com.sample.test.demo.utils.BaseTest;
import com.sample.test.demo.utils.TestConstants;
import com.sample.test.demo.validation.OrderValidators;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

import static com.sample.test.demo.utils.TestConstants.*;

@Slf4j
public class OrdersTest extends BaseTest {

    @Inject
    private OrderClient orderClient;
    @Inject
    private OrderValidators orderValidators;

    @Test
    public void getAllOrders() {
        orderClient.getAllOrders().then().assertThat().statusCode(200);
    }

    @Test
    void createOrderWithOnePizzaAndOneTopping() {
        // This test fails, as the order never gets created, and it gets a 404 on the GET order by ID request
        log.info("Testing the creation of an order with a single pizza and one topping");
        Pizza pizza = Pizza.builder().pizza(SMALL_SIX_SLICE_ONE_TOPPING).toppings(List.of(SALAMI)).build();
        Order order = Order.builder().items(List.of(pizza)).build();
        CreateOrderResponse createOrderResponse = orderClient.createNewOrder(order).then().assertThat().statusCode(201)
                .extract().body().as(CreateOrderResponse.class);
        Response getOrderByIdResponse = orderClient.getOrderById(createOrderResponse.getId());
        orderValidators.verifyGetOrderByIdResponseMatchesOrderPreviouslyCreated(getOrderByIdResponse, order);
    }

    @Test
    void createOrderWithOnePizzaAndMultipleToppings() {
        // This test fails, as the order never gets created, and it gets a 404 on the GET order by ID request
        log.info("Testing the creation of an order with a single pizza and multiple toppings");
        Pizza pizza = Pizza.builder().pizza(MEDIUM_EIGHT_SLICE_TWO_TOPPINGS)
                .toppings(List.of(PEPPERONI, MUSHROOMS)).build();
        Order order = Order.builder().items(List.of(pizza)).build();
        CreateOrderResponse createOrderResponse = orderClient.createNewOrder(order).then().assertThat().statusCode(201)
                .extract().body().as(CreateOrderResponse.class);
        Response getOrderByIdResponse = orderClient.getOrderById(createOrderResponse.getId());
        orderValidators.verifyGetOrderByIdResponseMatchesOrderPreviouslyCreated(getOrderByIdResponse, order);
    }

    @Test
    void createOrderWithMultiplePizzas() {
        // This test fails, as the order never gets created, and it gets a 404 on the GET order by ID request
        log.info("Testing the creation of an order with multiple pizzas");
        Pizza pizza1 = Pizza.builder().pizza(SMALL_SIX_SLICE_ONE_TOPPING).toppings(List.of(SALAMI)).build();
        Pizza pizza2 = Pizza.builder().pizza(MEDIUM_EIGHT_SLICE_TWO_TOPPINGS)
                .toppings(List.of(PEPPERONI, MUSHROOMS)).build();
        Pizza pizza3 = Pizza.builder().pizza(TestConstants.LARGE_TEN_SLICE_NO_TOPPINGS)
                .toppings(Collections.emptyList()).build();
        Order order = Order.builder().items(List.of(pizza1, pizza2, pizza3)).build();
        CreateOrderResponse createOrderResponse = orderClient.createNewOrder(order).then().assertThat().statusCode(201)
                .extract().body().as(CreateOrderResponse.class);
        Response getOrderByIdResponse = orderClient.getOrderById(createOrderResponse.getId());
        orderValidators.verifyGetOrderByIdResponseMatchesOrderPreviouslyCreated(getOrderByIdResponse, order);
    }
}
