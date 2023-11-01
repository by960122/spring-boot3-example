package com.example.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * @author: BYDylan
 * @date: 2022/2/5
 * @description: 测试属性注入
 */
@Data
@Component
@ConfigurationProperties(prefix = "person")
public class PersonModel {
    private String lastName;
    private int age;
    private boolean boss;
    private Date birth;
    private Map<String, Object> maps;
    private List<Object> lists;
    private DogModel dog;
}
