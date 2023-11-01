package com.example.controller;

import com.example.ExampleApplicationTests;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

/**
 * @author: BYDylan
 * @date: 2023/7/22
 * @description:
 */
@Slf4j
class TestControllerTest extends ExampleApplicationTests {

    @Test
    @Order(1)
    void modelTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/test/model").header("Authorization", "123")
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andDo(MockMvcResultHandlers.print()).andReturn();
        log.info("mock request mode response status: {}", mvcResult.getResponse().getStatus());
        log.info("mock request mode response content: {}", mvcResult.getResponse().getContentAsString());
    }
}