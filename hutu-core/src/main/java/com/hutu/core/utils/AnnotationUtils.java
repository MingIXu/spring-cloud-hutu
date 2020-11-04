package com.hutu.core.utils;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.util.ReflectUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 获取类属性上的注解信息
 *
 * @author hutu
 * @date 2018/6/8 9:46
 */
public class AnnotationUtils extends AnnotationUtil {
    /**
     * 获取类指定属性上的注解
     *
     * @param clazz           目标类
     * @param fieldName       目标属性
     * @param annotationClazz 目标注解类
     * @return 注解实例
     */
    public static Object getFieldAnnotation(Class clazz, String fieldName, Class<? extends Annotation> annotationClazz) {
        Field field;
        try {
            field = clazz.getDeclaredField(fieldName);
            return field.getAnnotation(annotationClazz);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取类指定属性上的注解值
     *
     * @param clazz           目标类
     * @param fieldName       目标属性
     * @param annotationClazz 目标注解类
     * @return 注解实例
     */
    public static Object getFieldAnnotationValue(Class clazz, String fieldName, Class<? extends Annotation> annotationClazz) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            Annotation annotation = field.getAnnotation(annotationClazz);
            if (null == annotation) {
                return null;
            }

            final Method method = ReflectUtil.getMethodOfObj(annotation, "value");
            if (null == method) {
                return null;
            }
            return ReflectUtil.invoke(annotation, method);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }
}
