package com.hutu.cloud.passport.controller;

import com.hutu.cloud.cache.util.RedisUtil;
import com.hutu.cloud.core.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.hutu.cloud.passport.constant.PassportConstant.CACHE_CAPTCHA_PREFIX;

/**
 * 获取手机验证码
 *
 * @author hutu
 * @date 2021/3/31 5:27 下午
 */
@RestController
public class CaptchaController {

	@Autowired
	RedisUtil redisUtil;

	@GetMapping("/captcha")
	public R captcha(String phone) {
		// TODO 获取验证码

		String captchaValue = "1234";// 测试数据
		redisUtil.set(CACHE_CAPTCHA_PREFIX + phone, captchaValue);
		return R.ok();
	}

}
