package com.hutu.cloud.gateway.constant;

/**
 * 网关常量
 *
 * @author hutu
 * @date 2021/4/1 2:21 下午
 */
public interface GatewayConstant {

	/**
	 * 此前缀与 授权中心保持一致
	 */
	String CACHE_TOKEN_PREFIX = "token_";

	/**
	 * header中key
	 */
	String X_CONTENT_MD5 = "X-Content-MD5";

	String X_CA_KEY = "X-Ca-Key";

	String X_CA_NONCE = "X-Ca-Nonce";

	String X_CA_TIMESTAMP = "X-Ca-Timestamp";

	String X_CA_SIGNATURE = "X-Ca-Signature";

	/**
	 * 入参是否加解密 key
	 */
	String X_SERVICE_ENCRYPT = "X-Service-Encrypt";

	String IS_ENCRYPT = "1";

	/**
	 * header中rpc服务id的key
	 */
	String SERVICE_ID = "X-Service-Id";

	/**
	 * header中rpc方法名的key
	 */
	String SERVICE_METHOD = "X-Service-Method";

}
