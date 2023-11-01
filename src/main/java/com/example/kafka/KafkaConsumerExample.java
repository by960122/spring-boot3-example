package com.example.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: BYDylan
 * @date: 2023/12/2
 * @description: kafka消费
 */
@Slf4j
@Component
public class KafkaConsumerExample {
    @KafkaListener(topics = {"example_topic"})
    public void listenExample(@Payload String data, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
        @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition, @Header(KafkaHeaders.RECEIVED_KEY) Integer key,
        @Header(KafkaHeaders.RECEIVED_TIMESTAMP) Long ts) {
        log.info("kafka consume topic: {}, partition: {}, key: {}, ts: {}, data: {}", topic, partition, key, ts, data);
    }
}
