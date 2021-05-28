package com.hutu.cloud.token.repository;

/**
 * @desc TokenDao
 * @author hutu
 * @date 2021/3/31 10:44 上午
 */
public interface TokenDao {

	/**
	 * 根据key获取value，如果没有，则返回空
	 * @param key 键名称
	 * @return value
	 */
	String get(String key);

	/**
	 * 写入指定key-value键值对，并设定过期时间 (单位: 秒)
	 * @param key 键名称
	 * @param value 值
	 * @param timeout 过期时间 (单位: 秒)
	 */
	void set(String key, String value, long timeout);

	/**
	 * 修改指定key-value键值对 (过期时间不变)
	 * @param key 键名称
	 * @param value 值
	 */
	void update(String key, String value);

	/**
	 * 删除一个指定的key
	 * @param key 键名称
	 */
	void delete(String key);

	/**
	 * 获取指定key的剩余存活时间 (单位: 秒)
	 * @param key 指定key
	 * @return 这个key的剩余存活时间
	 */
	long getTimeout(String key);

	/**
	 * 修改指定key的剩余存活时间 (单位: 秒)
	 * @param key 指定key
	 * @param timeout 过期时间
	 */
	void updateTimeout(String key, long timeout);

}
