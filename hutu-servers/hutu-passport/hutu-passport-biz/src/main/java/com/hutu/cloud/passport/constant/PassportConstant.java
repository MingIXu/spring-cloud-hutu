package com.hutu.cloud.passport.constant;

import java.util.HashSet;

public interface PassportConstant {

	String CAPTCHA_HEADER_KEY = "Captcha-Key";

	String CAPTCHA_HEADER_CODE = "Captcha-Code";

	String TENANT_HEADER_KEY = "Tenant-Id";

	String CLIENT_TYPE_HEADER_KEY = "Client-Type";

	String DEFAULT_TENANT_ID = "000000";

	String DEFAULT_GRANT_TYPE = "password";

	String DEFAULT_CLIENT_TYPE = PassportConstant.CLIENT_TYPE_WEB;

	String CLIENT_TYPE_WEB = "web";

	String CLIENT_TYPE_APP = "app";

	HashSet<String> CLIENT_TYPE_ARRAYS = new HashSet<String>() {
		{
			add(CLIENT_TYPE_APP);
			add(CLIENT_TYPE_WEB);
		}
	};

	String CACHE_TOKEN_PREFIX = "token_";

	String CACHE_REFRESH_TOKEN_PREFIX = "refreshToken_";

	String CACHE_CAPTCHA_PREFIX = "captcha_";

}
