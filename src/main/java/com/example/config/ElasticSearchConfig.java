package com.example.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import lombok.Data;
import nl.altindag.ssl.SSLFactory;

/***
 * @author: BYDylan
 * @date: 2022/2/21
 * @description: ElasticSearch 配置类 参考文档: https://www.elastic.co/guide/en/elasticsearch/client/java-api-client/index.html
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "elasticsearch", ignoreUnknownFields = false)
public class ElasticSearchConfig {
    private String schema;
    private String host;
    private int port;
    private String username;
    private String password;
    private int connectTimeout;
    private int socketTimeout;
    private int maxConnectNum;
    private int maxConnectPerRoute;
    private int connectionRequestTimeout;

    /**
     * 新版
     *
     * @return 返回 ElasticsearchClient
     */
    @Bean
    public ElasticsearchClient elasticsearchClient() {
        RestClientBuilder restClientBuilder = RestClient.builder(new HttpHost(host, port, schema));
        SSLFactory sslFactory = SSLFactory.builder().withUnsafeTrustMaterial().withUnsafeHostnameVerifier().build();
        // 异步连接数配置 + 禁用 ssl 验证 + 账号密码
        BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
        restClientBuilder.setHttpClientConfigCallback(httpClientBuilder -> {
            httpClientBuilder.setMaxConnTotal(maxConnectNum);
            httpClientBuilder.setMaxConnPerRoute(maxConnectPerRoute);
            httpClientBuilder.setSSLContext(sslFactory.getSslContext());
            httpClientBuilder.setSSLHostnameVerifier(sslFactory.getHostnameVerifier());
            httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
            return httpClientBuilder;
        });
        // 异步连接延时配置
        restClientBuilder.setRequestConfigCallback(requestConfigBuilder -> {
            requestConfigBuilder.setConnectTimeout(connectTimeout);
            requestConfigBuilder.setSocketTimeout(socketTimeout);
            requestConfigBuilder.setConnectionRequestTimeout(connectionRequestTimeout);
            return requestConfigBuilder;
        });
        ElasticsearchTransport transport = new RestClientTransport(restClientBuilder.build(), new JacksonJsonpMapper());
        return new ElasticsearchClient(transport);
    }
}
