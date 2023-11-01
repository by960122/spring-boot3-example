package com.example.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PathPatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPattern;

import jakarta.annotation.Resource;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: BYDylan
 * @date: 2023/11/10
 * @description:
 */
@Slf4j
@Component
@WebFilter(urlPatterns = {"/**"})
public class InvalidRequestFilter implements Filter {

    @Resource
    private ApplicationContext applicationContext;

    @Override
    public void init(FilterConfig filterConfig) {
        log.info("request filter init.");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws ServletException, IOException {
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> methodMap = mapping.getHandlerMethods();
        List<String> uris = new ArrayList<>();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : methodMap.entrySet()) {
            RequestMappingInfo requestMappingInfo = entry.getKey();
            String uri = getUriFromMappingInfo(requestMappingInfo);
            uris.add(uri);
            String method = getMethodFromMappingInfo(requestMappingInfo);
            log.debug("interface method: {}, uri: {}", method, uri);
        }
        if (servletRequest instanceof HttpServletRequest) {
            HttpServletRequest request = (HttpServletRequest)servletRequest;
            if (uris.contains(request.getRequestURI())) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                log.warn("invalid request: {}", request.getRequestURI());
                servletRequest.getRequestDispatcher("/failed").forward(servletRequest, servletResponse);
            }
        }
    }

    @Override
    public void destroy() {
        log.info("invalid request filter destroy.");
    }

    private String getUriFromMappingInfo(RequestMappingInfo requestMappingInfo) {
        PathPatternsRequestCondition patternsCondition = requestMappingInfo.getPathPatternsCondition();
        Set<PathPattern> patterns = patternsCondition.getPatterns();
        if (!CollectionUtils.isEmpty(patterns)) {
            return patterns.iterator().next().toString();
        }
        return "";
    }

    private String getMethodFromMappingInfo(RequestMappingInfo requestMappingInfo) {
        RequestMethodsRequestCondition methodsCondition = requestMappingInfo.getMethodsCondition();
        Set<RequestMethod> methods = methodsCondition.getMethods();
        if (!CollectionUtils.isEmpty(methods)) {
            return methods.iterator().next().toString();
        }
        return "";
    }

}
