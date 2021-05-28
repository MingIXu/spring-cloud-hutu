package com.hutu.cloud.es.init;

import com.hutu.cloud.es.properties.CustomElasticsearchProperties;
import com.hutu.cloud.es.utils.ScanDocumentEntityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.elasticsearch.UncategorizedElasticsearchException;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import java.util.Set;

/**
 * 初始化es相关索引配置环境等
 *
 * @author hutu
 * @date 2021/4/25 10:45 上午
 */
@Slf4j
public class InitElasticsearch implements CommandLineRunner {

	final CustomElasticsearchProperties properties;

	final ElasticsearchOperations operations;

	public InitElasticsearch(CustomElasticsearchProperties properties, ElasticsearchOperations operations) {
		this.properties = properties;
		this.operations = operations;
	}

	@Override
	public void run(String... args) throws Exception {
		Set<Class<?>> classes = ScanDocumentEntityUtil.scan(properties.getBasePackage());
		if (classes != null && classes.size() != 0) {
			for (Class<?> aClass : classes) {
				// getIndexName(ops)1. 检查是否创建索引，没有则创建
				IndexUtil.createIndex(operations.indexOps(aClass));
				// 2. 检查mapping是否增加字段，有则刷新
				// 注意：目前不支持更新字段属性
				try {
					IndexUtil.putMapping(operations.indexOps(aClass));
				}
				catch (UncategorizedElasticsearchException e) {
					// 打印刷新失败日志
					log.error(e.getMessage());
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		log.info("es init success ！");
	}

}
