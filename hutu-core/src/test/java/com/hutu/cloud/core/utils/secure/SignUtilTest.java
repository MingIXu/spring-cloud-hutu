package com.hutu.cloud.core.utils.secure;

import org.junit.jupiter.api.Test;

/**
 * SignUtilTest
 *
 * @author hutu
 * @date 2022/6/20
 */
class SignUtilTest {

    @Test
    void verify() {
        KeyPair keyPair = SignUtil.generateKeyPair();
        String word = "abc123你好@";
        System.out.println("before: " + word);
        String sign = SignUtil.sign(word, keyPair.getPrivateKey());
        System.out.println("after: " + sign);
        System.out.println(SignUtil.verify(word, sign, keyPair.getPublicKey()));
    }
}