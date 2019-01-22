package com.hutu.hutugateway.ratelimiter;

import io.github.bucket4j.*;
import org.springframework.cloud.gateway.filter.ratelimit.AbstractRateLimiter;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import javax.validation.constraints.Min;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 令牌法限流实现
 *
 * @author hutu
 * @date 2019/1/19
 */
public class InMemoryRateLimiter extends AbstractRateLimiter<InMemoryRateLimiter.Config> {


    public static final String CONFIGURATION_PROPERTY_NAME = "in-memory-rate-limiter";

    private InMemoryRateLimiter.Config defaultConfig;

    private final Map<String, Bucket> ipBucketMap = new ConcurrentHashMap<>();

    public InMemoryRateLimiter() {
        super(InMemoryRateLimiter.Config.class, CONFIGURATION_PROPERTY_NAME, null);
    }

    public InMemoryRateLimiter(int defaultReplenishRate, int defaultBurstCapacity) {
        super(Config.class, CONFIGURATION_PROPERTY_NAME, null);
        this.defaultConfig = new InMemoryRateLimiter.Config()
                .setReplenishRate(defaultReplenishRate)
                .setBurstCapacity(defaultBurstCapacity);
    }

    @Override
    public Mono<Response> isAllowed(String routeId, String id) {
        InMemoryRateLimiter.Config routeConfig = getConfig().get(routeId);

        if (routeConfig == null) {
            if (defaultConfig == null) {
                throw new IllegalArgumentException("No Configuration found for route " + routeId);
            }
            routeConfig = defaultConfig;
        }

        // How many requests per second do you want a user to be allowed to do?
        int replenishRate = routeConfig.getReplenishRate();

        // How much bursting do you want to allow?
        int burstCapacity = routeConfig.getBurstCapacity();

        Bucket bucket = ipBucketMap.computeIfAbsent(id, k -> {
            Refill refill = Refill.greedy(replenishRate, Duration.ofSeconds(1));
            Bandwidth limit = Bandwidth.classic(burstCapacity, refill);
            return Bucket4j.builder().addLimit(limit).build();
        });

        // tryConsume returns false immediately if no tokens available with the bucket
        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);
        if (probe.isConsumed()) {
            // the limit is not exceeded
            return Mono.just(new Response(true, probe.getRemainingTokens()));
        } else {
            // limit is exceeded
            return Mono.just(new Response(false,-1));
        }
    }
    @Validated
    public static class Config {
        // 允许用户每秒做多少次请求
        @Min(1)
        private int replenishRate;
        // 令牌桶的容量，允许在一秒钟内完成的最大请求数
        @Min(0)
        private int burstCapacity = 0;

        public int getReplenishRate() {
            return replenishRate;
        }

        public InMemoryRateLimiter.Config setReplenishRate(int replenishRate) {
            this.replenishRate = replenishRate;
            return this;
        }

        public int getBurstCapacity() {
            return burstCapacity;
        }

        public InMemoryRateLimiter.Config setBurstCapacity(int burstCapacity) {
            this.burstCapacity = burstCapacity;
            return this;
        }

        @Override
        public String toString() {
            return "Config{" +
                    "replenishRate=" + replenishRate +
                    ", burstCapacity=" + burstCapacity +
                    '}';
        }
    }
}
