package com.example.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

/**
 * @author: BYDylan
 * @date: 2022/2/7
 * @description:
 */
@Data
public class DogModel {

    @NotBlank(message = "the name can not be empty")
    private String name;

    @PositiveOrZero(message = "the age must be a positive number")
    @Max(message = "the age must be less than 9", value = 9)
    private int age;

}
