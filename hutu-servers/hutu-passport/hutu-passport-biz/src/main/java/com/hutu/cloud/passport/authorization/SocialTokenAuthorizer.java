package com.hutu.cloud.passport.authorization;

import com.hutu.cloud.core.entity.LoginUser;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 第三方登录实现
 *
 * @author hutu
 * @date 2021/3/31 3:08 下午
 */
@Component
public class SocialTokenAuthorizer implements Authorizer {

	public static final String GRANT_TYPE = "social";

	@Override
	public LoginUser grant(Map<String, String> params) {
		String tenantId = params.get("tenantId");
		String clientType = params.get("clientType");
		String refreshToken = params.get("refreshToken");
		return null;
	}

}