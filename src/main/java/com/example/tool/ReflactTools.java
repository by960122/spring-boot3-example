package com.example.tool;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: BYDylan
 * @date: 2023/11/1
 * @description: 反射工具类
 */
@Slf4j
public class ReflactTools {

    /**
     * 获取对象的所有声明字段
     * 
     * @param object 对象
     * @return 字段集合
     */
    public static List<Field> getFieldsByRecursion(Object object) {
        List<Field> declaredFields = new ArrayList<>(Arrays.asList(object.getClass().getDeclaredFields()));
        Class<?> superclass = object.getClass().getSuperclass();
        while (!Objects.isNull(superclass)) {
            declaredFields.addAll(Arrays.asList(superclass.getDeclaredFields()));
            superclass = superclass.getSuperclass();
        }
        return declaredFields;
    }

    /**
     * 将对象转换为参数
     * 
     * @param clazz 对象
     * @param isUseJsonProperty 是否使用 JsonProperty 值作为参数名
     * @return 参数字符
     */
    public static String objectToParams(Object clazz, Boolean isUseJsonProperty) {
        StringBuilder paramBuilder = new StringBuilder();
        try {
            boolean isFirst = true;
            String property, value;
            for (Field field : getFieldsByRecursion(clazz)) {
                // 允许访问私有变量
                field.setAccessible(true);
                // 过滤静态属性
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                // 过滤空属性值
                Object valueObject = field.get(clazz);
                if (Objects.isNull(valueObject)) {
                    continue;
                }
                value = valueObject.toString();
                // 属性名
                JsonProperty jsonProperty = field.getAnnotation(JsonProperty.class);
                if (isUseJsonProperty && !Objects.isNull(jsonProperty)) {
                    property = jsonProperty.value();
                } else {
                    property = field.getName();
                }
                String params = property + "=" + value;
                if (isFirst) {
                    paramBuilder.append("?").append(params);
                    isFirst = false;
                } else {
                    paramBuilder.append("&").append(params);
                }
            }
        } catch (Exception e) {
            log.error("object convert param error: ", e);
        }
        return paramBuilder.toString();
    }
}
