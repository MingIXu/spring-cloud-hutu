package com.hutu.cloud.register.util;

import java.lang.reflect.Method;

/**
 * 枚举工具
 *
 * @author hutu
 * @date 2021/4/22 5:27 下午
 */
public class EnumUtil {

	public static String getEnumValue(Class<?> clazz, String key) {
		try {
			Object[] objects = clazz.getEnumConstants();
			Method keyMethod = clazz.getDeclaredMethod("getCode");
			Method valueMethod = clazz.getDeclaredMethod("getValue");
			for (Object object : objects) {
				String k = (String) keyMethod.invoke(object);
				if (k.equals(key)) {
					String value = (String) valueMethod.invoke(object);
					return value;
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
