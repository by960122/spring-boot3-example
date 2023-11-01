package com.example.controller;

import java.util.concurrent.CompletableFuture;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.constant.GlobalResponseEnum;
import com.example.model.GlobalResponseModel;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: BYDylan
 * @date: 2024-08-10 15:20:37
 * @description:
 */
@Slf4j
@RestController
public class KafkaController {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaController(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
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
