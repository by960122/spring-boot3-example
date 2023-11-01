package com.example.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ConsumerAwareListenerErrorHandler;

import com.example.kafka.KafkaConsumerExample;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: BYDylan
 * @date: 2023/12/2
 * @description: kafka 消费端配置: 可以采用默认的 spring.kafka.consumer
 */
@Slf4j
@Configuration
public class KafkaConsumerConfig {

    @Value("${kafka.enabled:true}")
    private boolean kafkaEnabled;
    @Value("${kafka.consumer.bootstrap-servers}")
    private String servers;
    @Value("${kafka.consumer.enable-auto-commit}")
    private boolean enableAutoCommit;
    @Value("${kafka.consumer.auto-offset-reset}")
    private String autoOffsetReset;
    @Value("${kafka.consumer.auto-commit-interval}")
    private String autoCommitInterval;
    @Value("${kafka.consumer.group-id}")
    private String groupId;
    @Value("${kafka.consumer.session.timeout}")
    private int sessionTimeout;
    @Value("${kafka.consumer.heartbeat-interval}")
    private int heartInterval;
    @Value("${kafka.consumer.concurrency}")
    private int concurrency;
    // 一次调用poll()操作时返回的最大记录数,默认值为500
    @Value("${kafka.consumer.max-poll-records}")
    private int maxPollRecords;

    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        // 消费者参数设置
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit);
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, autoCommitInterval);
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeout);
        props.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, heartInterval);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
        // 批量消费
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, maxPollRecords);
        return props;
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>>
        kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
            new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        // 禁止自动启动
        factory.setAutoStartup(kafkaEnabled);
        // 被过滤的消息将被丢弃
        factory.setAckDiscarded(true);
        // 消息过滤策略（将消息转换为long类型,判断是奇数还是偶数,把所有奇数过滤,监听器只接收偶数）
        factory.setRecordFilterStrategy(consumerRecord -> {
            long data = Long.parseLong(consumerRecord.value());
            // 返回true将会被丢弃
            return data % 2 != 0;
        });
        return factory;
    }

    /**
     * @return kafka监听
     */
    @Bean
    public KafkaConsumerExample listener() {
        return new KafkaConsumerExample();
    }

    /**
     * @return 异常处理器
     */
    @Bean
    public ConsumerAwareListenerErrorHandler kafkaErrorHandler() {
        return (message, exception, consumer) -> {
            log.info("kafka consumer handler: {}, message: {}", message.getPayload(), exception);
            return null;
        };
    }
}
