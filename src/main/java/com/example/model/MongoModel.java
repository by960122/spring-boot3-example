package com.example.model;

import java.util.Date;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * @author: BYDylan
 * @date: 2022/2/20
 * @description: monodb 实体类
 */
@Data
@Document(collection = "user")
public class MongoModel {
    @MongoId
    private String id;
    private String name;
    private String sex;
    /**
     * 将不需要序列化的属性前添加关键字transient,序列化对象的时候,这个属性就不会序列化到指定的目的地中
     */
    @Transient
    private Integer salary;
    private Integer age;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birthday;
    @Field("mark")
    private String remake;

    private StatusModel status;
}
