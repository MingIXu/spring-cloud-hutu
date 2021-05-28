package com.hutu.cloud.passport.api.feign.fallback;

import com.hutu.cloud.core.entity.LoginUser;
import com.hutu.cloud.passport.api.feign.LoginUserService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 登录用户相关信息
 *
 * @author hutu
 * @date 2021/3/30 2:55 下午
 */
@Component
public class LoginUserServiceFallback implements LoginUserService {

	@Override
	public LoginUser getLoginUserInfo(String account, String tenantId) {
		// 测试
		return new LoginUser().setUserId(1L).setUserName("张三").setRoleId("1").setRoleName("admin").setClientId("web")
				.setTenantId("platform").setAccount("admin").setPassword("123456").setPhoneNum("17600001111");
	}

	@Override
	public List<String> getAuthorityByUser(String userId) {
		// 测试
		ArrayList<String> authorities = new ArrayList<>();
		authorities.add("upms:user:read");
		authorities.add("upms:user:write");
		authorities.add("upms:user:delete");
		authorities.add("upms:user:create");
		return authorities;
	}

	@Override
	public List<String> getAuthorityByRole(String roleId) {
		// 测试
		ArrayList<String> authorities = new ArrayList<>();
		authorities.add("upms:user:read");
		authorities.add("upms:user:write");
		authorities.add("upms:user:delete");
		authorities.add("upms:user:create");
		return authorities;
	}

}
