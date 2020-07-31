package com.hutu.core.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

/**
 * 使用Hutool生产全局唯一id,采用雪花算法生成时间有序id
 *
 * @author hutu
 * @date 2020/5/26 3:12 下午
 */
public class IdGenerator {

    private static Snowflake snowflake = IdUtil.createSnowflake(1, 1);

    public static long getId() {

        return snowflake.nextId();
    }

    public static String getIdStr() {
        return snowflake.nextIdStr();
    }

}
