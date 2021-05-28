package com.hutu.cloud.security.service;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.hutu.cloud.security.constant.SecurityConstant;
import com.hutu.cloud.core.constant.StringPool;
import com.hutu.cloud.token.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 默认鉴权实现
 *
 * @author hutu
 * @date 2019-12-06 18:46
 */
@Slf4j
public class AuthService {

	/**
	 * 放行所有请求
	 * @return {boolean}
	 */
	public boolean permitAll() {
		return true;
	}

	/**
	 * 只有超管角色才可访问
	 * @return {boolean}
	 */
	public boolean denyAll() {
		return hasRole(SecurityConstant.ROLE_ADMIN);
	}

	/**
	 * 判断是否有该角色权限
	 * @param role 单角色
	 * @return {boolean}
	 */
	public boolean hasRole(String role) {
		return hasAnyRole(role);
	}

	/**
	 * 判断是否有该角色权限
	 * @param role 角色集合
	 * @return {boolean}
	 */
	public boolean hasAnyRole(String... role) {
		log.info("roles :{}", role);
		String userRole = TokenUtil.getUserRole();
		if (StrUtil.isBlank(userRole)) {
			return false;
		}
		String[] roles = StrUtil.split(userRole, StringPool.COMMA);
		for (String r : role) {
			if (ArrayUtil.contains(roles, r)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断是否有具体权限
	 */
	public boolean hasAuthority(String authority) {
		return hasAnyAuthority(authority);
	}

	/**
	 * 判断是否有具体权限
	 */
	public boolean hasAnyAuthority(String... authority) {
		String userRole = TokenUtil.getUserRole();

		if (StrUtil.isBlank(userRole)) {
			return false;
		}
		String[] roles = StrUtil.split(userRole, StringPool.COMMA);
		// TODO 通过role获取所有authorities 这里只是模拟数据
		String[] authorities = { "user:read", "user:write" };

		for (String r : authority) {
			if (ArrayUtil.contains(authorities, r)) {
				return true;
			}
		}
		return false;
	}

}
