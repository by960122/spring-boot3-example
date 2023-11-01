package com.example.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.constant.GlobalResponseEnum;
import com.example.mapper.MysqlMapper;
import com.example.model.GlobalResponseModel;
import com.example.tool.JsonTools;
import com.example.vo.request.QueryParamVo;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: BYDylan
 * @date: 2024-08-10 16:16:35
 * @description:
 */
@Slf4j
@RestController
public class MySQLController {
    private final MysqlMapper mysqlMapper;

    public MySQLController(MysqlMapper mysqlMapper) {
        this.mysqlMapper = mysqlMapper;
    }

    @GetMapping("/test/mysql/get")
    public Object testGetMysql(@Valid QueryParamVo queryParamVo) {
        log.info("mysql get query param:{}", JsonTools.toJsonString(queryParamVo));
        List<String> tables = mysqlMapper.showDatabases();
        return GlobalResponseModel.setResponse(GlobalResponseEnum.SUCCESS, tables);
    }

    @PostMapping("/test/mysql/post")
    public Object testPostMysql(@RequestBody QueryParamVo queryParamVo) {
        log.info("mysql post query param:{}", JsonTools.toJsonString(queryParamVo));
        List<String> tables = mysqlMapper.showDatabases();
        return GlobalResponseModel.setResponse(GlobalResponseEnum.SUCCESS, tables);
    }
}
