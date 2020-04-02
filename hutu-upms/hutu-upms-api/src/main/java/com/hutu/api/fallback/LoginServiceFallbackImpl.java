package com.hutu.api.fallback;

import com.hutu.common.core.entity.R;
import com.hutu.api.RemoteLoginService;
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
