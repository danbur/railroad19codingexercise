package com.sample.test.demo.tests;

import com.sample.test.demo.client.OrderClient;
import com.sample.test.demo.utils.BaseTest;
import org.testng.annotations.Test;

import javax.inject.Inject;

public class OrdersTest extends BaseTest {

    @Inject
    private OrderClient orderClient;

    @Test
    public void getAllOrders() {
        orderClient.getAllOrders().then().assertThat().statusCode(200);
    }

}
