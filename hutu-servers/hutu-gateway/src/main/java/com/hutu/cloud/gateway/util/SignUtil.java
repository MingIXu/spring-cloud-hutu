package com.hutu.cloud.gateway.util;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.lang.Singleton;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.HMac;
import cn.hutool.crypto.digest.HmacAlgorithm;
import com.hutu.cloud.core.constant.StringPool;
import com.hutu.cloud.gateway.props.AuthProperties;

/**
 * 采用hutool工具签名 参考文档：http://doc.pages.hutuhealth.com/#/V3/advance/gateway
 *
 * @author hutu
 * @date 2021/5/7 9:21 上午
 */
public class SignUtil {

	public SignUtil(AuthProperties authProperties) {
		SignUtil.authProperties = authProperties;
	}

	private static AuthProperties authProperties;

	public static String doSign(String source) {
		return Base64.encode(getHMac().digest(source, CharsetUtil.CHARSET_UTF_8));
	}

	/**
	 * 获取单例签名实例
	 * @return hmac
	 */
	private static HMac getHMac() {
		return Singleton.get("gateway-hmac",
				() -> SecureUtil.hmac(HmacAlgorithm.HmacSHA256, authProperties.getAppSecret()));
	}

}
