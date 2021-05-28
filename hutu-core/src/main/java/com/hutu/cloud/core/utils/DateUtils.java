package com.hutu.cloud.core.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

/**
 * 时间工具类
 *
 * @author hutu
 * @date 2020/6/22 4:01 下午
 */
public class DateUtils extends cn.hutool.core.date.DateUtil {

	/**
	 * 更多时间格式请使用 {@link cn.hutool.core.date.DatePattern}
	 */

	public static final String PATTERN_DATETIME = "yyyy-MM-dd HH:mm:ss";

	public static final String PATTERN_DATE = "yyyy-MM-dd";

	public static final String PATTERN_TIME = "HH:mm:ss";

	/**
	 * java 8 时间格式化
	 */
	public static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern(DateUtils.PATTERN_DATETIME);

	public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DateUtils.PATTERN_DATE);

	public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(DateUtils.PATTERN_TIME);

	/**
	 * java8 日期时间格式化
	 * @param temporal 时间
	 * @return 格式化后的时间
	 */
	public static String formatDateTime(TemporalAccessor temporal) {
		return DATETIME_FORMATTER.format(temporal);
	}

	/**
	 * java8 日期时间格式化
	 * @param temporal 时间
	 * @return 格式化后的时间
	 */
	public static String formatDate(TemporalAccessor temporal) {
		return DATE_FORMATTER.format(temporal);
	}

	/**
	 * java8 时间格式化
	 * @param temporal 时间
	 * @return 格式化后的时间
	 */
	public static String formatTime(TemporalAccessor temporal) {
		return TIME_FORMATTER.format(temporal);
	}

	/**
	 * 将时间戳转化为 LocalDateTime
	 * @param timestamp 时间戳
	 * @return LocalDateTime
	 */
	public static LocalDateTime getDateTimeOfTimestamp(long timestamp) {
		Instant instant = Instant.ofEpochMilli(timestamp);
		ZoneId zone = ZoneId.systemDefault();
		return LocalDateTime.ofInstant(instant, zone);
	}

}
