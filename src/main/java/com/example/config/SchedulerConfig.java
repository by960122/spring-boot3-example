package com.example.config;

import java.util.TimeZone;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.jdbctemplate.JdbcTemplateLockProvider;

/**
 * @author: BYDylan
 * @date: 2022/11/6
 * @description:
 */
@Slf4j
@Configuration
public class SchedulerConfig {

    @Bean
    public TimeZone timeZone() {
        return TimeZone.getTimeZone("Asia/Shanghai");
    }

    /**
     * shedlock分布式锁-MySQL
     */
    @Bean
    public LockProvider lockProvider(DataSource dataSource) {
        JdbcTemplateLockProvider.Configuration configuration = JdbcTemplateLockProvider.Configuration.builder()
            .withJdbcTemplate(new JdbcTemplate(dataSource)).withTimeZone(TimeZone.getTimeZone("Asia/Shanghai")).build();
        return new JdbcTemplateLockProvider(configuration);
    }

    // /**
    // * shedlock分布式锁-Redis
    // */
    // @Bean
    // public LockProvider lockProvider(RedisTemplate redisTemplate) {
    // return new RedisLockProvider(redisTemplate.getConnectionFactory(), "shedlock");
    // }

    /**
     * 设置定时任务线程池
     */
    @Bean
    public TaskScheduler scheduledExecutorService() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(3);
        scheduler.setThreadNamePrefix("scheduled-thread-");
        scheduler.setErrorHandler(throwable -> log.error("scheduled thread error: {}", throwable.getMessage()));
        scheduler.setRejectedExecutionHandler((runnable, executor) -> log.error(
            "scheduled thread reject error: {}, scheduled count: {}.", runnable.toString(), executor.getActiveCount()));
        return scheduler;
    }
}
