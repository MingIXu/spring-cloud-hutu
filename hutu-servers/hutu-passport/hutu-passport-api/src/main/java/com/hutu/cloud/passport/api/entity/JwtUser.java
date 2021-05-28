package com.hutu.cloud.passport.api.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 登录用户信息类
 *
 * @author hutu
 * @date 2021/3/31 10:37 上午
 */
@Data
@Accessors(chain = true)
public class JwtUser implements Serializable {

	private static final long serialVersionUID = 8460656653311629155L;

	/**
	 * 客户端id
	 */
	private String clientId;

	/**
	 * 租户ID
	 */
	private String tenantId;

	/**
	 * 用户id
	 */
	private Long userId;

	/**
	 * 账号
	 */
	private String account;

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 角色id,多个都好隔开
	 */
	private String roleId;

	/**
	 * 角色名,多个都好隔开
	 */
	private String roleName;

	/**
	 * 密码
	 */
	transient private String password;

	/**
	 * 手机号
	 */
	private String phoneNum;

}
