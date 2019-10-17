package com.hutu.common.core.validator;

import cn.hutool.core.util.StrUtil;
import com.hutu.common.core.exception.GlobalException;

/**
 * 数据校验
 *
 * @date 2017-03-23 15:50
 */
public abstract class Assert {

    public static void isBlank(String str, String message) {
        if (StrUtil.isBlank(str)) {
            throw new GlobalException(message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new GlobalException(message);
        }
    }
}
