package com.hutu.cloud.core.utils.secure;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.DES;

/**
 * DesUtil
 *
 * @author hutu
 * @date 2022/6/20
 */
public class DesUtil {
    /**
     * des加密key
     */
    private final static String DES_SECRET_KEY = "bWluZyBodWEgcWlhbmcgTE9WRSB4dSB0YWkgbGlhbiAh";

    /**
     * des 单例对象
     */
    final static DES DES_DEFAULT = SecureUtil.des(DES_SECRET_KEY.getBytes(CharsetUtil.CHARSET_UTF_8));

    /**
     * des 对称加密
     * @param param 明文
     * @return 密文
     */
    public static String desEncode(String param) {
        return Base64.encode(DES_DEFAULT.encrypt(param));
    }

    /**
     * des 对称解密
     * @param param 密文
     * @return 明文
     */
    public static String desDecode(String param) {
        return new String(DES_DEFAULT.decrypt(Base64.decode(param)));
    }
}
