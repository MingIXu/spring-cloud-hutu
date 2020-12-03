package com.alibaba.csp.sentinel;

import com.alibaba.csp.sentinel.dashboard.repository.es.ESMetric;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;

/**
 * @desc 操作es mapping，谨慎操作
 *
 * @author hutu
 * @date 2020/12/3 10:06 上午
 */
@Slf4j
public class RefreshIndexTest extends BaseTest {

    @Autowired
    ElasticsearchOperations elasticsearchRestTemplate;

    @Test
    public void initIndex() {

        System.out.println("elastic init 。。。");

        IndexOperations indexOps = elasticsearchRestTemplate.indexOps(ESMetric.class);

        createIndex(indexOps,"sentinel");

    }

    /**
     * 删除索引后在刷新
     */
    public static void flush(IndexOperations ops,String indexName) {
        // 删索引
        log.info("delete {} index status: {}", indexName,ops.delete());
        createIndex(ops,indexName);
    }

    /**
     * 创建索引
     *
     * @param ops
     */
    public static void createIndex(IndexOperations ops,String indexName) {
        // 检查索引是否存在
        if (!ops.exists()) {
            log.info("create {} index begin ...",indexName);
            // 创建索引
            log.info("create index status: {}", ops.create());
            refreshMapping(ops,indexName);
            log.info("create {} index end ...",indexName);
        }else {
            refreshMapping(ops,indexName);
        }
    }


    /**
     * 刷新mapping
     *
     * @param ops
     */
    public static void refreshMapping(IndexOperations ops,String indexName) {
        // 建mapping
        log.info("create {} mapping status: {}",indexName, ops.putMapping(ops.createMapping()));
        // 刷新
        ops.refresh();
    }
}

