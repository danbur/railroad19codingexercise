package com.sample.test.demo.utils;


import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

/**
 * Base test class. Provides all necessary Spring and TestNG glue.
 */
@ContextConfiguration(locations = {"classpath:spring-test-config.xml"})
@EnableConfigurationProperties
@TestPropertySource("classpath:test.properties")
public class BaseTest extends AbstractTestNGSpringContextTests {

}
