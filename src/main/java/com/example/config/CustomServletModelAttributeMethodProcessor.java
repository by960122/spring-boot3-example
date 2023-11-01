package com.example.config;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ServletModelAttributeMethodProcessor;

import com.example.tool.ReflactTools;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.Valid;

/**
 * @author: BYDylan
 * @date: 2023/3/13
 * @description: 自定义属性处理器: CustomServletModelAttributeMethodProcessor
 *               继承ServletModelAttributeMethodProcessor类,重写bindRequestParameters方法,
 *               将bind方法替换为我们自定义的CustomServletRequestDataBinder
 */
class CustomServletModelAttributeMethodProcessor extends ServletModelAttributeMethodProcessor {

    private final Map<String, Map<String, String>> jsonPropertyFields = new ConcurrentHashMap<>();

    CustomServletModelAttributeMethodProcessor(final boolean annotationNotRequired) {
        super(annotationNotRequired);
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Valid.class);
    }

    @Override
    protected void bindRequestParameters(WebDataBinder binder, NativeWebRequest request) {
        Map<String, String> map = new HashMap<>();
        Map<String, String[]> params = request.getParameterMap();
        Object target = binder.getTarget();
        if (Objects.isNull(target)) {
            return;
        }
        Map<String, String> jsonPropertyFields = getJsonProperty(target);
        for (Map.Entry<String, String[]> entry : params.entrySet()) {
            String name = entry.getKey();
            String value = entry.getValue()[0];
            map.put(jsonPropertyFields.getOrDefault(name, name), value);
        }
        MutablePropertyValues propertyValues = new MutablePropertyValues(map);
        binder.bind(propertyValues);
    }

    private Map<String, String> getJsonProperty(Object target) {
        if (!jsonPropertyFields.containsKey(target.getClass().getName())) {
            Map<String, String> map = new HashMap<>();
            List<Field> declaredFields = ReflactTools.getFieldsByRecursion(target);
            for (Field field : declaredFields) {
                JsonProperty jsonProperty = field.getAnnotation(JsonProperty.class);
                if (!Objects.isNull(jsonProperty)) {
                    map.put(jsonProperty.value(), field.getName());
                }
            }
            jsonPropertyFields.put(target.getClass().getName(), map);
        }
        return jsonPropertyFields.getOrDefault(target.getClass().getName(), new HashMap<>());
    }
}
