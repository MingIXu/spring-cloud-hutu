package com.hutu.cloud.security.utils;

import cn.hutool.core.util.StrUtil;
import com.hutu.cloud.core.constant.HeaderConstant;
import com.hutu.cloud.core.utils.SignHelper;
import com.hutu.cloud.common.util.WebUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 客户端校验类
 *
 * @author hutu
 * @date 2021/3/24 10:50 上午
 */
@Slf4j
public class ClientUtil {

	/**
	 * 判断是否为内部调用
	 */
	public static boolean isInnerClient() {
		String clientId = WebUtil.getRequestParameter(HeaderConstant.CLIENT_ID_KEY);
		String clientSecret = WebUtil.getRequestParameter(HeaderConstant.CLIENT_SECRET_KEY);
		if (SignHelper.verify(clientId, clientSecret)) {
			log.trace("client check pass, clientId: {}", clientId);
			return true;
		}
		return false;
	}

	/**
	 * 判断是否为网关调用
	 */
	public static boolean isGatewayClient() {
		String clientId = WebUtil.getRequestParameter(HeaderConstant.CLIENT_ID_KEY);
		return StrUtil.equals(HeaderConstant.GATEWAY_CLIENT_ID, clientId) && isInnerClient();
	}

	/**
	 * 判断是否为监控调用
	 */
	public static boolean isMonitorClient() {
		String clientId = WebUtil.getRequestParameter(HeaderConstant.CLIENT_ID_KEY);
		return StrUtil.equals(HeaderConstant.MONITOR_CLIENT_ID, clientId) && isInnerClient();
	}

}
