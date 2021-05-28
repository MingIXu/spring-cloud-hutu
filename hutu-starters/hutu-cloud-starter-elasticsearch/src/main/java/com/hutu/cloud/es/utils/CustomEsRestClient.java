package com.hutu.cloud.es.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 封装highRestClient通用方法
 *
 * @author hutu
 * @date 2020/8/19 2:41 下午
 */
public class CustomEsRestClient {

	final RestHighLevelClient client;

	public CustomEsRestClient(RestHighLevelClient client) {
		this.client = client;
	}

	public RestHighLevelClient getEsClient() {
		return client;
	}

	/**
	 * 新增单条文档数据
	 * @param indexName 索引名称
	 * @param id
	 * @throws Exception
	 */
	public void createDocument(String indexName, String id, Map<String, Object> mpParams) throws Exception {
		RestHighLevelClient client = this.getEsClient();
		// 指定单条文档数据，最终会转化成Json格式
		XContentBuilder builder = XContentFactory.jsonBuilder();
		builder.startObject();
		{
			for (Map.Entry<String, Object> entry : mpParams.entrySet()) {
				if (entry.getValue() != null) {
					builder.field(entry.getKey(), entry.getValue());
				}
			}
		}
		builder.endObject();
		// 创建新增文档数据的请求
		IndexRequest indexRequest = new IndexRequest(indexName).id(id).source(builder);
		// 手动指定路由的key，文档查询时可提高性能
		// indexRequest.routing("");
		// 等待主分片保存的超时时长
		indexRequest.timeout(TimeValue.timeValueSeconds(1));
		// 刷新策略，WAIT_UNTIL设置则表示刷新使此请求的内容对搜索可见为止
		indexRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
		// 操作类型为新增
		indexRequest.opType(DocWriteRequest.OpType.CREATE);

		// 异步执行新增文档数据请求
		client.indexAsync(indexRequest, RequestOptions.DEFAULT, new ActionListener<IndexResponse>() {
			@Override
			public void onResponse(IndexResponse indexResponse) {
				System.out.println(indexResponse.toString());
			}

			@Override
			public void onFailure(Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * 新增单条文档数据
	 * @param indexName 索引名称
	 * @param id 自定义文档id
	 * @param object 文档对应java对象
	 * @throws Exception
	 */
	public void createDocument(String indexName, String id, Object object) throws Exception {
		createDocument(indexName, id, BeanUtil.beanToMap(object));
	}

	/**
	 * 更新文档内容
	 * @param indexName 索引名称
	 * @param id
	 */
	public void updateDocument(String indexName, String id, Map<String, Object> jsonMap) throws Exception {
		RestHighLevelClient client = this.getEsClient();
		// 更新部分文档内容
		jsonMap.entrySet().removeIf(entry -> entry.getValue() == null);

		// 创建更新文档请求并设置参数
		UpdateRequest updateRequest = new UpdateRequest(indexName, id);
		updateRequest.doc(jsonMap);
		// 主分片执行更新的超时时长
		updateRequest.timeout(TimeValue.timeValueSeconds(1));
		// 刷新策略
		updateRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
		// 重试更新操作多少次
		updateRequest.retryOnConflict(3);

		// 异步执行更新文档的请求
		client.updateAsync(updateRequest, RequestOptions.DEFAULT, new ActionListener<UpdateResponse>() {
			@Override
			public void onResponse(UpdateResponse updateResponse) {
				System.out.println(updateResponse);
			}

			@Override
			public void onFailure(Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * 更新文档内容
	 * @param indexName 索引名称
	 * @param id 文档id
	 * @param object 文档对应java对象
	 */
	public void updateDocument(String indexName, String id, Object object) throws Exception {
		updateDocument(indexName, id, BeanUtil.beanToMap(object));
	}

	/**
	 * 删除文档数据
	 * @param indexName 索引名称
	 * @param id
	 */
	public void deleteDocument(String indexName, String id) {
		RestHighLevelClient client = this.getEsClient();
		DeleteRequest deleteRequest = new DeleteRequest(indexName, id);
		// 主分片执行删除的超时时长
		deleteRequest.timeout(TimeValue.timeValueMinutes(2));
		// 刷新策略
		deleteRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
		// 异步执行删除
		client.deleteAsync(deleteRequest, RequestOptions.DEFAULT, new ActionListener<DeleteResponse>() {
			@Override
			public void onResponse(DeleteResponse deleteResponse) {
				System.out.println("删除成功:" + deleteResponse.toString());
			}

			@Override
			public void onFailure(Exception e) {
				System.out.println("删除失败:" + e.getMessage());
			}
		});
	}

	/**
	 * 文档数据量插入
	 * @param mapList 行数据
	 * @param indexName 索引
	 * @param idName 主键名称
	 * @return
	 * @throws Exception
	 */
	public void importDocument(List<Map> mapList, String indexName, String idName) {
		IndexRequest indexRequest = null;
		BulkRequest bulkRequest = new BulkRequest();
		RestHighLevelClient restHighLevelClient = null;
		try {
			restHighLevelClient = this.getEsClient();
			for (Map mp : mapList) {
				indexRequest = new IndexRequest(indexName).id(mp.get(idName).toString()).source(mp);
				bulkRequest.add(indexRequest);
			}
			BulkResponse bulkResponse = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
			System.out.println(bulkResponse.toString());
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		finally {
			try {
				restHighLevelClient.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 检索、分页
	 * @param indexName 索引名称
	 * @param mpParams 查询参数
	 * @param from 起始页
	 * @param size 每页数量
	 * @param fieldArray 返回列数组
	 * @param preciseQuery 1:精确查询 2:模糊查询
	 * @return
	 */
	public List<Map<String, Object>> searchDocument(String indexName, Map<String, Object> mpParams, int from, int size,
			String[] fieldArray, Integer preciseQuery) {
		RestHighLevelClient restHighLevelClient = null;
		SearchRequest searchRequest = new SearchRequest(indexName);
		// 大多数搜索参数添加到searchSourceBuilder
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		List<Map<String, Object>> mapList = new ArrayList<>();
		try {
			restHighLevelClient = this.getEsClient();
			// 组合字段查询
			BoolQueryBuilder boolQueryBuilder = this.getBoolQueryBuilder(mpParams, preciseQuery);

			searchSourceBuilder.query(boolQueryBuilder);
			// 自定义返回列
			if (fieldArray != null && fieldArray.length > 0) {
				searchSourceBuilder.fetchSource(fieldArray, null);
			}
			// 按照Id倒序
			searchSourceBuilder.sort(new FieldSortBuilder("id").order(SortOrder.DESC));
			// 分页
			searchSourceBuilder.from(from);
			searchSourceBuilder.size(size);
			// 允许搜索的超时时长
			searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
			searchRequest.source(searchSourceBuilder);
			SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
			// 返回结果
			SearchHits searchHitArray = searchResponse.getHits();
			for (SearchHit searchHit : searchHitArray.getHits()) {
				Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
				mapList.add(sourceAsMap);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				restHighLevelClient.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		return mapList;
	}

	/**
	 * 组合字段查询条件
	 * @param mpParams
	 * @param preciseQuery 1:精确查询 2:模糊查询
	 * @return
	 */
	public BoolQueryBuilder getBoolQueryBuilder(Map<String, Object> mpParams, Integer preciseQuery) {
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		try {
			if (mpParams != null) {
				for (Map.Entry<String, Object> entry : mpParams.entrySet()) {
					if (preciseQuery != null && preciseQuery == 1) {
						// 精确匹配
						TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery(entry.getKey() + ".keyword",
								entry.getValue());
						boolQueryBuilder = boolQueryBuilder.must(termQueryBuilder);
					}
					else {
						// 模糊匹配
						MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(entry.getKey(),
								entry.getValue());
						boolQueryBuilder = boolQueryBuilder.must(matchQueryBuilder);
					}
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return boolQueryBuilder;
	}

	/**
	 * 查询结果聚合
	 * @param mpParams 查询参数
	 * @param indexName 索引名称
	 * @param fieldName 字段名称
	 * @param preciseQuery 1:精确查询 2:模糊查询
	 * @return
	 */
	public Map<String, Object> searchAggregationDocument(Map<String, Object> mpParams, String indexName,
			String fieldName, Integer preciseQuery) {
		Map<String, Object> mpResult = new HashMap<>();
		RestHighLevelClient restHighLevelClient = null;
		SearchResponse searchResponse = null;
		try {
			restHighLevelClient = this.getEsClient();
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
			// 指定要聚合的字段，类似数据库的group by
			TermsAggregationBuilder aggregation = AggregationBuilders.terms(fieldName).field(fieldName);
			searchSourceBuilder.aggregation(aggregation);
			// 组合字段查询
			BoolQueryBuilder boolQueryBuilder = this.getBoolQueryBuilder(mpParams, preciseQuery);
			searchSourceBuilder.query(boolQueryBuilder);
			// 执行查询请求
			SearchRequest searchRequest = new SearchRequest(indexName);
			searchRequest.source(searchSourceBuilder);
			searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
			// 汇总聚合结果
			Aggregations aggregations = searchResponse.getAggregations();
			Terms byCompanyAggregation = aggregations.get(fieldName);
			if (byCompanyAggregation != null) {
				List<Terms.Bucket> bucketList = (List<Terms.Bucket>) byCompanyAggregation.getBuckets();
				for (Terms.Bucket bucket : bucketList) {
					String key = bucket.getKeyAsString();
					Long value = bucket.getDocCount();
					mpResult.put(key, value);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				restHighLevelClient.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		return mpResult;
	}

	/**
	 * 直接写dsl语句查询
	 * @param jsonStr json格式语句
	 */
	public <T> List<T> searchByJsonStr(String indexName, String jsonStr, Class<T> clazz) throws IOException {
		Assert.notEmpty(jsonStr);
		WrapperQueryBuilder wrapperQueryBuilder = QueryBuilders.wrapperQuery(jsonStr);
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(wrapperQueryBuilder);
		searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
		SearchRequest searchRequest = new SearchRequest(indexName);
		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		// 返回结果
		SearchHits searchHits = searchResponse.getHits();
		ArrayList<T> list = new ArrayList<>();
		for (SearchHit searchHit : searchHits.getHits()) {
			Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
			list.add(BeanUtil.toBean(sourceAsMap, clazz));
		}
		return list;
	}

}
