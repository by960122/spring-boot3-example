package com.example.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.example.event.OrderEvent;
import com.example.tool.JsonTools;

import lombok.extern.slf4j.Slf4j;

/***
 * @author: BYDylan
 * @date: 2022/10/18
 * @description: 相当于 MQ 的消费端
 */
@Slf4j
@Component
public class OrderEventListener implements ApplicationListener<OrderEvent> {

    @Override
    public void onApplicationEvent(OrderEvent event) {
        log.info("comsumer event: {}", JsonTools.toJsonString(event.getSource()));
    }
}
