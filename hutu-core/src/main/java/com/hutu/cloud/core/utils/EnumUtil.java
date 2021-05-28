package com.hutu.cloud.core.utils;

import cn.hutool.core.util.StrUtil;

import java.lang.reflect.Method;

/**
 * 枚举工具类
 *
 * @date 2018-11-08
 */
public class EnumUtil {

	private static final String GET_KEY_METHOD_NAME = "getCode";

	private static final String GET_VALUE_METHOD_NAME = "getDesc";

	/**
	 * 通过key获取name
	 * @param clazz
	 * @param key
	 * @return
	 */
	public static String getEnum(Class<?> clazz, Integer key) {
		try {
			Object[] objects = clazz.getEnumConstants();
			Method keyMethod = clazz.getDeclaredMethod(GET_KEY_METHOD_NAME);
			Method valueMethod = clazz.getDeclaredMethod(GET_VALUE_METHOD_NAME);
			for (Object object : objects) {
				Integer k = (Integer) keyMethod.invoke(object);
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

	/**
	 * 判断key在枚举中是否存在
	 * @param clazz
	 * @param key
	 * @return
	 */
	public static boolean exists(Class<?> clazz, Integer key) {
		String name = getEnum(clazz, key);
		if (StrUtil.isBlank(name)) {
			return false;
		}
		return true;
	}

	/**
	 * 判断key在枚举中是否不存在
	 * @param clazz
	 * @param key
	 * @return
	 */
	public static boolean notExists(Class<?> clazz, Integer key) {
		return !exists(clazz, key);
	}

}
