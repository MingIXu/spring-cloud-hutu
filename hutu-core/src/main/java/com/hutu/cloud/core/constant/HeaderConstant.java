package com.hutu.cloud.core.constant;

/**
 * 请求头常量
 *
 * @author hutu
 * @date 2021/5/8 2:16 下午
 */
public interface HeaderConstant {

	/**
	 * 客户端id键
	 */
	String CLIENT_ID_KEY = "client_id";

	/**
	 * 客户端secret键
	 */
	String CLIENT_SECRET_KEY = "client_secret";

	/**
	 * 网关调用标识
	 */
	String GATEWAY_CLIENT_ID = "gateway_client_id";

	/**
	 * 网关调用标识
	 */
	String MONITOR_CLIENT_ID = "monitor_client_id";

	/**
	 * 灰度类型
	 */
	String HEADER_GRAY_TYPE = "Gray-Type";

	/**
	 * 灰度类型的值
	 */
	String HEADER_GRAY_VALUE = "Gray-Value";

}
