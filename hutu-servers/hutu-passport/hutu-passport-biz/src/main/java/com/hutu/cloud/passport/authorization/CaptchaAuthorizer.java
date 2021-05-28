package com.hutu.cloud.passport.authorization;

import cn.hutool.core.util.StrUtil;
import com.hutu.cloud.cache.util.RedisUtil;
import com.hutu.cloud.core.exception.GlobalException;
import com.hutu.cloud.core.entity.LoginUser;
import com.hutu.cloud.passport.api.feign.LoginUserService;
import com.hutu.cloud.passport.enums.PassportStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.hutu.cloud.passport.constant.PassportConstant.CACHE_CAPTCHA_PREFIX;

/**
 * 验证码登录实现
 *
 * @author hutu
 * @date 2021/3/31 3:08 下午
 */
@Component
public class CaptchaAuthorizer implements Authorizer {

	public static final String GRANT_TYPE = "captcha";

	@Autowired
	RedisUtil redisUtil;

	@Autowired
	LoginUserService loginUserService;

	@Override
	public LoginUser grant(Map<String, String> params) {
		String tenantId = params.get("tenantId");
		// 此处为手机号与验证码
		String account = params.get("account");
		String password = params.get("password");

		String captchaValue = (String) redisUtil.get(CACHE_CAPTCHA_PREFIX + account);
		if (StrUtil.hasEmpty(password, captchaValue)) {
			throw new GlobalException(PassportStatusEnum.CAPTCHA_IS_EMPTY);
		}

		LoginUser loginUserInfo = loginUserService.getLoginUserInfo(account, tenantId);
		if (loginUserInfo == null) {
			throw new GlobalException(PassportStatusEnum.PHONE_NUM_NOT_FOUND);
		}

		return loginUserInfo;
	}

}
