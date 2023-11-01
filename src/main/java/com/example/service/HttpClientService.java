package com.example.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.stereotype.Service;

import com.example.constant.GlobalResponseEnum;
import com.example.exception.CustomException;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: BYDylan
 * @date: 2023/10/31
 * @description: https://www.cnblogs.com/weststar/p/12979499.html
 */
@Slf4j
@Service
public class HttpClientService {
    private final HttpClient httpClient;

    public HttpClientService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public String doGetSync(String url) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
            // .timeout(Duration.ofMinutes(2))
            .header("Content-Type", "text/html").GET().build();
        // 指定将服务器响应转化成字符串
        HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response;
        try {
            response = httpClient.send(request, bodyHandler);
        } catch (IOException | InterruptedException e) {
            log.info("request url: {}.", url, e);
            throw new CustomException(GlobalResponseEnum.PARAMETER_ERROR, "request error: " + e.getMessage());
        }
        return response.body();
    }

    public void doPostAsync(String url) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
            // .timeout(Duration.ofMinutes(2))
            .header("Content-Type", "text/html")
            .POST(HttpRequest.BodyPublishers.ofString("name=crazyit.org&pass=leegang")).build();
        // 发送异步的POST请求,返回CompletableFuture对象
        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
            // 当CompletableFuture 完成时,传入的Lambda表达式对该返回值进行转换
            // .thenApply(resp -> new Object[] {resp.statusCode(), resp.body()})
            // 当CompletableFuture 完成时,传入的Lambda表达式处理该返回值
            .thenAccept(result -> log.info("request async result: {}", result.body()));
    }
}
