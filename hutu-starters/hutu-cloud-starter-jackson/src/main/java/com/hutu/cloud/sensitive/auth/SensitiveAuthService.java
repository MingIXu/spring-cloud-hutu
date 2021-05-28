package com.hutu.cloud.sensitive.auth;

/**
 * 脱敏权限服务
 *
 * @author hutu
 * @date 2021/4/14 11:54 上午
 */
public interface SensitiveAuthService {

	/**
	 * 是否脱敏
	 * @return boolean
	 */
	default boolean skipDesensitization() {
		return false;
	}

}
