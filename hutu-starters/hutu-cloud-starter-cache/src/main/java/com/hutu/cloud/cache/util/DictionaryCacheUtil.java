package com.hutu.cloud.cache.util;

import cn.hutool.core.util.StrUtil;
import com.alicp.jetcache.Cache;
import com.hutu.cloud.core.constant.StringPool;

/**
 * 此工具用于访问 hutu 字典
 *
 * @author hutu
 * @date 2021/2/3 9:31 上午
 */
public class DictionaryCacheUtil {

	private static Cache<String, String> dicCache;

	final static String DIC_SERVICE = "basic.unLoginService";

	final static String DIC_METHOD = "getText";

	public DictionaryCacheUtil setDicCache(Cache<String, String> cache) {
		DictionaryCacheUtil.dicCache = cache;
		return this;
	}

	public static String getText(String id, Object key) {
		Object[] params = { id, key };
		String dicKey = id + StringPool.COLON + key;
		String dicValue = dicCache.get(dicKey);
		if (StrUtil.isEmpty(dicValue)) {
			// dicValue = restTemplate.exchange(DIC_SERVICE, DIC_METHOD, params,
			// String.class);
			dicCache.put(id, dicValue);
		}
		return dicValue;
	}

}
