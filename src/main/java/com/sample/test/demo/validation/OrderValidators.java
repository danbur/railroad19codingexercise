package com.sample.test.demo.validation;

import com.sample.test.demo.data.model.Order;
import io.restassured.mapper.TypeRef;
import io.restassured.response.Response;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

/**
 * Validation methods related to orders
 */
@Component
public class OrderValidators {
    /**
     * Verify the Get order by ID response matches and order that was previously created
     * @param expectedOrder The previously created order
     */
    public void verifyGetOrderByIdResponseMatchesOrderPreviouslyCreated(Response response, Order expectedOrder) {
        List<Order> getOrderResponse = response.then().assertThat().statusCode(200).extract().body()
                .as(new TypeRef<>() {
                });
        assertThat("Get order response should contain exactly one item", getOrderResponse, hasSize(1));
        assertThat("Get order response should match the order previously created", getOrderResponse.get(0),
                sameBeanAs(expectedOrder).ignoring("id"));
    }
}
