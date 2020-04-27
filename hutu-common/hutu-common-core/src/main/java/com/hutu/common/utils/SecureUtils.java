package com.hutu.common.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.symmetric.DES;

/**
 * 加密相关
 *
 * @author hutu
 * @date 2018/7/19 17:25
 */
public class SecureUtils {

    /**
     * des加密key
     */
    private final static String DES_SECRET_KEY = "bWluZyBodWEgcWlhbmcgTE9WRSB4dSB0YWkgbGlhbiAh";
    /**
     * des 单例对象
     */
    final static DES desDefault = SecureUtil.des(DES_SECRET_KEY.getBytes());

    /**
     * des 对称加密
     *
     * @param param 明文
     * @return 密文
     */
    public static String desEncode(String param) {
        return Base64.encode(desDefault.encrypt(param));
    }

    /**
     * des 对称解密
     *
     * @param param 密文
     * @return 明文
     */
    public static String desDecode(String param) {
        return new String(desDefault.decrypt(Base64.decode(param)));
    }

    /**
     * rsa 非对称加密 私钥
     */
    final static String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAL0nWjbaGb/P8XDIg37EOpnTWtfi3qWr75a2vAgnK7uqKi8ET1WsEC+FGTBUGrZ+HFUIwVkX9OZj/m5tl9aurewWrbmabigaQW5oqh57YEsoRtFMjvxgC3lsPFPppFu9lifnXr6T4by7hwDgOV3viaXCRfm75wHqcsYtRP66NZWzAgMBAAECgYBs2cKOokADI9UbRCh3wNDASOKULdhWmnUL1CiswG3oVupjmCVXdebykbOHHj8pGbWQMbOiY6jpFE4MmkCwqGEo54pLlzkN27oBjoSWKwLBqmS/kJZeYkes4c7tJYhcbKO7zwqAFKptK92eMi460TVcs+p5RieueKE4H9ag9bhvgQJBAOy9C82eMrc8xOGsD6X9ST+o/jb87sIwA/zqMraJTzX5PypprbCjokBxu3y+WcDHcF9zil+qQNoZ7IfzHIpvyhMCQQDMiy2Xx9iun4Nqk8EZ1HhLwhl6x21IBEYBGYaDXpbR+vyfGhRo01uIyhLtsYe8jqlel4jrXHMU/ftY81X5BnnhAkEAmujJRG70OLrnCdSRGMi4mZN3ZXv0r9TDh0eoj5Eh5+uEzbgBE8i/bvP8/w9/o4ndmlf8f8TkUtML15nZXyzvSQJAV86vpebO6DfmDIydat9BPRR4J7MRAMeE3df9XMvwZvgeFpb1iAKj2tcUxSFfKdz8bHUU/CTV6n8VtZvQnnTfQQJAd9IONZxSlf1XPmryshNuR0/WlhKnV5Vuogc48pgXHxkDpyOKvYP36IvvB1JLumjaJ8pSYNW6qNu+icv+36VyNQ==";
    /**
     * rsa 非对称加密 公钥
     */
    final static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC9J1o22hm/z/FwyIN+xDqZ01rX4t6lq++WtrwIJyu7qiovBE9VrBAvhRkwVBq2fhxVCMFZF/TmY/5ubZfWrq3sFq25mm4oGkFuaKoee2BLKEbRTI78YAt5bDxT6aRbvZYn516+k+G8u4cA4Dld74mlwkX5u+cB6nLGLUT+ujWVswIDAQAB";
    /**
     * rsa 单例对象
     */
    final static RSA rsaDefault = SecureUtil.rsa(privateKey, publicKey);

    public static String rsaEncode(String param) {
        return rsaDefault.encryptBase64(param, KeyType.PublicKey);
    }

    public static String rsaDecode(String param) {
        return rsaDefault.decryptStr(param, KeyType.PrivateKey);
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

    /**
     * 测试加密解密
     */
    /*public static void main(String[] args) {
        RSA rsa = SecureUtil.rsa();
        String publicKeyBase64 = rsa.getPublicKeyBase64();
        String privateKeyBase64 = rsa.getPrivateKeyBase64();

        String encrypt = desEncode("46464");
        String decrypt = desDecode(encrypt);
        String encrypt1 = rsaEncode("7987987");
        String decrypt1 = rsaDecode(encrypt1);
        System.out.println(decrypt);
        System.out.println(decrypt1);
    }*/
}
