package com.example.controller;

import java.util.Collection;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.constant.GlobalResponseEnum;
import com.example.mapper.MongoMapper;
import com.example.model.GlobalResponseModel;
import com.example.model.MongoModel;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: BYDylan
 * @date: 2024-08-10 16:16:35
 * @description:
 */
@Slf4j
@RestController
@ConditionalOnProperty(name = "spring.datasource.mongodb.enabled", havingValue = "true")
public class MongoDBController {

    private final MongoMapper mongoMapper;

    public MongoDBController(MongoMapper mongoMapper) {
        this.mongoMapper = mongoMapper;
    }

    @GetMapping("/test/mongodb")
    public Object testMongodb() {
        // mongoMapper.createCollection("test");
        log.info("collection exists: {}", mongoMapper.collectionExists("test"));
        MongoModel insert = mongoMapper.insert();
        Collection<MongoModel> yyyy = mongoMapper.insertMany("yyyy");
        return GlobalResponseModel.setResponse(GlobalResponseEnum.SUCCESS, yyyy.stream().toList());
    }
}
