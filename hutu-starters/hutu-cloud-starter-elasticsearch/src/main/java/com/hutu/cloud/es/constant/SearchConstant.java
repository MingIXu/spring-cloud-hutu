package com.hutu.cloud.es.constant;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;

/**
 * SearchConstant
 *
 * @author hutu
 * @date 2021/9/28 2:38 下午
 */
public interface SearchConstant {

    /**
     * 关键字前缀，用于精确搜索
     */
    String KEYWORD = "keyword";
    /**
     * 最大可能 ik 分词
     */
    String IK_MAX_WORD = "ik_max_word";
    /**
     * ik 分词聪明模式，只分出常用的，减少存储空间
     */
    String IK_SMART = "ik_smart";
    /**
     * 拼音分词
     */
    String PINYIN = "pinyin";
    /**
     * 标准分词器（es 默认）
     * 对英文数字分词效果不错
     * 对中文分词不理想，只是做了文字切割成单个汉字
     */
    String STANDARD = "standard";

    /**
     * es默认schema
     */
    String DEFAULT_SCHEMA = "http";

    String PINYIN_CHINESE_ANALYZER = "pinyin_chinese_analyzer";

    String PINYIN_ANALYZER = "pinyin_analyzer";
    /**
     * 存到 ThreadLocal 中的 es 日志 key
     */
    String ES_LOG_ID = "es_log_id";
    /**
     * 暂存es日志的缓存，用于测试查看完整 dsl 语句
     */
    Cache<String,String> LOG_CACHE = CacheUtil.newFIFOCache(1000,60*1000);

    String CHECK_DATA_LOG_ID = "check_data_log_id";

}
