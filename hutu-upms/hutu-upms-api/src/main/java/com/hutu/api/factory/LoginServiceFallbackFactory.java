package com.hutu.api.factory;

import com.hutu.api.fallback.LoginServiceFallbackImpl;
import com.hutu.api.RemoteLoginService;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * something
 *
 * @author hutu
 * @date 2019/7/15 18:57
 */
@Component
public class LoginServiceFallbackFactory implements FallbackFactory<RemoteLoginService> {

	@Override
	public LoginServiceFallbackImpl create(Throwable throwable) {
		LoginServiceFallbackImpl loginServiceFallback = new LoginServiceFallbackImpl();
		loginServiceFallback.setCause(throwable);
		return loginServiceFallback;
	}
}
