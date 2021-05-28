package com.hutu.cloud.passport.api.feign;

import com.hutu.cloud.core.constant.AppNamesConstant;
import com.hutu.cloud.feign.annotation.CustomFeignClient;
import com.hutu.cloud.feign.enums.RpcType;
import com.hutu.cloud.core.entity.LoginUser;
import com.hutu.cloud.passport.api.feign.fallback.LoginUserServiceFallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 登录用户相关信息
 *
 * @author hutu
 * @date 2021/3/30 2:55 下午
 */
@CustomFeignClient(value = AppNamesConstant.HUTU_UPMS, rpcType = RpcType.HTTP,
		fallback = LoginUserServiceFallback.class)
public interface LoginUserService {

	@GetMapping("getLoginUserInfo")
	LoginUser getLoginUserInfo(@RequestParam String account, @RequestParam String tenantId);

	@GetMapping("getAuthorityByUser")
	List<String> getAuthorityByUser(@RequestParam String userId);

	@GetMapping("getAuthorityByRole")
	List<String> getAuthorityByRole(@RequestParam String roleId);

}
