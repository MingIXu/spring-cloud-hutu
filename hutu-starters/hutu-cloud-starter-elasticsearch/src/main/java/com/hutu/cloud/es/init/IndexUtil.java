package com.hutu.cloud.es.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.IndexOperations;

/**
 * es 索引操作工具类
 *
 * @author hutu
 * @date 2021/4/13 3:42 下午
 */
@Slf4j
class IndexUtil {

	/**
	 * 创建索引
	 * @param ops
	 */
	public static void createIndex(IndexOperations ops) {
		// 检查索引是否存在
		if (!ops.exists()) {
			// 创建索引
			log.info("create index [{}] ,status: {}", getIndexName(ops), ops.create());
		}
		else {
			log.warn("The index :[{}] is nearly created", getIndexName(ops));
		}
	}

	/**
	 * 创建并刷新 mapping
	 * @param ops
	 */
	public static void putMapping(IndexOperations ops) {
		// 创建 mapping
		log.info("create [{}] mapping status: {}", getIndexName(ops), ops.putMapping());
		// 刷新
		ops.refresh();
	}

	/**
	 * 删除索引
	 */
	public static void delete(IndexOperations ops) {
		// 删索引
		log.info("delete {} index status: {}", getIndexName(ops), ops.delete());
	}

	public static String getIndexName(IndexOperations ops) {
		return ops.getIndexCoordinates().getIndexName();
	}

}
