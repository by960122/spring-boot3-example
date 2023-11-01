package com.example.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.example.filter.wrapper.UnGzipRequestWrapper;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: BYDylan
 * @date: 2023/11/7
 * @description: https://mp.weixin.qq.com/s/BzPXBg3QrFBmEZxbhOoapg
 */
@Slf4j
@Component
@WebFilter(urlPatterns = {"/**"})
public class GzipFilter implements Filter {

    private static final String CONTENT_ENCODING = "Content-Encoding";
    private static final String CONTENT_ENCODING_TYPE = "gzip";

    @Override
    public void init(FilterConfig filterConfig) {
        log.info("init gzip filter.");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException {
        long start = System.currentTimeMillis();
        HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;

        String encodeType = httpServletRequest.getHeader(CONTENT_ENCODING);
        if (CONTENT_ENCODING_TYPE.equals(encodeType)) {
            log.info("request uri need to uncompress: {}", httpServletRequest.getRequestURI());
            UnGzipRequestWrapper unGzipRequestWrapper = new UnGzipRequestWrapper(httpServletRequest);
            filterChain.doFilter(unGzipRequestWrapper, servletResponse);
            log.info("time consuming：{} ms", System.currentTimeMillis() - start);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
        log.info("destroy gzip filter.");
    }
}
