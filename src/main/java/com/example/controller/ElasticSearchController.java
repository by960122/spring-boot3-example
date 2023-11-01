package com.example.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.constant.GlobalResponseEnum;
import com.example.mapper.ElasticSearchMapper;
import com.example.model.GlobalResponseModel;
import com.example.vo.request.EsCreateIndexParam;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: BYDylan
 * @date: 2024-08-10 15:20:29
 * @description:
 */
@Slf4j
@RestController
public class ElasticSearchController {
    private final ElasticSearchMapper esMapper;

    public ElasticSearchController(ElasticSearchMapper esMapper) {
        this.esMapper = esMapper;
    }

    @PostMapping("/es/create-index")
    public Object esCreateIndex(@RequestBody @Valid EsCreateIndexParam param) {
        return GlobalResponseModel.setResponse(HttpStatus.OK.value(),
            String.valueOf(esMapper.createIndex(param.getIndexName(), param.getAliasesName(), 1)), null);
    }

    @GetMapping("/es/query")
    public Object esQuery() {
        return GlobalResponseModel.setResponse(GlobalResponseEnum.SUCCESS);
    }
}
