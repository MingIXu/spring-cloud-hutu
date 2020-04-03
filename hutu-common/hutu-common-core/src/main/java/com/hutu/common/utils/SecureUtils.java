package com.hutu.common.utils;


import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;

/**
 * 加密相关
 * @author hutu
 * @date 2018/7/19 17:25
 */
public class SecureUtils {

    /**
     * des加密key
     */
    private final static String DES_SECRET_KEY = "bWluZyBodWEgcWlhbmcgTE9WRSB4dSB0YWkgbGlhbiAh";

    /**
     * des 对称加密
     *
     * @param param 明文
     * @return 密文
     */
    public static String desEncode(String param) {
        return Base64.encode(SecureUtil.des(DES_SECRET_KEY.getBytes()).encrypt(param));
    }

    /**
     * des 对称解密
     *
     * @param param 密文
     * @return 明文
     */
    public static String desDecode(String param) {
        return new String(SecureUtil.des(DES_SECRET_KEY.getBytes()).decrypt(Base64.decode(param)));
    }

    /**
     * MD5 加密
     *
     * @param param 明文
     * @return 密文
     */
    public static String md5(String param) {
        return SecureUtil.md5(param);
    }
}
