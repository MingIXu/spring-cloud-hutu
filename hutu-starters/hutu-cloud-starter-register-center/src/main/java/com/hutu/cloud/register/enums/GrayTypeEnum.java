package com.hutu.cloud.register.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 灰度类型
 *
 * @author hutu
 * @date 2021/4/22 4:46 下午
 */
@Getter
@AllArgsConstructor
public enum GrayTypeEnum {

	/**
	 * 所属版本号
	 */
	VERSION("version", "gray_version"),
	/**
	 * 所属可用区
	 */
	ZONE("zone", "gray_zone"),
	/**
	 * 所属区域名
	 */
	REGION("region", "gray_region"),
	/**
	 * 所属组名
	 */
	GROUP("group", "gray_group"),
	/**
	 * 所属环境
	 */
	ENV("env", "gray_env");

	String code;

	String value;

}
