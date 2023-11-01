package com.example.runner;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.example.model.PersonModel;
import com.example.tool.JsonTools;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: BYDylan
 * @date: 2022/2/20
 * @description: 项目启动后, 自动运行初始化一些配置项之类的.注解 @Order ,当有多个实现时,value 越小,优先级越高
 */
@Slf4j
@Order(value = 1)
@Component
public class TestRunner implements ApplicationRunner {
    private final PersonModel personModel;

    public TestRunner(PersonModel personModel) {
        this.personModel = personModel;
    }

    @Override
    public void run(ApplicationArguments args) {
        log.info("test runner: {}", JsonTools.toJsonString(personModel));
    }
}
