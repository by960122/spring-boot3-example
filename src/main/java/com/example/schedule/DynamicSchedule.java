package com.example.schedule;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: BYDylan
 * @date: 2023/11/7
 * @description: 动态设置定时任务,后续可通过数据库或接口形式更新 cron 变量
 */
@Slf4j
@Component
public class DynamicSchedule implements SchedulingConfigurer {

    @Value("${cron.dynamic}")
    private String cron;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        // 动态使用cron表达式设置循环间隔
        taskRegistrar.addTriggerTask(() -> log.info("current time： {}", LocalDateTime.now()), triggerContext -> {
            // 使用CronTrigger触发器,可动态修改cron表达式来操作循环规则
            CronTrigger cronTrigger = new CronTrigger(cron);
            return cronTrigger.nextExecution(triggerContext);
        });
    }
}
