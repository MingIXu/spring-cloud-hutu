package com.hutu.config;

import com.hutu.utils.CustomEsRestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@Import(CustomEsRestTemplate.class)
//@EnableElasticsearchRepositories(
//        basePackages = "org.springframework.data.elasticsearch.repository"
//        basePackages = "com.hutu.es"
//)
public class EsAutoConfig {

}
