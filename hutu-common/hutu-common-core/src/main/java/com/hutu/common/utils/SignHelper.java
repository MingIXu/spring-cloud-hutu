package com.hutu.common.utils;


import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;

/**
 * 简单封装 签名工具类
 * @author hutu
 * @date 2018/3/28 11:41
 */
public class SignHelper {
    private static String PublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCcKVz9+3Kpspz8ajwF3CZ7XqsA1lSLHK46EG4ckkacWXfDO/z8jXaDHhooAj9ydIZm4dHOEIa65//S2R7n55xg2Fb2kxGmdRYZJn+Y37KhXQu6JokYPG6G1f3F7IXVsrH8KyYs4q4eeCn1zC+itunSQgMB2xt2wzH27gcpfm9N9QIDAQAB";
    private static String PrivateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJwpXP37cqmynPxqPAXcJnteqwDWVIscrjoQbhySRpxZd8M7/PyNdoMeGigCP3J0hmbh0c4Qhrrn/9LZHufnnGDYVvaTEaZ1Fhkmf5jfsqFdC7omiRg8bobV/cXshdWysfwrJizirh54KfXML6K26dJCAwHbG3bDMfbuByl+b031AgMBAAECgYBWJwvCo0JzFLM/HmzW3snsLiIxaA1kYgJMzluxL5H9F+7WqvW7b1/+nlDX2cWfI2IyyEqzYLKz0uJ0NIy0bDb86dC4IdwRQWWZcNa9iFRJVC0S7suOtgOGmrXeajBoRM9BlFAKwpjGGGZDB9wYc4oxA3sKM8RGxdSzGlm450wN0QJBANTj/GRa7IJ8rOp3fTXIoumknCbkEHI3wqNoT8OoqO0kR9T33fdxCEeMul/oj3DWEImgPFAxuxWFE5bqTU+TZUMCQQC7yJu9VyzWp2TExIQB7NB7OxyOHRNVgzsMSh4p5FKoVDHGzgygnTvn92mZxLyOIyGpkryZ9m58oDi8qydXdzBnAkEAmZZ1cy1JrgYmde2IrrG2Htu8MC6fUte5m0xZJ25ZmORw0kuUnry0XXJAz4qnWZ+GRNQOT0jhkO/2Jw2Ygw1yowJBAIoguyZgTHQst3vhjbSYzJYI2i1TB9i76iBVGLD56S82l1LEWBeA8QLlRAE+7O+kuesxK5gY5Ba6CGIHh63X13sCQBxinxfi5uVXsD1ozwApw+0QImSpgh3NpYlNSemciFi3UnQMRLugkjDNp5C8U8cVcyC3NPMDUjSosNjB3zKviyo=";

    /**
     * 验签
     * @param tokenStr Base64编码字符串
     * @param signStr Base64编码字符串
     * @return
     */
    public static boolean verify(String tokenStr,String signStr){
        Sign sign = SecureUtil.sign(SignAlgorithm.MD5withRSA,null,PublicKey);
        byte[] password0 = Base64.decode(tokenStr);
        byte[] result0 = Base64.decode(signStr);
        //验证签名
        return sign.verify(password0, result0);
    }

    /**
     * 签名
     * @param tokenStr
     * @return
     */
    public static String sign(String tokenStr){
        Sign sign = SecureUtil.sign(SignAlgorithm.MD5withRSA,PrivateKey,null);
        /* 签名 */
        byte[] data = Base64.decode(tokenStr);
        byte[] signed = sign.sign(data);
        return Base64.encode(signed);
    }

    /**
     * 签名测试
     * @param args
     */
    /*public static void main(String[] args) {
        // 生成随机公钥，私钥
        Sign sign1 = SecureUtil.sign(SignAlgorithm.MD5withRSA);
        String publicKeyBase64 = sign1.getPublicKeyBase64();
        String privateKeyBase64 = sign1.getPrivateKeyBase64();

        String before = Base64.encode("ming");
        String after = Base64.decodeStr(before);
        System.out.println("after:"+after);
        String sign = SignHelper.sign(before);
        boolean b = SignHelper.verify(before,sign);
        System.out.println(b);
    }*/
}
