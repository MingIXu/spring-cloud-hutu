package com.hutu.cloud.core.utils.secure;

import cn.hutool.crypto.digest.MD5;
import net.dreamlu.mica.core.tuple.KeyPair;
import net.dreamlu.mica.core.utils.AesUtil;
import net.dreamlu.mica.core.utils.Base64Util;
import net.dreamlu.mica.core.utils.DesUtil;
import net.dreamlu.mica.core.utils.RsaUtil;
import org.junit.jupiter.api.Test;

/**
 * AesUtilTest
 *
 * @author hutu
 * @date 2022/6/20
 */
class AesUtilTest {

    @Test
    void aesEncode() {
        String secretKey = "1234567891234567891234567891234a";
        String text = "abcd123你好@";
//        String en = AesUtil.encryptToBase64(text, secretKey);
//        String de = AesUtil.decryptFormBase64ToString(en, secretKey);
//        System.out.println(en);
//        System.out.println(de);
//
//        String desEn = DesUtil.encryptToBase64(text, secretKey);
//        String desDe = DesUtil.decryptFormBase64(desEn, secretKey);
//        System.out.println(desEn);
//        System.out.println(desDe);

        KeyPair keyPair = RsaUtil.genKeyPair();
        String rsaEn = RsaUtil.encryptToBase64(keyPair.getPublicBase64(), text);
        String rsaDe = RsaUtil.decryptFromBase64(keyPair.getPrivateBase64(), rsaEn);
        System.out.println(rsaEn);
        System.out.println(rsaDe);


    }

    @Test
    void aesDecode() {

    }
}