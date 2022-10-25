package com.hutu.cloud.es.interceptor;

import cn.hutool.core.util.StrUtil;
import com.hutu.cloud.es.properties.CustomElasticsearchProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.*;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.protocol.HttpContext;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * CustomLoggingInterceptor
 *
 * @author hutu
 * @date 2022/3/14 1:52 下午
 */
@Slf4j
public class LoggingInterceptor implements HttpResponseInterceptor, HttpRequestInterceptor {
    public final CustomElasticsearchProperties properties;

    public LoggingInterceptor(CustomElasticsearchProperties properties) {
        this.properties = properties;
    }

    /**
     * 请求拦截处理，打印 dsl 语句与入参
     */
    @Override
    public void process(HttpRequest request, HttpContext context) throws IOException {
        if (request instanceof HttpEntityEnclosingRequest && ((HttpEntityEnclosingRequest) request).getEntity() != null) {
            HttpEntityEnclosingRequest entityRequest = (HttpEntityEnclosingRequest) request;
            HttpEntity entity = ((HttpEntityEnclosingRequest) request).getEntity();
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            entity.writeTo(buffer);
            if (!entity.isRepeatable()) {
                entityRequest.setEntity(new ByteArrayEntity(buffer.toByteArray()));
            }
//            if (ThreadLocalMap.get(SearchConstant.ES_LOG_ID) != null) {
//                SearchConstant.LOG_CACHE.put(ThreadLocalMap.get(SearchConstant.ES_LOG_ID).toString(), StrUtil.join(" ", request.getRequestLine().getMethod(), request.getRequestLine().getUri(), new String(buffer.toByteArray())));
//                ThreadLocalMap.remove(SearchConstant.ES_LOG_ID);
//            }
            if (properties.isEnableLog()) {
                log.info("request url: {} {} \n parameter: {}", request.getRequestLine().getMethod(), request.getRequestLine().getUri(), new String(buffer.toByteArray()));
            }

        } else {
//            if (ThreadLocalMap.get(SearchConstant.ES_LOG_ID) != null) {
//                SearchConstant.LOG_CACHE.put(ThreadLocalMap.get(SearchConstant.ES_LOG_ID).toString(), StrUtil.join(" ", request.getRequestLine().getMethod(), request.getRequestLine().getUri()));
//                ThreadLocalMap.remove(SearchConstant.ES_LOG_ID);
//            }
            if (properties.isEnableLog()) {
                log.info("request url: {} {}", request.getRequestLine().getMethod(), request.getRequestLine().getUri());
            }
        }
    }

    /**
     * 返回拦截处理逻辑，打印 es 返回状态码
     */
    @Override
    public void process(HttpResponse response, HttpContext context) {
        if (properties.isEnableLog()) {
            log.info("Received raw response: {}", response.getStatusLine().getStatusCode());
        }
    }
}