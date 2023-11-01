package com.example.config;

import java.net.http.HttpClient;
import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: BYDylan
 * @date: 2023/10/30
 * @description: java 11 后自带的 httpclient 写法
 */
@Configuration
public class HttpClientConfig {
    @Bean
    public HttpClient httpClient() {
        HttpClient.Builder builder = HttpClient.newBuilder();
        // 指定HTTP协议的版本
        builder.version(HttpClient.Version.HTTP_2);
        // 指定重定向策略
        builder.followRedirects(HttpClient.Redirect.NORMAL);
        // 指定超时的时长
        builder.connectTimeout(Duration.ofSeconds(20));
        // 如有必要,可通过该方法指定代理服务器地址
        // builder.proxy(ProxySelector.of(new InetSocketAddress("proxy.crazyit.com", 80)));

        return builder.build();
    }
}
