package com.sample.test.demo.data.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonDeserialize(builder = Order.OrderBuilder.class)
@JsonInclude(Include.NON_EMPTY)
public class Order {

    private String id;
    private List<Pizza> items;

    @JsonPOJOBuilder(withPrefix = "")
    public static class OrderBuilder {}

}
