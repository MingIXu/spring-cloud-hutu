package com.hutu.cloud.es;

import com.hutu.cloud.es.entity.Book;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;

/**
 * 刷新mapping，谨慎操作
 *
 * @author hutu
 * @date 2020/9/25 5:57 下午
 */
@Slf4j
public class RefreshIndexTest extends ElasticApplicationTest {

	@Autowired
	ElasticsearchOperations elasticsearchOperations;

	@Test
	public void initIndex() {

		System.out.println("elastic init 。。。");

		IndexOperations indexOps = elasticsearchOperations.indexOps(Book.class);

		createIndex(indexOps, Book.class.getSimpleName());

	}

	/**
	 * 高危！！删除索引后在刷新
	 */
	public static void flush(IndexOperations ops, String indexName) {
		// 删索引
		log.info("delete {} index status: {}", indexName, ops.delete());
		createIndex(ops, indexName);
	}

	/**
	 * 创建索引
	 * @param ops
	 */
	public static void createIndex(IndexOperations ops, String indexName) {
		// 检查索引是否存在
		if (!ops.exists()) {
			log.info("create {} index begin ...", indexName);
			// 创建索引
			log.info("create index status: {}", ops.create());
			refreshMapping(ops, indexName);
			log.info("create {} index end ...", indexName);
		}
		else {
			refreshMapping(ops, indexName);
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

}
