package com.hutu.cloud.es.config;

import cn.hutool.core.text.StrPool;
import cn.hutool.extra.spring.SpringUtil;
import com.hutu.cloud.core.constant.StringPool;
import com.hutu.cloud.es.interceptor.LoggingInterceptor;
import com.hutu.cloud.es.properties.CustomElasticsearchProperties;
import com.hutu.cloud.es.util.IndexUtil;
import com.hutu.cloud.es.util.QueryHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponseInterceptor;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;
import org.springframework.data.elasticsearch.core.mapping.SimpleElasticsearchMappingContext;
import org.springframework.data.util.TypeInformation;

import java.util.Collection;
import java.util.Collections;

/**
 * es 自动配置类
 *
 * @author hutu
 * @date 2021/3/22 2:26 下午
 */
@Slf4j
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({ElasticsearchProperties.class,CustomElasticsearchProperties.class})
public class ElasticsearchAutoConfiguration extends AbstractElasticsearchConfiguration {

    private final CustomElasticsearchProperties customProperties;
    private final ElasticsearchProperties properties;

    /**
     * 覆盖默认client配置，使用RestClients创建
     * https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/#elasticsearch.clients.configuration
     */
    @Override
    public RestHighLevelClient elasticsearchClient() {
        return RestClients.create(ClientConfiguration.builder()
                .connectedTo(properties.getUris().toArray(new String[0]))
                .withBasicAuth(properties.getUsername(), properties.getPassword())
                .withClientConfigurer(
                        RestClients.RestClientConfigurationCallback.from(clientBuilder -> {
                            // 自定义日志打印逻辑
                            LoggingInterceptor interceptor = new LoggingInterceptor(customProperties);
                            clientBuilder.addInterceptorLast((HttpRequestInterceptor) interceptor);
                            clientBuilder.addInterceptorLast((HttpResponseInterceptor) interceptor);
                            return clientBuilder;
                        })).build()).rest();
    }
    @Override
    protected Collection<String> getMappingBasePackages() {
        return Collections.singleton(customProperties.getBasePackages());
    }

    /**
     * 注入自定义 elasticsearchTemplate
     */
    @Override
    @Bean(name = {"elasticsearchOperations", "elasticsearchTemplate", "queryHelper"})
    public ElasticsearchOperations elasticsearchOperations(ElasticsearchConverter elasticsearchConverter,
                                                           RestHighLevelClient elasticsearchClient) {
        final QueryHelper queryHelper = new QueryHelper(elasticsearchConverter, elasticsearchClient);
        queryHelper.setRefreshPolicy(refreshPolicy());
        // 初始化 indexUtil
        IndexUtil.of(queryHelper);
        return queryHelper;
    }

    /**
     * 索引前缀，用于动态拼接 indexName
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = "indexPrefix", prefix = CustomElasticsearchProperties.PREFIX)
    public String indexNamePrefix() {
        return customProperties.getIndexPrefix();
    }

    /**
     * 索引后缀，用于动态拼接 indexName
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = "indexSuffix", prefix = CustomElasticsearchProperties.PREFIX)
    public String indexNameSuffix() {
        return customProperties.getIndexSuffix();
    }

//    @Bean
//    @ConditionalOnBean(IndexUtil.class)
//    public CommandLineRunner indexInitRunner(SimpleElasticsearchMappingContext context) {
//        final Collection<TypeInformation<?>> managedTypes = context.getManagedTypes();
//        return args -> {
//            Set<Class<?>> classes = ScanAnnotationUtil.scan(tempestProperties.getBasePackages(), Document.class);
//            if (classes.size() != 0) {
//                for (Class<?> aClass : classes) {
//                    // 检查是否创建索引，没有则创建
//                    try {
//                        IndexUtil.createIndexAndMapping(aClass);
//                    } catch (UncategorizedElasticsearchException e) {
//                        // 打印刷新失败日志
//                        log.error(e.getMessage());
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        log.error(e.getMessage());
//                    }
//                }
//            }
//            log.info("elasticsearch index and mapping init success ！");
//        };
//    }
}