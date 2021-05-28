package com.hutu.cloud.passport.repository;

import com.hutu.cloud.cache.util.RedisUtil;
import com.hutu.cloud.token.constant.TokenConstant;
import com.hutu.cloud.token.repository.TokenDao;

import java.util.concurrent.TimeUnit;

/**
 * token持久层的实现类, 基于redis
 *
 * @author hutu
 * @date 2021/3/30 3:01 下午
 */
public class RedisTokenDaoImpl implements TokenDao {

	final RedisUtil redisUtil;

	public RedisTokenDaoImpl(RedisUtil redisUtil) {
		this.redisUtil = redisUtil;
	}

	/**
	 * 根据key获取value，如果没有，则返回空
	 */
	@Override
	public String get(String key) {
		return (String) redisUtil.get(key);
	}

	/**
	 * 写入指定key-value键值对，并设定过期时间(单位：秒)
	 */
	@Override
	public void set(String key, String value, long timeout) {
		// 判断是否为永不过期
		if (timeout == TokenConstant.NEVER_EXPIRE) {
			redisUtil.set(key, value);
		}
		else {
			redisUtil.set(key, value, timeout, TimeUnit.SECONDS);
		}
	}

	/**
	 * 修改指定key-value键值对 (过期时间不变)
	 */
	@Override
	public void update(String key, String value) {
		long expire = getTimeout(key);
		// -2 = 无此键
		if (expire == TokenConstant.NOT_VALUE_EXPIRE) {
			return;
		}
		this.set(key, value, expire);
	}

	/**
	 * 删除一个指定的key
	 */
	@Override
	public void delete(String key) {
		redisUtil.del(key);
	}

	/**
	 * 根据key获取value，如果没有，则返回空
	 */
	@Override
	public long getTimeout(String key) {
		return redisUtil.getExpire(key);
	}

	/**
	 * 修改指定key的剩余存活时间 (单位: 秒)
	 */
	@Override
	public void updateTimeout(String key, long timeout) {
		// 判断是否想要设置为永久
		if (timeout == TokenConstant.NEVER_EXPIRE) {
			long expire = getTimeout(key);
			if (expire == TokenConstant.NEVER_EXPIRE) {
				// 如果其已经被设置为永久，则不作任何处理
			}
			else {
				// 如果尚未被设置为永久，那么再次set一次
				this.set(key, this.get(key), timeout);
			}
			return;
		}
		redisUtil.expire(key, timeout);
	}

}
