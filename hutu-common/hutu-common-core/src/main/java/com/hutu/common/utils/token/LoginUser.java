package com.hutu.common.utils.token;

import lombok.Data;

import java.io.Serializable;

/**
 * 登录用户信息类
 * @author hutu
 * @date 2019-12-06 14:57
 */
@Data
public class LoginUser implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 客户端id
	 */
	private String clientId;

	/**
	 * 用户id
	 */
	private Integer userId;
	/**
	 * 租户ID
	 */
	private String tenantId;
	/**
	 * 昵称
	 */
	private String userName;
	/**
	 * 账号
	 */
	private String account;
	/**
	 * 角色id
	 */
	private String roleId;
	/**
	 * 角色名
	 */
	private String roleName;

}
