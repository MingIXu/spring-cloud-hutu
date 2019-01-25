/*
 *
 *      Copyright (c) 2018-2025, lengleng All rights reserved.
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
 *  Author: lengleng (wangiegie@gmail.com)
 *
 */

package com.hutu.gateway.config;

import com.hutu.gateway.ratelimiter.InMemoryRateLimiter;
import com.hutu.gateway.ratelimiter.IpAddressKeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author hutu
 * @date 2019/1/19
 * 路由限流配置
 */
@Configuration
public class RateLimiterConfiguration {
	@Bean
	public RateLimiter inMemoryRateLimiter(){
		return new InMemoryRateLimiter();
	}

	@Bean(name = IpAddressKeyResolver.BEAN_NAME)
	public KeyResolver ipAddressKeyResolver() {
		return new IpAddressKeyResolver();
	}
}
