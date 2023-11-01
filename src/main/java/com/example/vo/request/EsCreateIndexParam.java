package com.example.vo.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * @author: BYDylan
 * @date: 2024-08-10 15:26:09
 * @description:
 */
@Data
public class EsCreateIndexParam {
    @JsonProperty("index_name")
    private String indexName;
    @JsonProperty("aliases_name")
    private String aliasesName;
    @JsonProperty("num_of_shards")
    private int numOfShards;
}
