package com.hutu.cloud.es;

import com.hutu.cloud.es.utils.CustomElasticsearchRestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.bytes.BytesArray;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.BulkByScrollTask;
import org.elasticsearch.index.reindex.ReindexRequest;
import org.elasticsearch.index.reindex.RemoteInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * 同步业务数据
 *
 * @author hutu
 * @date 2020/9/25 5:57 下午
 */
@Slf4j
public class ReIndexTest extends ElasticApplicationTest {

	@Autowired
	RestHighLevelClient elasticsearchClient;

	@Autowired
	CustomElasticsearchRestTemplate elasticsearchOperations;

	@Test
	public void reindex() {
		String source = "book2";
		String dest = "book5";
		reindex(source, dest);
	}

	public void reindex(String source, String dest) {

		ReindexRequest request = new ReindexRequest().setSourceIndices(source).setDestIndex(dest)
				.setDestOpType("create").setTimeout(TimeValue.timeValueMinutes(2)).setRefresh(true);
		// 从远程备份数据
		// request.setRemoteInfo(getRemoteInfo());
		// 冲突跳过
		request.setConflicts("proceed");
		try {
			BulkByScrollResponse bulkResponse = elasticsearchClient.reindex(request, RequestOptions.DEFAULT);
			BulkByScrollTask.Status status = bulkResponse.getStatus();
			log.info("reasonCancelled: {}", status.getReasonCancelled());
			log.info("create count: {}", status.getCreated());
			log.info("delete count: {}", status.getDeleted());
			log.info("update count: {}", status.getUpdated());
		}
		catch (IOException e) {
			log.error("备份 index 失败", e);
		}
	}

	public void aliasAndDelete(Class<?> source) {
		// elasticsearchOperations
	}

	public RemoteInfo getRemoteInfo() {
		String remoteHost = "172.16.2.139";
		int remotePort = 9200;
		String user = "user";
		String password = "password";
		return new RemoteInfo("http", remoteHost, remotePort, null,
				new BytesArray(new MatchAllQueryBuilder().toString()), user, password, Collections.emptyMap(),
				new TimeValue(100, TimeUnit.MILLISECONDS), new TimeValue(100, TimeUnit.SECONDS));
	}

}
