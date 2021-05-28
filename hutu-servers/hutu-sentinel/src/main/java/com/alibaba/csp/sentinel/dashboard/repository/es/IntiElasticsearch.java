package com.alibaba.csp.sentinel.dashboard.repository.es;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 初始化es索引，以及清除更新索引
 *
 * @author hutu
 * @date 2021/4/16 9:28 上午
 */
@Component
public class IntiElasticsearch implements CommandLineRunner {

	@Override
	public void run(String... args) {
		// 判断今天的索引是否存在，不存在创建
		ElasticsearchIndexUtil.createIndex(elasticsearchOperations.indexOps(ESMetric.class));
	}

	@Autowired
	ElasticsearchOperations elasticsearchOperations;

	/**
	 * 每天23点执行一次
	 */
	@Scheduled(cron = "0 0 23 * * ?")
	public void clearAndCreateIndex() {
		// 创建明天的索引
		ElasticsearchIndexUtil.createIndex(elasticsearchOperations.indexOps(ESMetricIndex.class));
		// 删除过期索引
		ElasticsearchIndexUtil.delete(elasticsearchOperations.indexOps(ESMetricDeleteIndex.class));
	}

}
