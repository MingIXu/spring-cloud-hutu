package com.hutu.cloud.core.utils.secure;

import cn.hutool.crypto.SecureUtil;

/**
 * Md5Util
 *
 * @author hutu
 * @date 2022/6/20
 */
public class Md5Util {
    /**
     * MD5 加密
     * @param param 明文
     * @return 密文
     */
    public static String md5(String param) {
        return SecureUtil.md5(param);
    }
}
