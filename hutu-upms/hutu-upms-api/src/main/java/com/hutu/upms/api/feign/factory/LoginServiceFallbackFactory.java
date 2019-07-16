package com.hutu.upms.api.feign.factory;

import com.hutu.upms.api.feign.RemoteLoginService;
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
public class LoginServiceFallbackFactory implements FallbackFactory<RemoteLoginService> {

	@Override
	public LoginServiceFallbackImpl create(Throwable throwable) {
		LoginServiceFallbackImpl loginServiceFallback = new LoginServiceFallbackImpl();
		loginServiceFallback.setCause(throwable);
		return loginServiceFallback;
	}
}
