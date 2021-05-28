package com.hutu.cloud.passport.service;

import com.hutu.cloud.passport.util.AuthJwtUtil;
import com.hutu.cloud.token.enums.TokenTypeEnum;
import com.hutu.cloud.token.repository.TokenDao;
import com.hutu.cloud.token.service.TokenService;
import com.hutu.cloud.token.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author hutu
 * @desc TokenServiceImpl
 * @date 2021/3/30 10:08 下午
 */
public class TokenServiceImpl implements TokenService {

	@Autowired
	TokenDao tokenDao;

	@Override
	public String create(String key, Object sourceToken, TokenTypeEnum tokenType) {
		return AuthJwtUtil.createToken(key, sourceToken, tokenType.getExpire() * 1000);
	}

	@Override
	public Claims parse(String sourceToken) {
		return JwtUtil.parseToken(sourceToken);
	}

	@Override
	public boolean save(String key, String token, long expire) {
		tokenDao.set(key, token, expire);
		return true;
	}

	@Override
	public boolean delete(String key) {
		tokenDao.delete(key);
		return true;
	}

	@Override
	public boolean update(String key, String newToken) {
		tokenDao.update(key, newToken);
		return true;
	}

	@Override
	public String get(String key) {
		return tokenDao.get(key);
	}

	@Override
	public long getTimeout(String key) {

		return tokenDao.getTimeout(key);
	}

	@Override
	public void updateTimeout(String key, long timeout) {
		tokenDao.updateTimeout(key, timeout);
	}

}
