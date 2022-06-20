package com.hutu.cloud.core.utils;

import cn.hutool.core.util.StrUtil;
import com.hutu.cloud.core.constant.StringPool;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * 灰度工具类
 *
 * @author hutu
 * @date 2021/7/23 4:53 下午
 */
public class GrayUtil {

	public static Map<String, String> getTypeAndValue(String str) {
		HashMap<String, String> map = new HashMap<>(8);
		if (StrUtil.isNotEmpty(str)) {
			String[] typeAndValues = str.split(StringPool.COMMA);
			for (String typeAndValue : typeAndValues) {
				String[] typeValue = typeAndValue.split(StringPool.EQUALS);
				if (typeValue.length == 2) {
					map.put(typeValue[0], typeValue[1]);
				}
			}
		}
		return map.size() == 0 ? null : map;
	}

	public static Map<String, String> getGrayMap(Object object) {
		HashMap<String, String> map = new HashMap<>(8);
		Field[] fields = ReflectionUtils.getAllFields(object);
		for (Field field : fields) {
			// 排除 final 修饰字段，过滤非字符串字段
			if (!Modifier.isFinal(field.getModifiers()) && field.getType().equals(String.class)) {
				String name = field.getName();
				Object value = ReflectionUtils.getFieldValue(object, field);
				if (value != null) {
					map.put(name, (String) value);
				}
			}
		}
		return map.size() == 0 ? null : map;
	}

	public static boolean isMatchMetadata(Map<String, String> metadata, Map<String, String> grayMap) {
		if (metadata == null || metadata.size() == 0 || grayMap == null || grayMap.size() == 0) {
			return false;
		}
		for (String key : metadata.keySet()) {
			if (!(grayMap.containsKey(key) && grayMap.get(key).equalsIgnoreCase(metadata.get(key)))) {
				return false;
			}
		}
		return true;
	}

}
