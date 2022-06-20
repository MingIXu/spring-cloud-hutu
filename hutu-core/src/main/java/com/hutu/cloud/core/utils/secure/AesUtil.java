package com.hutu.cloud.core.utils.secure;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;

/**
 * AesUtil
 *
 * @author hutu
 * @date 2022/6/20
 */
public class AesUtil {

    /**
     * aes加密key
     */
    private final static String AES_SECRET_KEY = "hx8Mbj4w7nv0mvEm";

    /**
     * aes 单例对象
     */
    final static AES AES_DEFAULT = SecureUtil.aes(AES_SECRET_KEY.getBytes(CharsetUtil.CHARSET_UTF_8));

    /**
     * aes 对称加密
     * @param param 明文
     * @return 密文
     */
    public static String aesEncode(String param) {
        return AES_DEFAULT.encryptBase64(param);
    }

    /**
     * aes 对称解密
     * @param param 密文
     * @return 明文
     */
    public static String aesDecode(String param) {
        return AES_DEFAULT.decryptStr(param);
    }
}
