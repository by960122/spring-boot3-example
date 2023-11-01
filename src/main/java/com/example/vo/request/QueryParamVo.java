package com.example.vo.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author: BYDylan
 * @date: 2024-09-28 10:15:15
 * @description:
 */
@Data
public class QueryParamVo {
    @NotNull(message = "test_name cannot be null")
    @JsonProperty("test_name")
    private String testName;
}
