package com.hutu.utils;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.WrapperQueryBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 封装ElasticsearchRestTemplate通用方法
 */
@Getter
public class CustomEsRestTemplate {

    @Autowired
    ElasticsearchOperations elasticsearchRestTemplate;

    /**
     * 获取
     *
     * @param indexCoordinates     索引名称
     * @param queryBuilder         请求builder
     * @param sortBuilder          排序builder
     * @param highlightField       高亮字段
     * @param highlightFieldResult 接收高亮结果field
     * @param pageable             分页
     * @param clazz                文档对应实体类
     * @return 返回文档实体集合
     */
    public <T> List<T> list(QueryBuilder queryBuilder, SortBuilder sortBuilder, String highlightField, String highlightFieldResult, Pageable pageable, IndexCoordinates indexCoordinates, Class<T> clazz) {

        NativeSearchQueryBuilder nsQueryBuilder = new NativeSearchQueryBuilder();

        if (queryBuilder != null) {
            nsQueryBuilder.withQuery(queryBuilder);
        }
        if (sortBuilder != null) {
            nsQueryBuilder.withSort(sortBuilder);
        }
        if (pageable != null) {
            nsQueryBuilder.withPageable(pageable);
        }

        if (highlightField != null) {
            nsQueryBuilder.withHighlightBuilder(new HighlightBuilder().field(highlightField));
        }

        Query query = nsQueryBuilder.build();

        SearchHits<T> searchHits;

        if (indexCoordinates == null) {
            searchHits = elasticsearchRestTemplate.search(query, clazz);
        } else {
            searchHits = elasticsearchRestTemplate.search(query, clazz, indexCoordinates);
        }

        List<T> result = searchHits.get().map(searchHit -> {
            T content = searchHit.getContent();

            if (StrUtil.isNotEmpty(highlightField) && StrUtil.isNotEmpty(highlightFieldResult)) {
                List<String> values = searchHit.getHighlightField(highlightField);
                String join = StrUtil.join(StrUtil.EMPTY, values);
                ReflectUtil.setFieldValue(content, highlightFieldResult, join);
            }

            return content;
        }).collect(Collectors.toList());

        return result;
    }

    public <T> List<T> list(QueryBuilder queryBuilder, Class<T> clazz) {
        return list(queryBuilder,null,null,null,null,null,clazz);
    }

    private <T> List<T> list(Map<String, Object> map, SortBuilder sortBuilder, Pageable pageable, Class<T> clazz) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        map.forEach((s, o) -> boolQueryBuilder.must(QueryBuilders.matchQuery(s, o)));
        return list(boolQueryBuilder, sortBuilder, null, null, pageable, null, clazz);
    }

    public <T> List<T> list(String field, String text, Class<T> clazz) {
        return list(MapUtil.of(field, text), SortBuilders.scoreSort(), Pageable.unpaged(), clazz);
    }

    public <T> List<T> page(String field, String text, int page, int size, Class<T> clazz) {
        return list(MapUtil.of(field, text), SortBuilders.scoreSort(), PageRequest.of(page, size), clazz);
    }

    public <T> List<T> list(Map<String, Object> map, Class<T> clazz) {

        return list(map, SortBuilders.scoreSort(), Pageable.unpaged(), clazz);
    }

    public <T> List<T> page(Map<String, Object> map, int page, int size, Class<T> clazz) {

        return list(map, SortBuilders.scoreSort(), PageRequest.of(page, size), clazz);
    }


    public boolean update(UpdateQuery updateQuery, Class clazz) {
        UpdateResponse update = elasticsearchRestTemplate.update(updateQuery, IndexCoordinates.of(clazz.getSimpleName()));
        return update.getResult().equals(UpdateResponse.Result.UPDATED);
    }

    public <T> boolean save(T entity) {
        return Objects.nonNull(elasticsearchRestTemplate.save(entity));
    }

    public <T> boolean batchSave(List<T> list) {

        Assert.notEmpty(list);
        Iterable save = elasticsearchRestTemplate.save(list);
        return save.iterator().hasNext();
    }

    public <T> boolean delete(T entity) {
        return StrUtil.isNotEmpty(elasticsearchRestTemplate.delete(entity));
    }

    public <T> int batchDelete(List<T> list) {
        AtomicInteger n = new AtomicInteger();
        list.forEach(o -> {
            if (delete(o)) {
                n.getAndIncrement();
            }
        });
        return n.get();
    }

    public <T> T get(String id, Class<T> clazz) {
        return elasticsearchRestTemplate.get(id, clazz);
    }

    public <T> T get(QueryBuilder query, Class<T> clazz) {
        NativeSearchQuery builder = new NativeSearchQueryBuilder()
                .withQuery(query).build();
        return Objects.requireNonNull(elasticsearchRestTemplate.searchOne(builder, clazz)).getContent();
    }

    /**
     * 直接写dsl语句查询
     *
     * @param jsonStr json格式语句
     */
    public <T> List<T> searchByJsonStr(String jsonStr, Class<T> clazz) {
        Assert.notEmpty(jsonStr);
        WrapperQueryBuilder wrapperQueryBuilder = QueryBuilders.wrapperQuery(jsonStr);
        return list(wrapperQueryBuilder, SortBuilders.scoreSort(), null, null, Pageable.unpaged(), null, clazz);
    }

    public <T> List<T> searchByJsonStr(String jsonStr, int page, int size, Class<T> clazz) {
        Assert.notEmpty(jsonStr);
        WrapperQueryBuilder wrapperQueryBuilder = QueryBuilders.wrapperQuery(jsonStr);
        return list(wrapperQueryBuilder, SortBuilders.scoreSort(), null, null, PageRequest.of(page, size), null, clazz);
    }

}
