package com.example.tool;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author: BYDylan
 * @date: 2023/11/1
 * @description:
 */
public class FieldTools {

    public static List<Field> getFieldsByRecursion(Object object) {
        List<Field> declaredFields = new ArrayList<>(Arrays.asList(object.getClass().getDeclaredFields()));
        Class<?> superclass = object.getClass().getSuperclass();
        while (!Objects.isNull(superclass)) {
            declaredFields.addAll(Arrays.asList(superclass.getDeclaredFields()));
            superclass = superclass.getSuperclass();
        }
        return declaredFields;
    }

}
