package com.sample.test.demo.data.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(Include.NON_EMPTY)
public class Order {

    private String id = "";
    private List<Pizza> items;

}
