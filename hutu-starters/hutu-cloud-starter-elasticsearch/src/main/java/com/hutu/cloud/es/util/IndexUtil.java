package com.hutu.cloud.es.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.core.TimeValue;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.BulkByScrollTask;
import org.elasticsearch.index.reindex.ReindexRequest;
import org.elasticsearch.index.reindex.RemoteInfo;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;

import java.util.HashMap;
import java.util.Map;

/**
 * es 索引操作工具类
 *
 * @author hutu
 * @date 2021/4/13 3:42 下午
 */
@Slf4j
@Setter
public class IndexUtil {

    /**
     * 索引名称与索引操作类缓存
     */
    private static final Map<String, IndexOperations> INDEX_NAME_OPERATIONS_CACHE = new HashMap<>(16);

    private static QueryHelper queryHelper;

    public static void of(QueryHelper queryHelper){
        IndexUtil.queryHelper = queryHelper;
    }

    public static IndexOperations getIndexOperations(String indexName) {
        return INDEX_NAME_OPERATIONS_CACHE.getOrDefault(indexName, queryHelper.indexOps(IndexCoordinates.of(indexName)));
    }

    public static IndexOperations getIndexOperations(Class<?> indexName) {
        return INDEX_NAME_OPERATIONS_CACHE.getOrDefault(indexName.getSimpleName(), queryHelper.indexOps(indexName));
    }

    // region 索引创建与更新

    /**
     * 创建索引与mapping
     */
    public static void createIndexAndMapping(String indexName) {
        createIndex(indexName);
        putMapping(indexName);
    }

    public static void createIndexAndMapping(Class<?> indexName) {
        createIndex(indexName);
        putMapping(indexName);
    }

    /**
     * 脚本建索引与mapping
     *
     * @param indexName                索引名称
     * @param alias                    索引别名
     * @param indexSettingJsonScript   索引创建脚本
     * @param mappingSettingJsonScript mapping脚本
     */
    public static void createIndexAndMapping(String indexName, String alias, String indexSettingJsonScript, String mappingSettingJsonScript) {
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        // 添加分片和备份参数
        if (StrUtil.isNotEmpty(indexSettingJsonScript)) {
            request.settings(indexSettingJsonScript, XContentType.JSON);
        }
        // 设置mapping属性
        if (StrUtil.isNotEmpty(mappingSettingJsonScript)) {
            request.mapping(mappingSettingJsonScript, XContentType.JSON);
        }
        // 添加别名
        if (StrUtil.isNotEmpty(alias)) {
            request.alias(new Alias(alias));
        }
        //创建索引
        CreateIndexResponse createIndexResponse = queryHelper.execute(client -> client.indices().create(request, RequestOptions.DEFAULT));

        //得到响应（全部）
        boolean acknowledged = createIndexResponse.isAcknowledged();
        log.info("acknowledged: {}", acknowledged);
    }

    /**
     * 创建索引（还没初始化 mapping）
     */
    public static void createIndex(String indexName) {
        IndexOperations ops = getIndexOperations(indexName);
        // 检查索引是否存在
        if (!ops.exists()) {
            // 创建索引
            log.info("create index [{}] ,status: {}", indexName, ops.create());
        } else {
            log.warn("The index :[{}] is nearly created", indexName);
        }
    }

    public static void createIndex(Class<?> indexName) {
        IndexOperations ops = getIndexOperations(indexName);
        // 检查索引是否存在
        if (!ops.exists()) {
            // 创建索引
            log.info("create index [{}] ,status: {}", indexName, ops.create());
        } else {
            log.warn("The index :[{}] is nearly created", indexName);
        }
    }

    /**
     * 创建并刷新 mapping
     */
    public static void putMapping(String indexName) {
        IndexOperations ops = getIndexOperations(indexName);
        // 创建 mapping
        log.info("create [{}] mapping status: {}", indexName, ops.putMapping());
        // 刷新
        ops.refresh();
    }

    public static void putMapping(Class<?> indexName) {
        IndexOperations ops = getIndexOperations(indexName);
        // 创建 mapping
        log.info("create [{}] mapping status: {}", indexName, ops.putMapping());
        // 刷新
        ops.refresh();
    }

    /**
     * 删除索引
     */
    public static void delete(String indexName) {
        IndexOperations ops = getIndexOperations(indexName);
        // 删索引
        log.info("delete {} index status: {}", indexName, ops.delete());
    }

    public static void delete(Class<?> indexName) {
        IndexOperations ops = getIndexOperations(indexName);
        // 删索引
        log.info("delete {} index status: {}", indexName, ops.delete());
    }

    // endregion 索引创建与更新
    // region 备份索引

    /**
     * 备份索引
     */
    public static void reindex(String source, String dest) {
        reindex(source, dest, null);
    }

    /**
     * 备份索引
     * 1. 创建目标索引
     * 2. 从源索引同步数据到目标索引
     */
    public static void reindex(String source, String dest, RemoteInfo remoteInfo) {

        queryHelper.execute(restHighLevelClient -> {
            IndexOperations indexOperations = getIndexOperations(source);
            Map<String, Object> settings = indexOperations.getSettings();
            Map<String, Object> mappingSetting = indexOperations.getMapping();
            HashMap<Object, Object> indexSetting = new HashMap<>();
            indexSetting.put("index.number_of_shards", settings.get("index.number_of_shards"));
            indexSetting.put("index.number_of_replicas", settings.get("index.number_of_replicas"));
            indexSetting.put("index.store.type", settings.get("index.store.type"));
            indexSetting.put("index.refresh_interval", settings.get("index.refresh_interval"));
            String indexSettingJson = JSONUtil.toJsonStr(indexSetting);
            String mappingSettingJson = JSONUtil.toJsonStr(mappingSetting);
            createIndexAndMapping(dest, null, indexSettingJson, mappingSettingJson);
            return null;
        });

        ReindexRequest request = new ReindexRequest().setSourceIndices(source).setDestIndex(dest)
                // 只同步新数据
//                .setDestOpType("create")
                // 过滤条件
//				.setSourceQuery(QueryBuilders.termQuery("",""))
                .setTimeout(TimeValue.timeValueMinutes(2))
                .setRefresh(true);
        // 从远程备份数据
        if (remoteInfo != null) {
            request.setRemoteInfo(remoteInfo);
        }
        // 冲突跳过
        request.setConflicts("proceed");
        queryHelper.execute(restHighLevelClient -> {
            BulkByScrollResponse bulkResponse = restHighLevelClient.reindex(request, RequestOptions.DEFAULT);
            BulkByScrollTask.Status status = bulkResponse.getStatus();
            log.info("reasonCancelled: {}", status.getReasonCancelled());
            log.info("create count: {}", status.getCreated());
            log.info("delete count: {}", status.getDeleted());
            log.info("update count: {}", status.getUpdated());
            return "success";
        });
    }

    // endregion 备份索引
}
