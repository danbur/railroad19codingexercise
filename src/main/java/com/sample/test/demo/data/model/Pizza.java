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
@JsonDeserialize(builder = Pizza.PizzaBuilder.class)
@JsonInclude(Include.NON_EMPTY)
public class Pizza {

    private String item;
    private String pizza;
    private List<String> toppings;

    @JsonPOJOBuilder(withPrefix = "")
    public static class PizzaBuilder {}
}
