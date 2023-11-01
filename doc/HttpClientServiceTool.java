package com.example.service;

import com.example.constant.GlobalResponseEnum;
import com.example.exception.CustomException;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * https://juejin.cn/post/7098260968186052645
 *
 * @author: BYDylan
 * @date: 2023/10/30
 * @description: apache httpclient 工具类
 */
@Slf4j
public class HttpClientServiceTool {
    private static final RequestConfig requestConfig;

    private static final RequestConfig uploadConfig;

    static {
        requestConfig = RequestConfig.custom()
                // 客户端和服务器建立连接的timeout
                .setConnectTimeout(1000 * 60)
                // 指从连接池获取连接的timeout
                .setConnectionRequestTimeout(6000)
                // 客户端从服务器读取数据的timeout
                .setSocketTimeout(1000 * 60 * 3)
                .build();
        uploadConfig = RequestConfig.custom()
                // 客户端和服务器建立连接的timeout
                .setConnectTimeout(1000 * 60 * 20)
                // 指从连接池获取连接的timeout
                .setConnectionRequestTimeout(6000)
                // 客户端从服务器读取数据的timeout
                .setSocketTimeout(1000 * 60 * 20)
                .build();
    }

    /**
     * 发送get请求, 接收json响应数据
     *
     * @param url   访问地址, 无query参数
     * @param param query参数
     * @return 结果
     */
    public static String doGet(String url, Map<String, String> param) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String result = null;
        CloseableHttpResponse response = null;
        try {
            URIBuilder builder = new URIBuilder(url);
            if (!Objects.isNull(param)) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();

            HttpGet httpGet = new HttpGet(uri);
            httpGet.setConfig(requestConfig);

            response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                result = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8.toString());
            }
        } catch (IOException | URISyntaxException e) {
            log.error("request: {}, error: {}", url, e);
            throw new CustomException(GlobalResponseEnum.PARAMETER_ERROR, "requesterror: " + e.getMessage());
        } finally {
            closeResponse(response, httpClient);
        }
        return result;
    }

//    /**
//     * 发送post请求, 上传byte
//     * POST binary
//     *
//     * @param url 请求地址, 不拼接
//     * @return 结果
//     */
//    public static String doPostBinaryBody(String url, byte[] bytes, String fileName) {
//        // 创建Httpclient对象
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//        CloseableHttpResponse response = null;
//        String result = "";
//        try {
//            // 创建Http Post请求
//            log.info("-->>Http POST请求地址：" + url);
//
//            HttpPost httpPost = new HttpPost(url);
//            httpPost.setConfig(uploadConfig);
//            // 创建参数列表
//
//            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
//            // 这个video自己填, 因为这个方法非常少用, 是我用来上传视频的, 所以这里写死了
//            multipartEntityBuilder.addBinaryBody("video", bytes, ContentType.MULTIPART_FORM_DATA, fileName);
//
//            httpPost.setEntity(multipartEntityBuilder.build());
//            // 执行http请求
//            response = httpClient.execute(httpPost);
//            // 判断返回状态是否为200
//            if (response.getStatusLine().getStatusCode() == 200) {
//                result = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8.toString());
//            }
//        } catch (Exception e) {
//            log.error("request: {}, error: {}", url, e);
//            throw new CustomException(GlobalResponseEnum.PARAMETER_ERROR, "requesterror: " + e.getMessage());
//        } finally {
//            closeResponse(response, httpClient);
//        }
//        return result;
//    }

