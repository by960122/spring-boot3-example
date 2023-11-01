package com.example.model;

import java.util.List;

import com.example.constant.GlobalResponseEnum;

import lombok.Getter;

/**
 * @author: BYDylan
 * @date: 2022/2/7
 * @description: 全局返回实体类
 */
@Getter
public class GlobalResponseModel {
    private Integer code;
    private String message;
    private List<?> data;

    private GlobalResponseModel(GlobalResponseEnum responseStatusEnum, List<?> data) {
        this.code = responseStatusEnum.getHttpStatus();
        this.message = responseStatusEnum.getMessage();
        this.data = data;
    }

    private GlobalResponseModel(Integer code, String message, List<?> data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static GlobalResponseModel setResponse(GlobalResponseEnum responseStatusEnum) {
        return new GlobalResponseModel(responseStatusEnum, null);
    }

    /**
     * 不让外部 new 对象
     *
     * @param responseStatusEnum 设置 http
     * @param data data
     * @return 返回实体
     */
    public static GlobalResponseModel setResponse(GlobalResponseEnum responseStatusEnum, List<?> data) {
        return new GlobalResponseModel(responseStatusEnum, data);
    }

    /**
     * 不让外部 new 对象
     *
     * @param code http code
     * @param message http message
     * @param data data
     * @return 返回实体
     */
    public static GlobalResponseModel setResponse(Integer code, String message, List<?> data) {
        return new GlobalResponseModel(code, message, data);
    }
}
