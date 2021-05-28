package com.hutu.cloud.cache.dto;

import com.alicp.jetcache.support.CacheMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 缓存信息传递对象
 *
 * @author hutu
 * @date 2020/10/9 10:43 上午
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CacheDTO {

	String area;

	String cacheName;

	CacheMessage cacheMessage;

}
