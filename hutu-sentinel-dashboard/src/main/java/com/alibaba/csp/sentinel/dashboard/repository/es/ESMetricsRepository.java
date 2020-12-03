package com.alibaba.csp.sentinel.dashboard.repository.es;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.MetricEntity;
import com.alibaba.csp.sentinel.dashboard.repository.metric.MetricsRepository;
import com.alibaba.csp.sentinel.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.MatchPhraseQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @desc 监控数据持久化到es
 *
 * @author hutu
 * @date 2020/12/3 10:03 上午
 */
@Repository("esMetricsRepository")
@Primary
@Slf4j
public class ESMetricsRepository implements MetricsRepository<MetricEntity> {

    @Autowired
    ElasticsearchOperations elasticsearchOperations;

    @Override
    public void save(MetricEntity metric) {

        if (metric == null || StringUtil.isBlank(metric.getApp())) {
            return;
        }

        ESMetric esMetric = new ESMetric();

        BeanUtils.copyProperties(metric, esMetric);
        esMetric.setTimestamp(metric.getTimestamp().getTime());

        //保存监控数据到ES中
        elasticsearchOperations.save(esMetric);
    }

    @Override
    public void saveAll(Iterable<MetricEntity> metrics) {
        if (metrics == null) {
            log.debug("metrics is empty");
            return;
        }

        metrics.forEach(this::save);
    }

    @Override
    public List<MetricEntity> queryByAppAndResourceBetween(String app, String resource, long startTime, long endTime) {
        List<MetricEntity> results = new ArrayList<>();

        if (StringUtil.isBlank(app)) {
            return results;
        }

        if (StringUtil.isBlank(resource)) {
            return results;
        }

        //多条件查询设置
        MatchPhraseQueryBuilder mpqb1 = QueryBuilders.matchPhraseQuery("app", app);
        MatchPhraseQueryBuilder mpqb2 = QueryBuilders.matchPhraseQuery("resource", resource);

        // 设置时间过滤
        RangeQueryBuilder rqb1 = QueryBuilders.rangeQuery("timestamp").gte(startTime).lte(endTime);

        QueryBuilder queryBuilder = QueryBuilders.boolQuery().must(mpqb1).must(mpqb2).must(rqb1);
        NativeSearchQuery query = new NativeSearchQueryBuilder().withQuery(queryBuilder).build();


        List<ESMetric> esMetrics = elasticsearchOperations.search(query, ESMetric.class).get().map(SearchHit::getContent).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(esMetrics)) {
            return results;
        }
        for (ESMetric esMetric : esMetrics) {
            MetricEntity metricEntity = new MetricEntity();
            BeanUtils.copyProperties(esMetric, metricEntity);
            metricEntity.setTimestamp(Date.from(Instant.ofEpochMilli(esMetric.getTimestamp())));
            results.add(metricEntity);
        }
        return results;
    }

    @Override
    public List<String> listResourcesOfApp(String app) {

        List<String> results = new ArrayList<>();

        if (StringUtil.isBlank(app)) {
            return results;
        }

        //多条件查询设置
        MatchPhraseQueryBuilder mpqb1 = QueryBuilders.matchPhraseQuery("app", app);
//        MatchPhraseQueryBuilder mpqb2 = QueryBuilders.matchPhraseQuery("resource", resource);
        //设置时间过滤
        long startTime = System.currentTimeMillis() - 1000 * 60 * 60;
        RangeQueryBuilder rqb1 = QueryBuilders.rangeQuery("timestamp").gte(startTime);

        QueryBuilder queryBuilder = QueryBuilders.boolQuery().must(mpqb1).must(rqb1);

        //建立查询
        NativeSearchQuery query = new NativeSearchQueryBuilder().withQuery(queryBuilder).build();

        List<MetricEntity> metricEntities = new ArrayList<MetricEntity>();

        List<ESMetric> esMetrics = elasticsearchOperations.search(query, ESMetric.class).get().map(SearchHit::getContent).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(esMetrics)) {
            return results;
        }

        for (ESMetric esMetric : esMetrics) {
            MetricEntity metricEntity = new MetricEntity();
            BeanUtils.copyProperties(esMetric, metricEntity);
            metricEntities.add(metricEntity);
        }

        Map<String, MetricEntity> resourceCount = new HashMap<>(32);

        for (MetricEntity metricEntity : metricEntities) {
            String resource = metricEntity.getResource();
            if (resourceCount.containsKey(resource)) {
                MetricEntity oldEntity = resourceCount.get(resource);
                oldEntity.addPassQps(metricEntity.getPassQps());
                oldEntity.addRtAndSuccessQps(metricEntity.getRt(), metricEntity.getSuccessQps());
                oldEntity.addBlockQps(metricEntity.getBlockQps());
                oldEntity.addExceptionQps(metricEntity.getExceptionQps());
                oldEntity.addCount(1);
            } else {
                resourceCount.put(resource, MetricEntity.copyOf(metricEntity));
            }
        }

        // Order by last minute b_qps DESC.
        return resourceCount.entrySet()
                .stream()
                .sorted((o1, o2) -> {
                    MetricEntity e1 = o1.getValue();
                    MetricEntity e2 = o2.getValue();
                    int t = e2.getBlockQps().compareTo(e1.getBlockQps());
                    if (t != 0) {
                        return t;
                    }
                    return e2.getPassQps().compareTo(e1.getPassQps());
                })
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
