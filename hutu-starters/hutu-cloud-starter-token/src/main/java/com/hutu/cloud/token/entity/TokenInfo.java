package com.hutu.cloud.token.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @desc TokenInfo
 * @author hutu
 * @date 2021/3/31 10:08 上午
 */
@Data
@Accessors(chain = true)
public class TokenInfo {

	/**
	 * 令牌值
	 */
	private String token;

	/**
	 * 过期秒数
	 */
	private int expire;

}
