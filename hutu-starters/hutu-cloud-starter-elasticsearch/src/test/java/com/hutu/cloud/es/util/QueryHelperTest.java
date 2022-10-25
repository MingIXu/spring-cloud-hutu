package com.hutu.cloud.es.util;

import cn.hutool.json.JSONObject;
import com.hutu.cloud.es.config.ElasticsearchAutoConfiguration;
import com.hutu.cloud.es.entity.Book;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;

import javax.annotation.Resource;
import java.util.List;

/**
 * QueryHelperTest
 *
 * @author hutu
 * @date 2022/7/8
 */
@Slf4j
@SpringBootTest(classes = ElasticsearchAutoConfiguration.class)
class QueryHelperTest {

    @Resource
    QueryHelper queryHelper;
    @Test
    void test(){
        final List<Book> search = queryHelper.searchList(Query.findAll(), Book.class);
        System.out.println();
    }

    @Test
    void test1(){
        final IndexOperations indexOperations = IndexUtil.getIndexOperations(Book.class);
        System.out.println(indexOperations.getMapping());
        System.out.println(indexOperations.getSettings());
    }
    @Test
    void test2(){
        IndexUtil.createIndexAndMapping(Book.class);
        queryHelper.save(new Book().setId(1).setName("小明"));
        queryHelper.save(new Book().setId(3).setName("小华"));
    }
}