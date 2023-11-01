package com.example.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

import com.example.constant.GlobalResponseEnum;
import com.example.event.OrderEvent;
import com.example.exception.CustomException;
import com.example.model.DogModel;
import com.example.model.GlobalResponseModel;
import com.example.model.OrderModel;
import com.example.model.PersonModel;
import com.example.service.AsyncService;
import com.example.tool.JsonTools;
import com.example.tool.SpringTools;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: BYDylan
 * @date: 2022/1/31
 * @description: 注解 RestController = Controller + ResponseBody
 */
@Slf4j
@RestController
public class TestController {
    private final PersonModel personModel;
    private final AsyncService asyncService;

    public TestController(PersonModel personModel, AsyncService asyncService) {
        this.personModel = personModel;
        this.asyncService = asyncService;
    }

    @GetMapping("/test/response")
    public Object testEntity() {
        log.info("person model: {}", JsonTools.toJsonString(personModel));
        Map<Object, Object> responseMap = new HashMap<>();
        responseMap.put("content", "test");
        responseMap.put("entity", personModel);
        return GlobalResponseModel.setResponse(GlobalResponseEnum.SUCCESS, Collections.singletonList(responseMap));
    }

    @GetMapping("/test/exception")
    public void testException() {
        throw new CustomException(GlobalResponseEnum.PARAMETER_ERROR, "request error");
    }

    @PostMapping("/test/validation")
    public Object testValidation(@RequestBody @Valid DogModel dog) {
        return GlobalResponseModel.setResponse(GlobalResponseEnum.SUCCESS, Collections.singletonList(dog));
    }

    @GetMapping("/test/async")
    public Object testAsync(@RequestParam(value = "count") Integer count) {
        for (int i = 1; i <= count; i++) {
            asyncService.execute(i);
        }
        return GlobalResponseModel.setResponse(GlobalResponseEnum.SUCCESS);
    }

    /**
     * 观察者模式
     */
    @PostMapping("/test/event")
    public Object testEvent(@RequestParam(value = "count") Integer count) {
        for (int index = 1; index <= count; index++) {
            OrderModel orderModel = new OrderModel();
            orderModel.setOrderId((long)index);
            orderModel.setBuyerName("Tom-" + index);
            orderModel.setSellerName("judy-" + index);
            orderModel.setAmount(100L);
            // 发布Spring事件通知
            log.info("product event id: {}", orderModel.getOrderId());
            SpringTools.getApplicationContext().publishEvent(new OrderEvent(orderModel));
        }
        return GlobalResponseModel.setResponse(GlobalResponseEnum.SUCCESS);
    }

}
