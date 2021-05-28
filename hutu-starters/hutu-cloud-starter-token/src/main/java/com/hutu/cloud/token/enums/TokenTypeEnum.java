package com.hutu.cloud.token.enums;

import com.hutu.cloud.token.util.TokenExpireUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Token 类型枚举 说明 时间单位都是秒
 *
 * @author hutu
 * @date 2021/3/30 2:48 下午
 */
@Getter
@AllArgsConstructor
public enum TokenTypeEnum {

	/**
	 * 临时令牌（30分钟有效期）
	 */
	ACCESS_TOKEN(1, 30 * 60L),
	/**
	 * 刷新令牌（长久令牌）
	 */
	REFRESH_TOKEN(2, 30 * 24 * 60 * 60L),
	/**
	 * 可用一天的临时令牌
	 */
	ONE_DAY_TOKEN(3, TokenExpireUtil.getOneDayExpire());

	int code;

	long expire;

}
