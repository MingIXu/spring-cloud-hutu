package com.hutu.cloud.core.utils.secure;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * RsaUtil
 *
 * @author hutu
 * @date 2022/6/20
 */
public class RsaUtil {

    /**
     * rsa 非对称加密 私钥
     */
    final static String DEFAULT_PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAL0nWjbaGb/P8XDIg37EOpnTWtfi3qWr75a2vAgnK7uqKi8ET1WsEC+FGTBUGrZ+HFUIwVkX9OZj/m5tl9aurewWrbmabigaQW5oqh57YEsoRtFMjvxgC3lsPFPppFu9lifnXr6T4by7hwDgOV3viaXCRfm75wHqcsYtRP66NZWzAgMBAAECgYBs2cKOokADI9UbRCh3wNDASOKULdhWmnUL1CiswG3oVupjmCVXdebykbOHHj8pGbWQMbOiY6jpFE4MmkCwqGEo54pLlzkN27oBjoSWKwLBqmS/kJZeYkes4c7tJYhcbKO7zwqAFKptK92eMi460TVcs+p5RieueKE4H9ag9bhvgQJBAOy9C82eMrc8xOGsD6X9ST+o/jb87sIwA/zqMraJTzX5PypprbCjokBxu3y+WcDHcF9zil+qQNoZ7IfzHIpvyhMCQQDMiy2Xx9iun4Nqk8EZ1HhLwhl6x21IBEYBGYaDXpbR+vyfGhRo01uIyhLtsYe8jqlel4jrXHMU/ftY81X5BnnhAkEAmujJRG70OLrnCdSRGMi4mZN3ZXv0r9TDh0eoj5Eh5+uEzbgBE8i/bvP8/w9/o4ndmlf8f8TkUtML15nZXyzvSQJAV86vpebO6DfmDIydat9BPRR4J7MRAMeE3df9XMvwZvgeFpb1iAKj2tcUxSFfKdz8bHUU/CTV6n8VtZvQnnTfQQJAd9IONZxSlf1XPmryshNuR0/WlhKnV5Vuogc48pgXHxkDpyOKvYP36IvvB1JLumjaJ8pSYNW6qNu+icv+36VyNQ==";

    /**
     * rsa 非对称加密 公钥
     */
    final static String DEFAULT_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC9J1o22hm/z/FwyIN+xDqZ01rX4t6lq++WtrwIJyu7qiovBE9VrBAvhRkwVBq2fhxVCMFZF/TmY/5ubZfWrq3sFq25mm4oGkFuaKoee2BLKEbRTI78YAt5bDxT6aRbvZYn516+k+G8u4cA4Dld74mlwkX5u+cB6nLGLUT+ujWVswIDAQAB";

    /**
     * rsa 单例对象
     */
    final static RSA RSA_DEFAULT = SecureUtil.rsa(DEFAULT_PRIVATE_KEY, DEFAULT_PUBLIC_KEY);

    public static String rsaEncode(String param) {
        return RSA_DEFAULT.encryptBase64(param, KeyType.PublicKey);
    }

    public static String rsaDecode(String param) {
        return RSA_DEFAULT.decryptStr(param, KeyType.PrivateKey);
    }

    public static KeyPair generateKeyPair() {
        RSA rsa = SecureUtil.rsa();
        String publicKeyBase64 = rsa.getPublicKeyBase64();
        String privateKeyBase64 = rsa.getPrivateKeyBase64();
        return new KeyPair(privateKeyBase64, publicKeyBase64);
    }

    @Getter
    @ToString
    @AllArgsConstructor
    public static class KeyPair {
        String privateKey;
        String publicKey;
    }
}
