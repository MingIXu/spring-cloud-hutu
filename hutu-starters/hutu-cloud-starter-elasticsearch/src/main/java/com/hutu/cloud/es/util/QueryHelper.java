package com.hutu.cloud.es.util;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchScrollHits;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.lang.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 扩展 ElasticsearchRestTemplate
 *
 * @author hutu
 * @date 2021/3/24 4:44 下午
 */
@Getter
@Slf4j
public class QueryHelper extends ElasticsearchRestTemplate {

    public QueryHelper(ElasticsearchConverter elasticsearchConverter,
                       RestHighLevelClient elasticsearchClient) {
        super(elasticsearchClient,elasticsearchConverter);
    }

    // region 搜索部分

    public <T> List<T> convertToList(SearchHits<T> searchHits){
        return searchHits.get().map(SearchHit::getContent).collect(Collectors.toList());
    }

    /**
     * TODO 高亮处理
     */
    public <T> void highlight(SearchHits<T> searchHits){
        searchHits.forEach(searchHit -> {
            Map<String, List<String>> highlightFields = searchHit.getHighlightFields();
//            String join = StrUtil.join(StrUtil.EMPTY, values);
//            ReflectUtil.setFieldValue(content, highlightFieldResult, join);
        });
    }
    /**
     * 直接获取实体类列表
     *
     * @return 返回文档实体集合
     */
    public <T> List<T> searchList(Query query, Class<T> clazz) {
        return convertToList(super.search(query,clazz));
    }


    /**
     * 直接写dsl语句查询
     *
     * @param jsonStr json格式语句
     */
    public <T> List<T> search(String jsonStr, Class<T> clazz) {
        return convertToList(super.search(new StringQuery(jsonStr), clazz));
    }

    // endregion
    // region 更新操作

    /**
     * 此方法会局部更新字段，而直接使用save会覆盖掉原es中已有值
     */
    public boolean update(Object targetObject) {
        return update(targetObject, getId(targetObject));
    }

    public boolean update(Object targetObject, String id) {
        UpdateResponse update = super.update(buildUpdateQuery(targetObject, id), super.getIndexCoordinatesFor(targetObject.getClass()));
        return update.getResult().equals(UpdateResponse.Result.UPDATED);
    }

    /**
     * 批量更新
     * @param targetObjects 实体集合
     */
    public void update(Iterable<?> targetObjects) {
        List<UpdateQuery> queryList = new ArrayList<>();
        targetObjects.forEach(o -> queryList.add(buildUpdateQuery(o,getId(o))));
        bulkUpdate(queryList,targetObjects.iterator().next().getClass());
    }

    public boolean upsert(Object targetObject) {
        return update(targetObject, getId(targetObject));
    }

    public boolean upsert(Object targetObject, String id) {
        UpdateResponse update = super.update(buildUpdateQuery(targetObject, id,true), super.getIndexCoordinatesFor(targetObject.getClass()));
        return update.getResult().equals(UpdateResponse.Result.UPDATED);
    }

    /**
     * 批量更新
     * @param targetObjects 实体集合
     */
    public void upsert(Iterable<?> targetObjects) {
        List<UpdateQuery> queryList = new ArrayList<>();
        targetObjects.forEach(o -> queryList.add(buildUpdateQuery(o,getId(o),true)));
        bulkUpdate(queryList,targetObjects.iterator().next().getClass());
    }

    private UpdateQuery buildUpdateQuery(Object targetObject, String id) {
        return buildUpdateQuery(targetObject,id,false);
    }

    /**
     * 组装查询对象
     * @param targetObject 实体
     * @return updateQuery
     */
    private UpdateQuery buildUpdateQuery(Object targetObject, String id, boolean isUpsert) {
        Document document = super.getElasticsearchConverter().mapObject(targetObject);
        return UpdateQuery.builder(id)
                .withDocument(document)
                .withUpsert(document)
                .withDocAsUpsert(isUpsert)
                .build();
    }

    /**
     * 获取实体@Id注解的属性值
     *
     * @param targetObject 目标实体
     * @return id
     */
    private String getId(Object targetObject) {

        return String.valueOf(ReflectUtil.getFieldValue(targetObject, getFieldByAnnotation(targetObject.getClass(), Id.class)));
    }

    /**
     * 获取实体类中标记指定注解的 field
     *
     * @param entityClass 实体类
     * @return field
     */
    public static <T> Field getFieldByAnnotation(Class<T> entityClass, Class<? extends Annotation> annotation) {
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            Annotation fieldAnnotation = field.getAnnotation(annotation);
            if (fieldAnnotation != null) {
                return field;
            }
        }
        throw new RuntimeException(entityClass + " 找不到带注解" + annotation.getName() + " field");
    }
    // endregion

    // region 滚动查询

    public <T> SearchScrollHits<T> searchScroll(@Nullable String scrollId, Query query, int maxResults, Class<T> entityClass) {
        // 设置每页数据量
        if (query instanceof NativeSearchQuery) {
            ((NativeSearchQuery) query).setMaxResults(maxResults);
        } else {
            query.setPageable(PageRequest.of(0, maxResults));
        }
        long scrollTimeInMillis = 60 * 1000;
        IndexCoordinates index = super.getIndexCoordinatesFor(entityClass);
        SearchScrollHits<T> searchScrollHits;
        if (StrUtil.isEmpty(scrollId)) {
            // 第一次查询
            searchScrollHits = super.searchScrollStart(scrollTimeInMillis, query, entityClass, index);
        } else {
            // 后续查询
            searchScrollHits = super.searchScrollContinue(scrollId, scrollTimeInMillis, entityClass, index);
        }
        // 清除 scroll
        super.searchScrollClear(Collections.singletonList(scrollId));
        return searchScrollHits;
    }

    // endregion
}
