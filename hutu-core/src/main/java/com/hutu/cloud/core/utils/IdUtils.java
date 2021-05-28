package com.hutu.cloud.core.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

/**
 * 分布式id生成工具
 *
 * @author hutu
 * @date 2020/5/26 3:12 下午
 */
public class IdUtils extends IdUtil {

	/**
	 * 单例对象保证产生id不重复
	 */
	private final static Snowflake SNOWFLAKE = IdUtil.getSnowflake(1, 1);

	public static long getId() {

		return SNOWFLAKE.nextId();
	}

	public static String getIdStr() {
		return SNOWFLAKE.nextIdStr();
	}

}
