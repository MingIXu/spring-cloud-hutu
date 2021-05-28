package com.hutu.cloud.passport.util;

import cn.hutool.core.util.StrUtil;
import com.hutu.cloud.common.util.WebUtil;

import static com.hutu.cloud.passport.constant.PassportConstant.*;

/**
 * @desc PassportUtil
 * @author hutu
 * @date 2021/4/2 2:20 下午
 */
public class PassportUtil {

	public static String getClientType() {
		String clientType = WebUtil.getRequestParameter(CLIENT_TYPE_HEADER_KEY);
		// 处理客户端类型
		if (StrUtil.isEmpty(clientType) || !CLIENT_TYPE_ARRAYS.contains(clientType)) {
			clientType = DEFAULT_CLIENT_TYPE;
		}
		return clientType;
	}

}
