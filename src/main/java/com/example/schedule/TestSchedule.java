package com.example.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.annotation.CustomDataSource;
import com.example.model.PersonModel;
import com.example.tool.JsonTools;

import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;

/**
 * @author: BYDylan
 * @date: 2022/2/20
 * @description: 测试定时调度 lockAtLeastFor: 持有锁最少时间 lockAtMostFor: 持有锁最长时间
 */
@Slf4j
@Component
public class TestSchedule {
    private final PersonModel personModel;

    public TestSchedule(PersonModel personModel) {
        this.personModel = personModel;
    }

    @Scheduled(cron = "0 0/30 * * * ?", zone = "Asia/Shanghai")
    @SchedulerLock(name = "testSchedule", lockAtLeastFor = "PT30S", lockAtMostFor = "PT1M")
    @CustomDataSource()
    public void testSchedule() {
        log.info("test schedule: {}", JsonTools.toJsonString(personModel));
    }
}
