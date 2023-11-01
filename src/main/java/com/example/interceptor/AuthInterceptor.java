package com.example.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.druid.util.StringUtils;
import com.example.constant.GlobalResponseEnum;
import com.example.exception.CustomException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: BYDylan
 * @date: 2022/2/19
 * @description: 测试拦截器
 */
@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {
        String authHeaderValue = request.getHeader("Authorization");
        if (StringUtils.isEmpty(authHeaderValue)) {
            throw new CustomException(GlobalResponseEnum.AUTHORIZATION_FAIL, "authorization fail.");
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
        ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
        log.debug("auth interceptor post handle.");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
        throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
        log.debug("auth interceptor after completion.");
    }
}
