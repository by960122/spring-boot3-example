package com.example.controller;

import java.util.*;
import java.util.concurrent.CompletableFuture;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.*;

import com.example.constant.GlobalResponseEnum;
import com.example.event.OrderEvent;
import com.example.exception.CustomException;
import com.example.mapper.MongoMapper;
import com.example.mapper.MysqlMapper;
import com.example.model.*;
import com.example.service.AsyncService;
import com.example.tool.JsonTools;
import com.example.tool.SpringTools;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: BYDylan
 * @date: 2022/1/31
 * @description: 注解 RestController = Controller + ResponseBody
 */
@Slf4j
@RestController
public class TestController {
    private final MysqlMapper mysqlMapper;
    private final MongoMapper mongoMapper;
    private final PersonModel personModel;
    private final AsyncService asyncService;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public TestController(MysqlMapper mysqlMapper, MongoMapper mongoMapper, PersonModel personModel,
        AsyncService asyncService, KafkaTemplate<String, Object> kafkaTemplate) {
        this.mysqlMapper = mysqlMapper;
        this.mongoMapper = mongoMapper;
        this.personModel = personModel;
        this.asyncService = asyncService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @GetMapping("/test/model")
    public Object testEntity() {
        log.info("person model: {}", JsonTools.toJsonString(personModel));
        Map<Object, Object> responseMap = new HashMap<>();
        responseMap.put("content", "test");
        responseMap.put("entity", personModel);
        return GlobalResponseModel.setResponse(GlobalResponseEnum.SUCCESS, Collections.singletonList(responseMap));
    }

    @GetMapping("/test/mongodb")
    public Object testMongodb() {
        // mongoMapper.createCollection("test");
        log.info("collection exists: {}", mongoMapper.collectionExists("test"));
        MongoModel insert = mongoMapper.insert();
        Collection<MongoModel> yyyy = mongoMapper.insertMany("yyyy");
        return GlobalResponseModel.setResponse(GlobalResponseEnum.SUCCESS, yyyy.stream().toList());
    }

    @GetMapping("/test/mysql")
    public Object testMysql() {
        List<String> tables = mysqlMapper.showDatabases();
        return GlobalResponseModel.setResponse(GlobalResponseEnum.SUCCESS, tables);
    }

    @GetMapping("/test/exception")
    public void testException() {
        throw new CustomException(GlobalResponseEnum.PARAMETER_ERROR, "request error");
    }

    @PostMapping("/test/validation")
    public Object testValidation(@RequestBody @Valid DogModel dog) {
        return GlobalResponseModel.setResponse(GlobalResponseEnum.SUCCESS, Collections.singletonList(dog));
    }

    @GetMapping("/test/async")
    public Object testAsync(@RequestParam(value = "count") Integer count) {
        for (int i = 1; i <= count; i++) {
            asyncService.execute(i);
        }
        return GlobalResponseModel.setResponse(GlobalResponseEnum.SUCCESS);
    }

    /**
     * 观察者模式
     */
    @PostMapping("/test/event")
    public Object testEvent(@RequestParam(value = "count") Integer count) {
        for (int index = 1; index <= count; index++) {
            OrderModel orderModel = new OrderModel();
            orderModel.setOrderId((long)index);
            orderModel.setBuyerName("Tom-" + index);
            orderModel.setSellerName("judy-" + index);
            orderModel.setAmount(100L);
            // 发布Spring事件通知
            log.info("product event id: {}", orderModel.getOrderId());
            SpringTools.getApplicationContext().publishEvent(new OrderEvent(orderModel));
        }
        return GlobalResponseModel.setResponse(GlobalResponseEnum.SUCCESS);
    }

    @GetMapping("/test/kafka")
    public Object testKafka() {
        for (int index = 0; index < 5; index++) {
            CompletableFuture<SendResult<String, Object>> future =
                kafkaTemplate.send("example_topic", String.valueOf(index), String.valueOf(index));

            future.thenAccept(result -> {
                ProducerRecord<String, Object> record = result.getProducerRecord();
                log.info("kafka product topic: {}, message: {}", record.topic(), record.value().toString());
            });
            future.exceptionally(e -> {
                log.info("kafka product error: ", e);
                return null;
            });
        }
        return GlobalResponseModel.setResponse(GlobalResponseEnum.SUCCESS);
    }
}
