package com.hutu.common.core.validator;

import com.hutu.common.core.exception.GlobalException;
import org.apache.commons.lang3.StringUtils;

/**
 * 数据校验
 *
 * @date 2017-03-23 15:50
 */
public abstract class Assert {

    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new GlobalException(message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new GlobalException(message);
        }
    }
}
