package com.alibaba.csp.sentinel.dashboard.repository.es;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * es 索引工具类
 *
 * @author hutu
 * @date 2021/4/13 3:42 下午
 */
@Slf4j
@Component("eiu")
@EnableScheduling
public class ElasticsearchIndexUtil {

	public static final DateTimeFormatter YYYY_MM_DD = DateTimeFormatter.ofPattern("-yyyy-MM-dd");

	public static String getIndexNameSuffix() {
		return LocalDate.now(ZoneId.systemDefault()).format(YYYY_MM_DD);
	}

	public static String getLastDayIndexNameSuffix() {
		return LocalDate.now(ZoneId.systemDefault()).plusDays(1).format(YYYY_MM_DD);
	}

	public static String getBefore10DayIndexNameSuffix() {
		return LocalDate.now(ZoneId.systemDefault()).format(YYYY_MM_DD);
	}

	/**
	 * 创建索引
	 * @param ops
	 */
	public static void createIndex(IndexOperations ops) {
		String indexName = ops.getIndexCoordinates().getIndexName();
		// 检查索引是否存在
		if (!ops.exists()) {
			log.info("create {} index begin ...", indexName);
			// 创建索引
			log.info("create index status: {}", ops.create());
			refreshMapping(ops, indexName);
			log.info("create {} index end ...", indexName);
		}
	}

	/**
	 * 刷新mapping
	 * @param ops
	 */
	public static void refreshMapping(IndexOperations ops, String indexName) {
		// 建mapping
		log.info("create {} mapping status: {}", indexName, ops.putMapping(ops.createMapping()));
		// 刷新
		ops.refresh();
	}

	/**
	 * 删除10天前的
	 */
	public static void delete(IndexOperations ops) {
		String indexName = ops.getIndexCoordinates().getIndexName();
		// 删索引
		log.info("delete {} index status: {}", indexName, ops.delete());
	}

}
