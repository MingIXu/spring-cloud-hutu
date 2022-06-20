package com.hutu.cloud.passport.controller;

import com.hutu.cloud.cache.util.RedisUtil;
import com.hutu.cloud.core.R;
import com.hutu.cloud.core.constant.StringPool;
import com.hutu.cloud.core.entity.LoginUser;
import com.hutu.cloud.passport.constant.PassportConstant;
import com.hutu.cloud.passport.util.PassportUtil;
import com.hutu.cloud.token.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.hutu.cloud.passport.constant.PassportConstant.CACHE_TOKEN_PREFIX;

/**
 * token关系相关
 *
 * @author hutu
 * @date 2021/3/31 5:27 下午
 */
@RestController
@RequestMapping("tokenManage")
public class TokenController {

	@Autowired
	RedisUtil redisUtil;

	/**
	 * 通过key获取真正的token
	 */
	@GetMapping("/getByKey")
	public R<String> getTokenByKey(String key) {
		Object o = redisUtil.get(CACHE_TOKEN_PREFIX + key);
		return R.ok();
	}

	/**
	 * 获取所有 clientType 类型的登录用户 TODO：可支持分页 1. 先通过 clientType 获取所有keys 2. 根据key内存分页获取jwt
	 */
	@GetMapping("/list")
	public R<List> getToken() {
//		List<String> list = redisUtil.keys(PassportConstant.CACHE_TOKEN_PREFIX + PassportUtil.getClientType()
//				+ StringPool.DASH + StringPool.ASTERISK);
//		ArrayList<LoginUser> loginUsers = new ArrayList<>(list.size());
//		list.forEach(str -> loginUsers.add(TokenUtil.getLoginUser(str)));
//		return R.ok(loginUsers);
		return null;
	}

	/**
	 * 通过key删除token
	 */
	@DeleteMapping("/delete")
	public R<String> deleteToken(String key) {
		redisUtil.del(PassportConstant.CACHE_TOKEN_PREFIX + PassportUtil.getClientType() + key);
		return R.ok();
	}

}
