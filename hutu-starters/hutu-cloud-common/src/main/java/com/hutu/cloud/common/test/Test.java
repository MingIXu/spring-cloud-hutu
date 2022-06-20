package com.hutu.cloud.common.test;

import com.hutu.cloud.common.util.BeanProperty;
import com.hutu.cloud.common.util.BeanUtil;
import com.hutu.cloud.core.utils.JsonUtil;

public class Test {
    public static void main(String[] args) {
        TestStudent source = new TestStudent();
        // 拷贝
        TestStudent copy = BeanUtil.clone(source);
        System.out.println("copy："+copy.toString());
        // 不同类型拷贝
        TestStudent2 student2 = BeanUtil.copy(source, TestStudent2.class);
        System.out.println("student2："+student2.toString());
        // 动态添加属性并赋值
        BeanProperty beanProperty = new BeanProperty("hutu", String.class);
        Object generator = BeanUtil.generator(source, beanProperty);
        BeanUtil.setProperty(generator,beanProperty.getName(),"湖图");
        System.out.println("generator："+ JsonUtil.toPrettyJson(generator));

    }
}
