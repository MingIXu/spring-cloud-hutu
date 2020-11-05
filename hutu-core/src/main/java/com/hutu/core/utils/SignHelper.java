package com.hutu.core.utils;


import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import com.hutu.core.constant.CommonConstant;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 简单封装 签名工具类
 *
 * @author hutu
 * @date 2018/3/28 11:41
 */
public class SignHelper {

    private static String PublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDKb70toq+vwmxqV4yT4+nsmIUgEjpUyQxGolpzoLkZeLw8xrg5AMdWt8tMitxdrMOwUrVNdcMRGh5CjDQoBea44OLopMN6aRcdtW7u/yq5OuWUQI6gnAqSFRe1AhyXbXXWildNl8FU00xn0N+i+oSvKjkLNeKkfjKyaD48FihlPQIDAQAB";
    private static String PrivateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAMpvvS2ir6/CbGpXjJPj6eyYhSASOlTJDEaiWnOguRl4vDzGuDkAx1a3y0yK3F2sw7BStU11wxEaHkKMNCgF5rjg4uikw3ppFx21bu7/Krk65ZRAjqCcCpIVF7UCHJdtddaKV02XwVTTTGfQ36L6hK8qOQs14qR+MrJoPjwWKGU9AgMBAAECgYBIbmyY3IHR+FXEQukIrV6wie3wCLWYSos65rJIdvRjucBYWp3lv3KTg5WZIIIyfjNSbnVybrVJ9DdphjLPMzVWxCsb77Cs5YkdtdHd6JdA0a6HQSzjRclf9nh9uG/34qZgcwN4N+42aL0+2fGKux4X+RDrfhr2wo/qEJ1Tw0VIoQJBAOc7rh7JK207G2JOkJJJVyu2XDWHTZwTJDNbvgpdTtrb7VbGojnT0VTjpwoFpDCDnAiZaJhT4a2OmJVjVa29bdUCQQDgHndg7bx+OQ38L4Odl3fn0uGJnP7is3jB0Wd7epaXiHExsC9lf+wHDdPFVGokiE50SmXORtFAu4teau2QKAXJAkAnjjsEAy/NmL3ffEcY7zjdPm3ZrlKlb1TTAE+rIDkcEWExZftcXxLBLcxmV3612d24Tt3oqPjDItCRRZPji7MdAkBlFljSJ2j1pdZhAS1kT4WN0thXsbBloH2/Lix2zXxPLQOhXc6DAYf8RohWYRTfFXwNh+ETgW2wvObH14L/mo3BAkAie+XJd7ftJlBDwj5FzAMYRS4N0DyMUD9blLP9wgJFOBxDssnq6qo2BJTCYOTBSPwbp7NiWsSAR2PeyjCaS+Ir";

    /**
     * 验签
     * @param tokenStr Base64编码字符串
     * @param signStr Base64编码字符串
     * @return
     */
    public static boolean  verify(String tokenStr,String signStr){
        Sign sign = SecureUtil.sign(SignAlgorithm.MD5withRSA,null,PublicKey);
        byte[] password0 = Base64.decode(tokenStr);
        byte[] result0 = Base64.decode(signStr);
        //验证签名
        boolean verify = false;
        try{
            verify = sign.verify(password0, result0);
        }catch (Exception e){
            e.printStackTrace();
        }
        return verify;
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
    public static void main(String[] args) {

        String before = Base64.encode(CommonConstant.GATEWAY_KEY);
        String after = Base64.decodeStr(before);
        System.out.println("after:"+after);
        String sign = SignHelper.sign(before);

        System.out.println(sign);

        System.out.println(verify(before, sign));
    }

    /**
     *  生成随机公钥，私钥
     */
    private static void getKeys(){
        Sign sign1 = SecureUtil.sign(SignAlgorithm.MD5withRSA);
        String publicKeyBase64 = sign1.getPublicKeyBase64();
        String privateKeyBase64 = sign1.getPrivateKeyBase64();
        System.out.println("publicKey: " + publicKeyBase64);
        System.out.println("privateKey: " + privateKeyBase64);
    }
}
