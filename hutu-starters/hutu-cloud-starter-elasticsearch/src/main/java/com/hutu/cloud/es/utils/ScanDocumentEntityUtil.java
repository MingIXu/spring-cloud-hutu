package com.hutu.cloud.es.utils;

import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Set;

/**
 * 扫描工具
 *
 * @author hutu
 * @date 2021/4/23 5:10 下午
 */
@Slf4j
public class ScanDocumentEntityUtil {

	public static Set<Class<?>> scan(String basePackage) {

		Reflections reflections = new Reflections(basePackage);
		Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Document.class);
		log.debug("扫描到的索引实体：{}", classes);
		return classes;
	}

}
