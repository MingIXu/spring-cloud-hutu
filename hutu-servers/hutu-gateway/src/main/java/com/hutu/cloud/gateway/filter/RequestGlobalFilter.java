package com.hutu.cloud.gateway.filter;

import cn.hutool.core.util.StrUtil;
import com.hutu.cloud.core.constant.StringPool;
import com.hutu.cloud.core.enums.CommonStatusEnum;
import com.hutu.cloud.core.exception.GlobalException;
import com.hutu.cloud.core.utils.ReflectionUtils;
import com.hutu.cloud.gateway.constant.GatewayConstant;
import com.hutu.cloud.gateway.util.SignUtil;
import lombok.extern.slf4j.Slf4j;
import net.dreamlu.mica.core.utils.AesUtil;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;
import org.springframework.cloud.gateway.filter.factory.rewrite.CachedBodyOutputMessage;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Function;

/**
 * 请求参数预处理
 * 1. 验签
 * 2. 解密
 *
 * @author hutu
 * @date 2021/4/1 2:07 下午
 */
@Slf4j
@Component
public class RequestGlobalFilter implements GlobalFilter, Ordered {

    private final List<HttpMessageReader<?>> messageReaders;

    private boolean isSign = false;

    public RequestGlobalFilter() {
        this.messageReaders = HandlerStrategies.withDefaults().messageReaders();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // 1. 此处可以加解密参数等

        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();

        // 是否验签
        if (isSign){
            String caSignature = headers.getFirst(GatewayConstant.X_CA_SIGNATURE);
            String caKey = headers.getFirst(GatewayConstant.X_CA_KEY);
            String caNonce = headers.getFirst(GatewayConstant.X_CA_NONCE);
            String caTimestamp = headers.getFirst(GatewayConstant.X_CA_TIMESTAMP);
            String contentMd5 = headers.getFirst(GatewayConstant.X_CONTENT_MD5);
            String serviceId = headers.getFirst(GatewayConstant.SERVICE_ID);
            String serviceMethod = headers.getFirst(GatewayConstant.SERVICE_METHOD);

            if (StrUtil.hasEmpty(caSignature, caKey, caNonce, caTimestamp, contentMd5, serviceId, serviceMethod)) {
                throw new GlobalException(CommonStatusEnum.MISSING_HEADER_PARAMETER);
            }
            String signSource = StrUtil.join(StringPool.AMPERSAND, caKey, caNonce, caTimestamp, contentMd5, serviceId, serviceMethod);
            String singTarget = SignUtil.doSign(signSource);

            if (!caSignature.equals(singTarget)) {
                throw new GlobalException(CommonStatusEnum.VERIFY_SIGN_FAIL);
            }
        }

        // 是否解密
        String serviceEncrypt = headers.getFirst(GatewayConstant.X_SERVICE_ENCRYPT);
        if (StrUtil.isEmpty(serviceEncrypt) || !serviceEncrypt.equals(GatewayConstant.IS_ENCRYPT)) {
            return chain.filter(exchange);
        }

        Class inClass = String.class;
        Class outClass = String.class;
        ServerRequest serverRequest = ServerRequest.create(exchange, messageReaders);

        // TODO: flux or mono
        Mono<?> modifiedBody = serverRequest.bodyToMono(inClass)
                .flatMap(originalBody -> {
                    log.debug("解密参数原文：{}",originalBody);
                    String newBody = AesUtil.decryptFormBase64ToString((String) originalBody,"12345678912345678912345678912345");
                    return Mono.just(newBody);
                })
                .switchIfEmpty(Mono.defer(() -> Mono.just(null)));

        BodyInserter bodyInserter = BodyInserters.fromPublisher(modifiedBody, outClass);
        HttpHeaders newHeaders = new HttpHeaders();
        newHeaders.putAll(headers);

        // the new content type will be computed by bodyInserter
        // and then set in the request decorator
        newHeaders.remove(HttpHeaders.CONTENT_LENGTH);

        CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(exchange, newHeaders);
        return bodyInserter.insert(outputMessage, new BodyInserterContext())
                // .log("modify_request", Level.INFO)
                .then(Mono.defer(() -> {
                    ServerHttpRequest decorator = decorate(exchange, headers, outputMessage);
                    return chain.filter(exchange.mutate().request(decorator).build());
                })).onErrorResume((Function<Throwable, Mono<Void>>) throwable -> release(exchange,
                        outputMessage, throwable));
    }

    ServerHttpRequestDecorator decorate(ServerWebExchange exchange, HttpHeaders headers,
                                        CachedBodyOutputMessage outputMessage) {
        return new ServerHttpRequestDecorator(exchange.getRequest()) {
            @Override
            public HttpHeaders getHeaders() {
                long contentLength = headers.getContentLength();
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.putAll(headers);
                if (contentLength > 0) {
                    httpHeaders.setContentLength(contentLength);
                }
                else {
                    // TODO: this causes a 'HTTP/1.1 411 Length Required' // on
                    // httpbin.org
                    httpHeaders.set(HttpHeaders.TRANSFER_ENCODING, "chunked");
                }
                return httpHeaders;
            }

            @Override
            public Flux<DataBuffer> getBody() {
                return outputMessage.getBody();
            }
        };
    }

    protected Mono<Void> release(ServerWebExchange exchange, CachedBodyOutputMessage outputMessage,
                                 Throwable throwable) {
        boolean cached = (boolean) ReflectionUtils.getFieldValue(outputMessage, "cached");
        if (cached) {
            return outputMessage.getBody().map(DataBufferUtils::release).then(Mono.error(throwable));
        }
        return Mono.error(throwable);
    }

    @Override
    public int getOrder() {
        return NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER - 20;
    }

}
