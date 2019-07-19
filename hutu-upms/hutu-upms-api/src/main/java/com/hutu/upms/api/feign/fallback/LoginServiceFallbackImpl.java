/*
 *
 *      Copyright (c) 2018-2025,  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the pig4cloud.com developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author:  (wangiegie@gmail.com)
 *
 */

package com.hutu.upms.api.feign.fallback;

import com.hutu.common.core.entity.R;
import com.hutu.upms.api.feign.RemoteLoginService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * something
 *
 * @author hutu
 * @date 2019/7/15 18:55
 */
@Slf4j
@Component
public class LoginServiceFallbackImpl implements RemoteLoginService {
	@Setter
	private Throwable cause;

	/**
	 * 通过用户名查询用户、角色信息
	 */
	@Override
	public R login(String username, String password){
		log.error("feign 查询用户信息失败:{}", username, cause);
		return null;
	}

}
