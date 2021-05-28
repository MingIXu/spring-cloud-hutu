package com.hutu.cloud.passport.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @desc AuthVO
 * @author hutu
 * @date 2021/3/30 10:52 下午
 */
@Data
@Accessors(chain = true)
public class AuthVO implements Serializable {

	private static final long serialVersionUID = -8369314482182164881L;

	/**
	 * 令牌
	 */
	private String token;

	/**
	 * 令牌过期时间
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	private long expire;

	/**
	 * 刷新令牌
	 */
	private String refreshToken;

}
