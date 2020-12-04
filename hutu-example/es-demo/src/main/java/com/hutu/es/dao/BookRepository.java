package com.hutu.es.dao;

import com.hutu.es.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Highlight;
import org.springframework.data.elasticsearch.annotations.HighlightField;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
/**
 * spring-data-jap 查询 demo
 *
 * @author hutu
 * @date 2020/12/4 9:37 上午
 */
public interface BookRepository extends ElasticsearchRepository<Book, String> {
    @Query("{\"match\": {\"name\": {\"query\": \"?0\"}}}")
    Page<Book> findByName(String name, Pageable pageable);

    @Highlight(fields = {
            @HighlightField(name = "name"),
            @HighlightField(name = "summary")
    })
    List<SearchHit<Book>> findByNameOrSummary(String text, String summary);
}
