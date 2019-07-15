/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */

package com.hutu.upms.api.feign.factory;

import com.hutu.upms.api.feign.LoginService;
import com.hutu.upms.api.feign.fallback.LoginServiceFallbackImpl;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * something
 *
 * @author hutu
 * @date 2019/7/15 18:57
 */
@Component
public class LoginServiceFallbackFactory implements FallbackFactory<LoginService> {

	@Override
	public LoginService create(Throwable throwable) {
		LoginServiceFallbackImpl loginServiceFallback = new LoginServiceFallbackImpl();
		loginServiceFallback.setCause(throwable);
		return loginServiceFallback;
	}
}
