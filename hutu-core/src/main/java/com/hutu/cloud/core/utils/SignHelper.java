package com.hutu.cloud.core.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import com.hutu.cloud.core.constant.HeaderConstant;

/**
 * 简单封装 签名工具类
 *
 * @author hutu
 * @date 2018/3/28 11:41
 */
public class SignHelper {

	private static String PublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCEQU9cNle3X2vHq+pyn26B7ISMqFBRLbzmbAUHPulb4k+fMFeL+Wfoi4hsjV3LxmWn2CVTHQeCwAzKOS7r0rGHWsVMAh1HdRajXxJhkOPCDglfi9aO8G0QtBJjJmGTjW61A8pX2uZrbf2LobQnsOceuAIMDcGnPr+grpSFbSdH3QIDAQAB";

	private static String PrivateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIRBT1w2V7dfa8er6nKfboHshIyoUFEtvOZsBQc+6VviT58wV4v5Z+iLiGyNXcvGZafYJVMdB4LADMo5LuvSsYdaxUwCHUd1FqNfEmGQ48IOCV+L1o7wbRC0EmMmYZONbrUDylfa5mtt/YuhtCew5x64AgwNwac+v6CulIVtJ0fdAgMBAAECgYBfawtJuT9R4ntNZOBScGp3PGBpZuoDl58pE5gRITeV/lJ5xJP1PizUnw7/WoLoiSxAi3ZqX5BSqiLQJ6+NnsZLllCLkHXIgbYXzWx3K9vWQYtU9lrxDWG+HVs5kgRF8OETZkBubeXtpKCW3jLziSZfCmaGSJvQMKvg5Ei+hg8XQQJBAN5REBiLYuZ2MyAe7UQt1viswuB+wS3kURd5NH1my4jrTGo4yzwMdA7uZMcqTLLuq5lt+zsTtl2JK56CSekOIVECQQCYSw3heUkQVknA5V0Bh2S0xkdBpxp7nrd2eZ+M6GUYA+RDl4MW+BfrJShon+HCYHdnQA+BsBNB4KvoZz30FnrNAkAcifF0BSisBw5nGDFDKFNJVvwpT3xDBOU4CzImyvRj3jjQBfxo9/xKEmZTfuWbrrHOn2S537zwYvR7g6QzS8IRAkALI0oTmGQwjq78LYocx4Mk+W9SrN7x9YyjXIe/rWtfU0GJc/ZBYsmVB/WpmS/QoG97HKNFr9PouH66E3rjdBVtAkEAiLIrAHovOo3rLu8C0svhyczeUV5JDN0M2hs1FNUi4uWOZiwVLErDEu5810DzA2NR8HFXs5/HN3emNT81ZMudWQ==";

	/**
	 * 验签
	 * @param tokenStr Base64编码字符串
	 * @param signStr Base64编码字符串
	 * @return
	 */
	public static boolean verify(String tokenStr, String signStr) {
		Sign sign = SecureUtil.sign(SignAlgorithm.MD5withRSA, null, PublicKey);
		byte[] result0 = Base64.decode(signStr);
		// 验证签名
		boolean verify = false;
		try {
			verify = sign.verify(tokenStr.getBytes(), result0);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return verify;
	}

	/**
	 * 签名
	 * @param tokenStr
	 * @return
	 */
	public static String sign(String tokenStr) {
		Sign sign = SecureUtil.sign(SignAlgorithm.MD5withRSA, PrivateKey, null);
		/* 签名 */
		byte[] signed = sign.sign(tokenStr.getBytes());
		return Base64.encode(signed);
	}

	/**
	 * 签名测试
	 * @param args
	 */
	public static void main(String[] args) {

		String word = HeaderConstant.GATEWAY_CLIENT_ID;
		System.out.println("before: " + word);
		String sign = SignHelper.sign(word);

		System.out.println("after: " + sign);

		System.out.println(verify(word, sign));
	}

	/**
	 * 生成随机公钥，私钥
	 */
	private static void getAutoGenerateKeysPair() {
		Sign sign = SecureUtil.sign(SignAlgorithm.MD5withRSA);
		String publicKeyBase64 = sign.getPublicKeyBase64();
		String privateKeyBase64 = sign.getPrivateKeyBase64();
		System.out.println("publicKey: " + publicKeyBase64);
		System.out.println("privateKey: " + privateKeyBase64);
	}

}
