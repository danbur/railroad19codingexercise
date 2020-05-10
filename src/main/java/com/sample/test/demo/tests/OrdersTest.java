package com.sample.test.demo.tests;

import com.sample.test.demo.client.OrderClient;
import com.sample.test.demo.data.model.CreateOrderResponse;
import com.sample.test.demo.data.model.Order;
import com.sample.test.demo.data.model.Pizza;
import com.sample.test.demo.utils.BaseTest;
import com.sample.test.demo.utils.TestConstants;
import io.restassured.mapper.TypeRef;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.util.List;

import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

@Slf4j
public class OrdersTest extends BaseTest {

    @Inject
    private OrderClient orderClient;

    @Test
    public void getAllOrders() {
        orderClient.getAllOrders().then().assertThat().statusCode(200);
    }

    @Test
        // This test fails, as the order never gets created, and it gets a 404 on the GET order by ID request
    void createOrderWithOnePizzaAndOneTopping() {
        log.info("Testing the creation of an order with a single pizza and one topping");
        Pizza pizza = Pizza.builder().pizza(TestConstants.SMALL_SIX_SLICE_ONE_TOPPING)
                .toppings(List.of(TestConstants.SALAMI)).build();
        Order order = Order.builder().items(List.of(pizza)).build();
        CreateOrderResponse createOrderResponse = orderClient.createNewOrder(order).then().assertThat().statusCode(201)
                .extract().body().as(CreateOrderResponse.class);
        List<Order> getOrderResponse = orderClient.getOrderById(createOrderResponse.getId()).then().assertThat()
                .statusCode(200).extract().body().as(new TypeRef<>() {
                });
        assertThat("Get order response should contain exactly one item", getOrderResponse, hasSize(1));
        assertThat("Get order response should match the order created", getOrderResponse.get(0),
                sameBeanAs(order).ignoring("id"));
    }

}
