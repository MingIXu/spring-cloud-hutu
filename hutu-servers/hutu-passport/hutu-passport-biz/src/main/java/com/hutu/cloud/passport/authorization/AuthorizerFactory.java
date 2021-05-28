package com.hutu.cloud.passport.authorization;

import cn.hutool.extra.spring.SpringUtil;
import com.hutu.cloud.core.exception.GlobalException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @desc AuthorizerFactory
 * @author hutu
 * @date 2021/3/30 5:55 下午
 */
public class AuthorizerFactory {

	/**
	 * 缓存池
	 */
	private static final Map<String, Authorizer> GRANTER_POOL = new ConcurrentHashMap<>();

	static {
		GRANTER_POOL.put(PasswordAuthorizer.GRANT_TYPE, SpringUtil.getBean(PasswordAuthorizer.class));
		GRANTER_POOL.put(CaptchaAuthorizer.GRANT_TYPE, SpringUtil.getBean(CaptchaAuthorizer.class));
		GRANTER_POOL.put(RefreshTokenAuthorizer.GRANT_TYPE, SpringUtil.getBean(RefreshTokenAuthorizer.class));
		GRANTER_POOL.put(SocialTokenAuthorizer.GRANT_TYPE, SpringUtil.getBean(SocialTokenAuthorizer.class));
	}

	/**
	 * 获取Authorizer
	 */
	public static Authorizer getAuthorizer(String grantType) {
		Authorizer tokenGranter = GRANTER_POOL.get(grantType);
		if (tokenGranter == null) {
			throw new GlobalException("no grantType was found");
		}
		else {
			return tokenGranter;
		}
	}

}
