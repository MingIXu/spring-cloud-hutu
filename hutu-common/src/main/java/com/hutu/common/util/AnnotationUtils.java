package com.hutu.common.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * 获取类属性上的注解信息
 * @author hutu
 * @date 2018/6/8 9:46
 */
public class AnnotationUtils {
    /**
     * 获取类指定属性上的注解值
     * @param clazz 目标类
     * @param fieldName 目标属性
     * @param annotationClazz 目标注解类
     * @return 注解实例
     */
    public static Object getAnnotationValue(Class clazz, String fieldName, Class<? extends Annotation> annotationClazz) {
        Field field;
        try {
            field = clazz.getDeclaredField(fieldName);
            return field.getAnnotation(annotationClazz);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }
}
