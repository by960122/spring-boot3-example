package com.example.config;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: BYDylan
 * @date: 2022/10/17
 * @description: 全局线程池
 */
@Slf4j
@Configuration
public class ThreadPoolConfig {

    @Bean(name = "defaultThreadPoolExecutor", destroyMethod = "shutdown")
    public ThreadPoolExecutor defaultThreadPoolExecutor() {
        return new ThreadPoolExecutor(4, 8, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(10000),
            (r, executor) -> log.error("system pool is full!"));
    }
}
