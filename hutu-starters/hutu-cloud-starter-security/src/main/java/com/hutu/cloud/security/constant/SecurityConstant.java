
package com.hutu.cloud.security.constant;

/**
 * 安全常量
 *
 * @author hutu
 * @date 2020/6/18 11:15 上午
 */
public interface SecurityConstant {

	/**
	 * 认证请求头
	 */
	String BASIC_HEADER_KEY = "Authorization";

	/**
	 * 认证请求头前缀
	 */
	String BASIC_HEADER_PREFIX = "Basic ";

	/**
	 * 认证请求头前缀
	 */
	String BASIC_HEADER_PREFIX_EXT = "Basic%20";

	/**
	 * 管理员角色
	 */
	String ROLE_ADMIN = "admin";

	/**
	 * 默认排除的路径 patterns
	 */
	String[] DEFAULT_EXCLUDE_PATTERNS = { "/error/**", "/actuator/**", "/token/**" };

}
