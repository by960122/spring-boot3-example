package com.example.filter;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.example.filter.wrapper.RepeatableHttpRequestWrapper;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @author: BYDylan
 * @date: 2023/11/7
 * @description: 可重复读 body
 */
@Component
@WebFilter(urlPatterns = {"/**"})
public class RepeatableFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
        throws IOException, ServletException {
        if (request instanceof HttpServletRequest
            && StringUtils.startsWithIgnoreCase(request.getContentType(), MediaType.APPLICATION_JSON_VALUE)) {
            ServletRequest wrapper = new RepeatableHttpRequestWrapper((HttpServletRequest)request);
            filterChain.doFilter(wrapper, response);
            return;
        }
        filterChain.doFilter(request, response);

    }
}
