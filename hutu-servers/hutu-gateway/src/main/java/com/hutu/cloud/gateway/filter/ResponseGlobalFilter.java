package com.hutu.cloud.gateway.filter;

import com.hutu.cloud.core.R;
import com.hutu.cloud.core.utils.SecureUtils;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;
import org.springframework.cloud.gateway.filter.factory.rewrite.CachedBodyOutputMessage;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.ORIGINAL_RESPONSE_CONTENT_TYPE_ATTR;

/**
 * 返回数据处理
 * 1. 加密
 *
 * @author hutu
 * @date 2021/5/26 4:13 下午
 */
@Slf4j
@Component
public class ResponseGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        return chain.filter(exchange.mutate()
                .response(responseDecorate(exchange))
                .build());
    }

    /**
     * 处理返回结果 此处主要处理加解密返回值
     *
     * @param exchange
     * @return
     */
    private ServerHttpResponse responseDecorate(ServerWebExchange exchange) {
        return new ServerHttpResponseDecorator(exchange.getResponse()) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                Class inClass = R.class;
                Class outClass = R.class;

                String originalResponseContentType = exchange.getAttribute(ORIGINAL_RESPONSE_CONTENT_TYPE_ATTR);
                HttpHeaders httpHeaders = new HttpHeaders();

                httpHeaders.add(HttpHeaders.CONTENT_TYPE, originalResponseContentType);

                ClientResponse clientResponse = ClientResponse.create(exchange.getResponse().getStatusCode())
                        .headers(headers -> headers.putAll(httpHeaders)).body(Flux.from(body)).build();

                Mono modifiedBody = clientResponse.bodyToMono(inClass).flatMap(originalBody -> {
                    log.debug("加密返回数据原文：{}",originalBody);
                    R newBody = (R) originalBody;
                    String dataStr = newBody.getBody().toString();
                    String s = SecureUtils.aesEncode(dataStr);
                    newBody.setBody(s);
                    return Mono.just(newBody);
                });

                BodyInserter bodyInserter = BodyInserters.fromPublisher(modifiedBody, outClass);
                CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(exchange,
                        exchange.getResponse().getHeaders());
                return bodyInserter.insert(outputMessage, new BodyInserterContext()).then(Mono.defer(() -> {
                    Flux<DataBuffer> messageBody = outputMessage.getBody();
                    HttpHeaders headers = getDelegate().getHeaders();
                    if (!headers.containsKey(HttpHeaders.TRANSFER_ENCODING)) {
                        messageBody = messageBody.doOnNext(data -> headers.setContentLength(data.readableByteCount()));
                    }
                    return getDelegate().writeWith(messageBody);
                }));
            }

            @Override
            public Mono<Void> writeAndFlushWith(Publisher<? extends Publisher<? extends DataBuffer>> body) {
                return writeWith(Flux.from(body).flatMapSequential(p -> p));
            }
        };
    }

    @Override
    public int getOrder() {
        return NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER - 10;
    }

}
