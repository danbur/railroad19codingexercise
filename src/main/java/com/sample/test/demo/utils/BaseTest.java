package com.sample.test.demo.utils;


import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

@ContextConfiguration(locations = { "classpath:spring-test-config.xml" })
public class BaseTest extends AbstractTestNGSpringContextTests {

}
