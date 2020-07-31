package com.hutu.provider;

import java.util.ArrayList;
import java.util.List;

/**
 * 鉴权配置
 *
 * @author hutu
 */
public class AuthProvider {

	private static List<String> defaultSkipUrl = new ArrayList<>();

	static {
		defaultSkipUrl.add("/example");
		defaultSkipUrl.add("/token/**");
		defaultSkipUrl.add("/captcha/**");
		defaultSkipUrl.add("/actuator/health/**");
		defaultSkipUrl.add("/v2/api-docs/**");
		defaultSkipUrl.add("/v2/api-docs-ext/**");
		defaultSkipUrl.add("/auth/**");
		defaultSkipUrl.add("/log/**");
		defaultSkipUrl.add("/menu/routes");
		defaultSkipUrl.add("/menu/auth-routes");
		defaultSkipUrl.add("/order/create/**");
		defaultSkipUrl.add("/storage/deduct/**");
		defaultSkipUrl.add("/error/**");
		defaultSkipUrl.add("/assets/**");
	}

	/**
	 * 默认无需鉴权的API
	 */
	public static List<String> getDefaultSkipUrl() {
		return defaultSkipUrl;
	}

}
