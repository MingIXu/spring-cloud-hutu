package com.hutu.cloud.es.config;

import cn.hutool.core.util.ArrayUtil;
import com.hutu.cloud.es.init.InitElasticsearch;
import com.hutu.cloud.es.properties.CustomElasticsearchProperties;
import com.hutu.cloud.es.utils.CustomElasticsearchRestTemplate;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchRestClientProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;

/**
 * es 自动配置类
 *
 * @author hutu
 * @date 2021/3/22 2:26 下午
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({ CustomElasticsearchProperties.class, ElasticsearchRestClientProperties.class })
public class CustomElasticsearchAutoConfiguration extends AbstractElasticsearchConfiguration {

	final ElasticsearchRestClientProperties properties;

	public CustomElasticsearchAutoConfiguration(ElasticsearchRestClientProperties properties) {
		this.properties = properties;
	}

	/**
	 * 可定制客户端参数，如添加 headers and other parameters 等
	 * 参考：https://docs.spring.io/spring-data/elasticsearch/docs/4.1.6/reference/html/#elasticsearch.clients.configuration
	 */
	@Override
	@Bean
	public RestHighLevelClient elasticsearchClient() {

		final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
				.connectedTo(ArrayUtil.toArray(properties.getUris(), String.class)).build();

		return RestClients.create(clientConfiguration).rest();
	}

	/**
	 * 注入自定义 elasticsearchTemplate
	 * @param elasticsearchConverter
	 * @param elasticsearchClient
	 * @param elasticsearchProperties
	 * @return ElasticsearchOperations
	 */
	@Bean(name = { "elasticsearchOperations", "elasticsearchTemplate" })
	public CustomElasticsearchRestTemplate elasticsearchOperations(ElasticsearchConverter elasticsearchConverter,
			RestHighLevelClient elasticsearchClient, CustomElasticsearchProperties elasticsearchProperties) {
		return new CustomElasticsearchRestTemplate(elasticsearchClient, elasticsearchConverter,
				elasticsearchProperties);
	}

	@Bean
	public InitElasticsearch initElasticsearch(ElasticsearchOperations elasticsearchOperations,
			CustomElasticsearchProperties properties) {
		return new InitElasticsearch(properties, elasticsearchOperations);
	}

}