//    /**
//     * 发送post请求, form-data数据传输
//     * POST multipart/form-data
//     *
//     * @param url 请求地址
//     * @return 返回json数据
//     */
//    public static String doPostFormData(String url, Map<String, String> formData) {
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//        CloseableHttpResponse response = null;
//        String result = "";
//        try {
//
//            HttpPost httpPost = new HttpPost(url);
//            httpPost.setConfig(requestConfig);
//            // 创建参数列表
////            httpPost.setHeader("Content-Type", "multipart/form-data;charset=utf-8"); // 报错
//            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
//            if (formData != null) {
//                for (String key : formData.keySet()) {
//                    multipartEntityBuilder.addTextBody(key, formData.get(key), ContentType.MULTIPART_FORM_DATA);
//                }
//            }
//
//            httpPost.setEntity(multipartEntityBuilder.build());
//            response = httpClient.execute(httpPost);
//            if (response.getStatusLine().getStatusCode() == 200) {
//                result = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8.toString());
//            }
//        } catch (Exception e) {
//            log.error("request: {}, error: {}", url, e);
//            throw new CustomException(GlobalResponseEnum.PARAMETER_ERROR, "requesterror: " + e.getMessage());
//        } finally {
//            closeResponse(response, httpClient);
//        }
//        return result;
//    }

    /**
     * 发送post请求, 接收json响应数据
     * POST application/x-www-form-urlencoded
     *
     * @param url   请求地址, 不拼接
     * @param param 表单query参数
     * @return 结果
     */
    public static String doPost(String url, Map<String, String> param) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String result = "";
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
                httpPost.setEntity(entity);
            }
            response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {
                result = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8.toString());
            }
        } catch (Exception e) {
            log.error("request: {}, error: {}", url, e);
            throw new CustomException(GlobalResponseEnum.PARAMETER_ERROR, "requesterror: " + e.getMessage());
        } finally {
            closeResponse(response, httpClient);
        }
        return result;
    }

    /**
     * 发送post请求, 接收json响应数据
     * POST application/json
     *
     * @param url  请求地址
     * @param json json入参
     * @return 结果
     */
    public static String doPostJson(String url, String json) {
        if (StringUtils.isBlank(json)) {
            log.error("-->>Http POST发送json数据, json不能为空, url:" + url);
            return null;
        }
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String result = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);
            // 创建请求内容
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            // 执行http请求
            response = httpClient.execute(httpPost);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                result = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8.toString());
            }
        } catch (Exception e) {
            log.error("request: {}, error: {}", url, e);
            throw new CustomException(GlobalResponseEnum.PARAMETER_ERROR, "requesterror: " + e.getMessage());
        } finally {
            closeResponse(response, httpClient);
        }
        return result;
    }


    /**
     * 发送get请求, 接收json响应数据 [使用代理]
     *
     * @param url           访问地址, 不拼接
     * @param params        query参数
     * @param headers       请求头
     * @param proxyHost     代理服务器地址
     * @param proxyPort     代理服务器端口
     * @param proxyUser     认证用户名
     * @param proxyPassword 认证密码
     * @return 结果
     */
    public static String doGetProxy(String url,
                                    Map<String, String> params,
                                    Map<String, String> headers,
                                    String proxyHost, Integer proxyPort,
                                    String proxyUser, String proxyPassword) {
        String result = "";
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = null;
        try {
            HttpHost proxy = new HttpHost(proxyHost, proxyPort);

            // 设置代理认证
            CredentialsProvider provider = new BasicCredentialsProvider();
            provider.setCredentials(new AuthScope(proxy), new UsernamePasswordCredentials(proxyUser, proxyPassword));

            httpClient = HttpClients.custom().setDefaultCredentialsProvider(provider).build();

            URIBuilder builder = new URIBuilder(url);
            if (params != null) {
                for (String key : params.keySet()) {
                    builder.addParameter(key, params.get(key));
                }
            }
            URI uri = builder.build();

            HttpGet httpGet = new HttpGet(uri);

            httpGet.setConfig(requestConfig);

            if (null != headers) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    httpGet.setHeader(entry.getKey(), entry.getValue());
                }
            }
            response = httpClient.execute(httpGet);

            if (response.getStatusLine().getStatusCode() == 200) {
                result = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8.toString());
            }
        } catch (IOException | URISyntaxException e) {
            log.error("request: {}, error: {}", url, e);
            throw new CustomException(GlobalResponseEnum.PARAMETER_ERROR, "requesterror: " + e.getMessage());
        } finally {
            closeResponse(response, httpClient);
        }
        return result;
    }

    private static void closeResponse(CloseableHttpResponse response, CloseableHttpClient httpClient) {
        try {
            if (response != null) {
                response.close();
            }
            httpClient.close();
        } catch (IOException e) {
            log.error("close response error: ", e);
        }
    }
}
