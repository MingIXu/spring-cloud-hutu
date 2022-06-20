package com.hutu.cloud.token.util;

import cn.hutool.core.util.StrUtil;
import com.hutu.cloud.common.util.WebUtil;
import com.hutu.cloud.core.constant.StringPool;
import com.hutu.cloud.core.entity.LoginUser;
import com.hutu.cloud.core.utils.JsonUtil;
import com.hutu.cloud.token.constant.TokenConstant;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;

/**
 * token工具类
 *
 * @author hutu
 * @date 2019-12-13 9:29
 */
@Slf4j
public class TokenUtil {

	/**
	 * 解析jwt token
	 */
	public static Claims parseToken(String sourceToken) {
		return JwtUtil.parseToken(sourceToken);
	}

	/**
	 * 判断token是否过期且合法
	 * @return boolean
	 */
	public static boolean isLogin() {
		String token = getTokenString();
		if (StrUtil.isNotEmpty(token)) {
			try {
				parseToken(token);
				return true;
			}
			catch (Exception e) {
				return false;
			}
		}
		else {
			log.info("请求中无token认证信息");
			return false;
		}
	}

	/**
	 * 获取登录用户信息
	 * @return subject
	 */
	public static LoginUser getLoginUser() {
		return getLoginUser(getTokenString());
	}

	public static LoginUser getLoginUser(String sourceToken) {
		if (StrUtil.isNotEmpty(sourceToken)) {
			Claims claim = parseToken(sourceToken);
			return JsonUtil.parse(claim.getSubject(), LoginUser.class);
		}
		else {
			log.info("请求中无token认证信息");
			return null;
		}
	}

	/**
	 * 获取用户id
	 * @return userId
	 */
	public static String getUserId() {
		Object rpc_uid = null;
		return rpc_uid == null ? getLoginUser().getUserId().toString() : rpc_uid.toString();
	}

	/**
	 * 获取客户端信息
	 * @return clientId
	 */
	public static String getClientId() {
		Object rpc_client_id = null;
		return rpc_client_id == null ? getLoginUser().getClientId() : rpc_client_id.toString();
	}

	/**
	 * 获取用户名
	 * @return userName
	 */
	public static String getUserName() {
		LoginUser user = getLoginUser();
		return (null == user) ? StringPool.EMPTY : user.getUserName();
	}

	/**
	 * 获取用户角色
	 * @return userName
	 */
	public static String getUserRole() {
		LoginUser user = getLoginUser();
		return (null == user) ? StringPool.EMPTY : user.getRoleName();
	}

	/**
	 * 获取租户ID
	 * @return tenantId
	 */
	public static String getTenantId() {
		LoginUser user = getLoginUser();
		return (null == user) ? StringPool.EMPTY : user.getTenantId();
	}

	public static String getTokenString() {
		return WebUtil.getRequestParameter(TokenConstant.HEADER_TOKEN_KEY);
	}

}
