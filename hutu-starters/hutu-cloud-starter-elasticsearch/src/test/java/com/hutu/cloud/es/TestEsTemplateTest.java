package com.hutu.cloud.es;

import com.hutu.cloud.core.utils.JsonUtil;
import com.hutu.cloud.es.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;

public class TestEsTemplateTest extends ElasticApplicationTest {

	@Autowired
	ElasticsearchOperations elasticsearchOperations;

	@Test
	public void test() {
		SearchHits<Book> list = elasticsearchOperations.search(Query.findAll(), Book.class);
		System.out.println(JsonUtil.toJsonPrettyString(list));
	}

	@Test
	public void get() {
		Book book = elasticsearchOperations.get("2", Book.class);
		System.out.println(JsonUtil.toJsonPrettyString(book));
	}

}
