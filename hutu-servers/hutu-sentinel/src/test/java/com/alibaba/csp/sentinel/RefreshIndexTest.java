package com.alibaba.csp.sentinel;

import com.alibaba.csp.sentinel.dashboard.repository.es.ESMetric;
import com.alibaba.csp.sentinel.dashboard.repository.es.ElasticsearchIndexUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

/**
 * @desc 操作es mapping，谨慎操作
 * @author hutu
 * @date 2020/12/3 10:06 上午
 */
@Slf4j
public class RefreshIndexTest extends BaseTest {

	@Autowired
	ElasticsearchOperations elasticsearchRestTemplate;

	@Test
	public void initIndex() {
		ElasticsearchIndexUtil.createIndex(elasticsearchRestTemplate.indexOps(ESMetric.class));

	}

}
