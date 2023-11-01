package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.constant.GlobalResponseEnum;
import com.example.model.GlobalResponseModel;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: BYDylan
 * @date: 2022/2/7
 * @description: 全局异常拦截 1.捕获异常 注解: RestControllerAdvice = ControllerAdvice + ResponseBody 如果用 ControllerAdvice ,必须携带
 *               ResponseBody
 *               <p>
 *               2.返回状态码 注解: @ResponseStatus(HttpStatus.FOUND) 可以定义在类或方法上 HttpServletResponse.setStatus(int)
 *               可以更灵活的设置同一异常,返回不同的状态码
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {CustomException.class})
    public GlobalResponseModel customExceptionHandler(HttpServletResponse response, CustomException exception) {
        final GlobalResponseEnum responseStatusEnum = exception.getResponseStatusEnum();
        response.setStatus(responseStatusEnum.getHttpStatus());
        log.error("custom exception catch: {}", exception.getMessage());
        response.setStatus(responseStatusEnum.getHttpStatus());
        return GlobalResponseModel.setResponse(responseStatusEnum.getHttpStatus(), exception.getMessage(), null);
    }

    @ExceptionHandler(value = {BindException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GlobalResponseModel customExceptionHandler(HttpServletResponse response, BindException exception) {
        StringBuilder errorMessageBuilder = new StringBuilder();
        exception.getAllErrors().forEach(error -> errorMessageBuilder.append(error.getDefaultMessage()).append("."));
        return GlobalResponseModel.setResponse(HttpStatus.BAD_REQUEST.value(), errorMessageBuilder.toString(), null);
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public GlobalResponseModel exceptionHandler(HttpServletResponse response, Exception exception) {
        log.error("global exception catch: {}", exception.getMessage());
        return GlobalResponseModel.setResponse(GlobalResponseEnum.SYSTEM_ERROR.getHttpStatus(), exception.getMessage(),
            null);
    }
}
