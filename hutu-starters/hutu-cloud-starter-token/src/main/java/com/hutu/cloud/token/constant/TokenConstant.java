package com.hutu.cloud.token.constant;

/**
 * token 常量
 *
 * @author hutu
 * @date 2021/3/30 2:12 下午
 */
public interface TokenConstant {

	String HEADER_TOKEN_KEY = "token";

	/**
	 * 前缀
	 */
	String PREFIX = "Bearer";

	/** 常量，表示一个key永不过期 (在一个key被标注为永远不过期时返回此值) */
	Long NEVER_EXPIRE = -1L;

	/** 常量，表示系统中不存在这个缓存 (在对不存在的key获取剩余存活时间时返回此值) */
	Long NOT_VALUE_EXPIRE = -2L;

}
