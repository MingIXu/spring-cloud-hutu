package com.hutu.config;

import com.hutu.properties.ElasticsearchProperties;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.CustomEsRestTemplate;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;

/**
 * es 配置信息
 *
 * @author hutu
 * @date 2020/12/3 5:17 下午
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(ElasticsearchProperties.class)
public class ElasticsearchAutoConfig {

    @Bean(name = { "elasticsearchOperations", "elasticsearchTemplate" })
    public CustomEsRestTemplate customEsRestTemplate(RestHighLevelClient client, ElasticsearchConverter elasticsearchConverter,ElasticsearchProperties elasticsearchProperties) {
        return new CustomEsRestTemplate(client, elasticsearchConverter,elasticsearchProperties);
    }
}
