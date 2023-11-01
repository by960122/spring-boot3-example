package com.example.tool;

import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;

import org.springframework.boot.json.JsonParseException;

import com.example.constant.GlobalResponseEnum;
import com.example.exception.CustomException;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: BYDylan
 * @date: 2022/2/22
 * @description: Jackson 工具类, 参考: org.springframework.boot.json
 */
@Slf4j
public class JsonTools {
    private static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 允许对象有json不存在的字段
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 允许有空值
        // MAPPER.getSerializerProvider().setNullKeySerializer(new Jsr310NullKeySerializer());
        // 允许有注释
        OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
    }

    private static ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }

    /**
     * JsonTools.tryParse( () -> JsonTools.)
     *
     * @param parser 解析方法
     * @param <T>    泛型
     * @return 结果
     */
    public static <T> T tryParse(Callable<T> parser) {
        return tryParse(parser, JacksonException.class);
    }

    private static <T> T tryParse(Callable<T> parser, Class<? extends Exception> exception) {
        try {
            return parser.call();
        } catch (Exception e) {
            log.error("json deal error: {}", e);
            if (exception.isAssignableFrom(e.getClass())) {
                throw new JsonParseException(e);
            }
            throw new IllegalStateException(e);
        }
    }

    /**
     * 转换成 json string
     *
     * @param object 传入对象
     * @return 返回 string
     */
    public static String toJsonString(Object object) {
        if (Objects.isNull(object)) {
            throw new CustomException(GlobalResponseEnum.PARAMETER_ERROR, "object is null, unable to convert");
        }
        return tryParse(() -> getObjectMapper().writeValueAsString(object), Exception.class);
    }

    /**
     * 将 json 转为对象
     *
     * @param json 传入 json
     * @param aClass 对象定义
     * @param <T> 泛型
     * @return 返回对象
     */
    public static <T> T toObject(String json, Class<T> aClass) {
        return tryParse(() -> getObjectMapper().readValue(json, aClass), Exception.class);
    }

    /**
     * 将 json 转为 list
     *
     * @param json 传入 json
     * @param cla 对象定义
     * @param <T> 泛型
     * @return 返回 集合
     */
    public static <T> List<T> toArray(String json, Class<T> cla) {
        JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructParametricType(List.class, cla);
        return tryParse(() -> getObjectMapper().readValue(json, javaType), Exception.class);
    }

    /**
     * json 转 类型
     *
     * @param json 传入 json
     * @param type type
     * @param <T> 泛型定义
     * @return 返回对象
     */
    public static <T> T toObject(String json, Type type) {
        return tryParse(() -> getObjectMapper().readValue(json, getObjectMapper().getTypeFactory().constructType(type)),
                Exception.class);
    }

    /**
     * 输入流转对象
     *
     * @param inputStream 输入流
     * @param aClass 对象定义
     * @param <T> 泛型
     * @return 返回对象
     */
    public static <T> T toObject(InputStream inputStream, Class<T> aClass) {
        if (Objects.isNull(inputStream)) {
            throw new CustomException(GlobalResponseEnum.PARAMETER_ERROR, "InputStream is null");
        }
        return tryParse(() -> getObjectMapper().readValue(inputStream, aClass), Exception.class);
    }

    /**
     * reader 转对象
     *
     * @param reader reader
     * @param aClass 对象定义
     * @param <T> 泛型
     * @return 返回对象
     */
    public static <T> T toObject(Reader reader, Class<T> aClass) {
        if (reader == null) {
            throw new CustomException(GlobalResponseEnum.PARAMETER_ERROR, "reader is null");
        }
        return tryParse(() -> getObjectMapper().readValue(reader, aClass), Exception.class);
    }
}