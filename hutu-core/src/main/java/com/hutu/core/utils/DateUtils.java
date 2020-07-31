package com.hutu.core.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * 时间工具类
 *
 * @author hutu
 * @date 2020/6/22 4:01 下午
 */
public class DateUtils extends cn.hutool.core.date.DateUtil {

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
