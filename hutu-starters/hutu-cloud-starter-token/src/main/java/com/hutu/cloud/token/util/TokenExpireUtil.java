package com.hutu.cloud.token.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * token 过期时间生成工具
 *
 * @author hutu
 * @date 2021/4/27 10:37 上午
 */
public class TokenExpireUtil {

	/**
	 * 过期时间次日凌晨2点 注意：获取的是现在到明天2点的秒数
	 * @return expire 单位秒
	 */
	public static long getOneDayExpire() {

		LocalDateTime now = LocalDateTime.now();
		LocalDate localDate = now.toLocalDate();

		long start = now.toEpochSecond(ZoneOffset.of("+8"));
		long end = localDate.plusDays(1).atTime(2, 0, 0).toEpochSecond(ZoneOffset.of("+8"));

		return end - start;
	}

}
