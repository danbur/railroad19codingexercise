package com.sample.test.demo.tests;

import com.sample.test.demo.client.OrderClient;
import com.sample.test.demo.data.model.CreateOrderResponse;
import com.sample.test.demo.data.model.Order;
import com.sample.test.demo.data.model.Pizza;
import com.sample.test.demo.utils.BaseTest;
import com.sample.test.demo.validation.OrderResponseValidators;
import io.restassured.mapper.TypeRef;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

import static com.sample.test.demo.utils.TestConstants.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

@Slf4j
public class OrdersTest extends BaseTest {

    @Inject
    private OrderClient orderClient;
    @Inject
    private OrderResponseValidators orderResponseValidators;

    @Test
    public void getAllOrders() {
        log.info("Getting all orders and verifying the response format");
        orderClient.getAllOrders().then().assertThat().statusCode(200)
                .body(matchesJsonSchemaInClasspath(GET_ALL_ORDERS_SCHEMA_FILE));
    }

    @Test
    public void getAllOrdersContainsPreviouslyCreatedOrder() {
        // Test test assumes that we know the ID of an order already created before the tests run
        // This test fails, because the GET order by ID response is a single item, while the API specification states
        // that it should be an array containing that item
        log.info("Verifying the get all orders response contains a previously created order");
        Order firstOrder = orderClient.getOrderById(FIRST_ORDER_ID).then().statusCode(200)
                .extract().body().as(new TypeRef<List<Order>>() {
                }).get(0);
        List<Order> allOrders = orderClient.getAllOrders().then().assertThat().statusCode(200).extract()
                .as(new TypeRef<>() {
                });
        assertThat("The get all orders response should contain the previously created order", allOrders,
                hasItem(firstOrder));
    }

    @Test
    public void getOrderById() {
        // This test assumes some orders have already been created before the tests run
        // This test fails, because the GET order by ID response is a single item, while the API specification states
        // that it should be an array containing that item
        log.info("Retrieving the first order in the list by ID and validating the response");
        Order firstOrder = orderClient.getAllOrders().then().assertThat().statusCode(200).extract()
                .as(new TypeRef<List<Order>>() {
                }).get(0);
        List<Order> getOrderByIdResponse = orderClient.getOrderById(firstOrder.getId()).then().statusCode(200)
                .body(matchesJsonSchemaInClasspath(GET_ORDER_BY_ID_SCHEMA_FILE))
                .extract().body().as(new TypeRef<>() {
                });
        assertThat("Get order response should contain exactly one item", getOrderByIdResponse, hasSize(1));
        assertThat("Get order by ID response should contain the first order created",
                getOrderByIdResponse.get(0), equalTo(firstOrder));
    }

    @Test
    void createOrderAndVerifyResponseFormat() {
        log.info("Sending a create order request and verifying the response format");
        Pizza pizza = Pizza.builder().pizza(SMALL_SIX_SLICE_ONE_TOPPING).toppings(List.of(PEPPERONI)).build();
        Order order = Order.builder().items(List.of(pizza)).build();
        orderClient.createNewOrder(order).then().assertThat().statusCode(201)
                .body(matchesJsonSchemaInClasspath(CREATE_ORDER_RESPONSE_SCHEMA_FILE));
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
        orderResponseValidators.verifyGetOrderByIdResponseMatchesOrderPreviouslyCreated(getOrderByIdResponse, order);
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
        orderResponseValidators.verifyGetOrderByIdResponseMatchesOrderPreviouslyCreated(getOrderByIdResponse, order);
    }

    @Test
    void createOrderWithMultiplePizzas() {
        // This test fails, as the order never gets created, and it gets a 404 on the GET order by ID request
        log.info("Testing the creation of an order with multiple pizzas");
        Pizza pizza1 = Pizza.builder().pizza(SMALL_SIX_SLICE_ONE_TOPPING).toppings(List.of(SALAMI)).build();
        Pizza pizza2 = Pizza.builder().pizza(MEDIUM_EIGHT_SLICE_TWO_TOPPINGS)
                .toppings(List.of(PEPPERONI, MUSHROOMS)).build();
        Pizza pizza3 = Pizza.builder().pizza(LARGE_TEN_SLICE_NO_TOPPINGS)
                .toppings(Collections.emptyList()).build();
        Order order = Order.builder().items(List.of(pizza1, pizza2, pizza3)).build();
        CreateOrderResponse createOrderResponse = orderClient.createNewOrder(order).then().assertThat().statusCode(201)
                .extract().body().as(CreateOrderResponse.class);
        Response getOrderByIdResponse = orderClient.getOrderById(createOrderResponse.getId());
        orderResponseValidators.verifyGetOrderByIdResponseMatchesOrderPreviouslyCreated(getOrderByIdResponse, order);
    }

    @Test
    void createOrderWithPizzaNotSpecifiedResultsInError() {
        // This test fails, as the order is accepted by the endpoint, despite having no pizza specified
        log.info("Verifying that attempting to create an order without a pizza type specified results in an error");
        Pizza pizza1 = Pizza.builder().pizza(SMALL_SIX_SLICE_ONE_TOPPING).toppings(List.of(SALAMI)).build();
        Pizza pizza2 = Pizza.builder().pizza(null).toppings(List.of(PEPPERONI, MUSHROOMS)).build();
        Pizza pizza3 = Pizza.builder().pizza(LARGE_TEN_SLICE_NO_TOPPINGS)
                .toppings(Collections.emptyList()).build();
        Order order = Order.builder().items(List.of(pizza1, pizza2, pizza3)).build();
        orderClient.createNewOrder(order).then().assertThat().statusCode(408);
    }
}
