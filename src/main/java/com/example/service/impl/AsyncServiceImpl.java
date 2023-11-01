package com.example.service.impl;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.service.AsyncService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: BYDylan
 * @date: 2022/10/17
 * @description:
 */
@Slf4j
@Service
public class AsyncServiceImpl implements AsyncService {

    @Async("defaultThreadPoolExecutor")
    @Override
    public void execute(Integer id) {
        log.info("exec aysnc task id: {}", id);
    }
}
