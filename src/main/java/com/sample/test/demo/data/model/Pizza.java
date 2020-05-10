package com.sample.test.demo.data.model;

import java.util.List;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Data
@JsonInclude(Include.NON_EMPTY)
public class Pizza {

    private String item;
    private String pizza;
    private List<String> toppings;

}
