package com.hutu.cloud.core.utils.secure;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.FIFOCache;
import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import lombok.extern.slf4j.Slf4j;

/**
 * 简单封装 签名工具类
 *
 * @author hutu
 * @date 2018/3/28 11:41
 */
@Slf4j
public class SignUtil {
    /**
     * 缓存签名实例
     */
    private final static FIFOCache<String, Sign> SIGN_CACHE = CacheUtil.newFIFOCache(8);

    public static final String DEFAULT_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCEQU9cNle3X2vHq+pyn26B7ISMqFBRLbzmbAUHPulb4k+fMFeL+Wfoi4hsjV3LxmWn2CVTHQeCwAzKOS7r0rGHWsVMAh1HdRajXxJhkOPCDglfi9aO8G0QtBJjJmGTjW61A8pX2uZrbf2LobQnsOceuAIMDcGnPr+grpSFbSdH3QIDAQAB";

    public static final String DEFAULT_PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIRBT1w2V7dfa8er6nKfboHshIyoUFEtvOZsBQc+6VviT58wV4v5Z+iLiGyNXcvGZafYJVMdB4LADMo5LuvSsYdaxUwCHUd1FqNfEmGQ48IOCV+L1o7wbRC0EmMmYZONbrUDylfa5mtt/YuhtCew5x64AgwNwac+v6CulIVtJ0fdAgMBAAECgYBfawtJuT9R4ntNZOBScGp3PGBpZuoDl58pE5gRITeV/lJ5xJP1PizUnw7/WoLoiSxAi3ZqX5BSqiLQJ6+NnsZLllCLkHXIgbYXzWx3K9vWQYtU9lrxDWG+HVs5kgRF8OETZkBubeXtpKCW3jLziSZfCmaGSJvQMKvg5Ei+hg8XQQJBAN5REBiLYuZ2MyAe7UQt1viswuB+wS3kURd5NH1my4jrTGo4yzwMdA7uZMcqTLLuq5lt+zsTtl2JK56CSekOIVECQQCYSw3heUkQVknA5V0Bh2S0xkdBpxp7nrd2eZ+M6GUYA+RDl4MW+BfrJShon+HCYHdnQA+BsBNB4KvoZz30FnrNAkAcifF0BSisBw5nGDFDKFNJVvwpT3xDBOU4CzImyvRj3jjQBfxo9/xKEmZTfuWbrrHOn2S537zwYvR7g6QzS8IRAkALI0oTmGQwjq78LYocx4Mk+W9SrN7x9YyjXIe/rWtfU0GJc/ZBYsmVB/WpmS/QoG97HKNFr9PouH66E3rjdBVtAkEAiLIrAHovOo3rLu8C0svhyczeUV5JDN0M2hs1FNUi4uWOZiwVLErDEu5810DzA2NR8HFXs5/HN3emNT81ZMudWQ==";

    /**
     * 验签
     *
     * @param sourceStr 原始字符串
     * @param signStr   签名后的字符串
     * @return boolean
     */
    public static boolean verify(String sourceStr, String signStr, String publicKey) {
        Sign sign = SIGN_CACHE.get("sign_" + publicKey, () -> SecureUtil.sign(SignAlgorithm.MD5withRSA, null, publicKey));
        return sign.verify(sourceStr.getBytes(), Base64.decode(signStr));
    }

    /**
     * 签名
     *
     * @param sourceStr 原始的字符串
     * @return String
     */
    public static String sign(String sourceStr, String privateKey) {
        Sign sign = SIGN_CACHE.get("sign_" + privateKey, () -> SecureUtil.sign(SignAlgorithm.MD5withRSA, privateKey, null));
        return Base64.encode(sign.sign(sourceStr.getBytes()));
    }

    /**
     * 生成随机公钥，私钥
     */
    public static KeyPair generateKeyPair() {
        Sign sign = SecureUtil.sign(SignAlgorithm.MD5withRSA);
        String publicKeyBase64 = sign.getPublicKeyBase64();
        String privateKeyBase64 = sign.getPrivateKeyBase64();
        KeyPair keyPair = new KeyPair(privateKeyBase64, publicKeyBase64);
        log.warn("sign keyPair is {}", keyPair);
        return keyPair;
    }
}
