package com.hutu.cloud.passport.config;

import com.hutu.cloud.cache.util.RedisUtil;
import com.hutu.cloud.passport.repository.RedisTokenDaoImpl;
import com.hutu.cloud.passport.service.TokenServiceImpl;
import com.hutu.cloud.token.repository.TokenDao;
import com.hutu.cloud.token.service.TokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 鉴权中心自动配置
 *
 * @author hutu
 * @date 2021/4/2 5:09 下午
 */
@Configuration(proxyBeanMethods = false)
public class PassportAutoConfiguration {

	@Bean
	public TokenService tokenService() {
		return new TokenServiceImpl();
	}

	@Bean
	public TokenDao tokenDao(RedisUtil redisUtil) {
		return new RedisTokenDaoImpl(redisUtil);
	}

}
