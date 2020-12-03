package com.alibaba.csp.sentinel.utils;

import org.elasticsearch.search.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;

import java.util.List;
import java.util.stream.Collectors;

public class ElasticsearchUtils {

    public static <T> List toList(SearchHits<T> searchHits){
        return searchHits.toList();
    }
}
