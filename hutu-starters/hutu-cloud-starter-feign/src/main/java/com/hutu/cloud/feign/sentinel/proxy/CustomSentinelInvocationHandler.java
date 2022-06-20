package com.hutu.cloud.feign.sentinel.proxy;

import com.alibaba.cloud.sentinel.feign.SentinelContractHolder;
import com.alibaba.cloud.sentinel.feign.SentinelInvocationHandler;
import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.context.ContextUtil;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.hutu.cloud.core.constant.StringPool;
import com.hutu.cloud.core.utils.AnnotationUtils;
import com.hutu.cloud.core.utils.JsonUtil;
import com.hutu.cloud.feign.annotation.CustomFeignClient;
import com.hutu.cloud.feign.enums.RpcType;
import com.hutu.cloud.feign.properties.FeignProperties;
import feign.Feign;
import feign.InvocationHandlerFactory;
import feign.MethodMetadata;
import feign.Target;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.LinkedHashMap;
import java.util.Map;

import static feign.Util.checkNotNull;

/**
 * 支持自动降级注入 重写 {@link com.alibaba.cloud.sentinel.feign.SentinelInvocationHandler}
 */
@Slf4j
public class CustomSentinelInvocationHandler implements InvocationHandler {

    public static final String EQUALS = "equals";

    public static final String HASH_CODE = "hashCode";

    public static final String TO_STRING = "toString";

    private final Target<?> target;

    private final Map<Method, InvocationHandlerFactory.MethodHandler> dispatch;

    private FallbackFactory fallbackFactory;

    private Map<Method, Method> fallbackMethodMap;

    private FeignProperties feignProperties;

    CustomSentinelInvocationHandler(Target<?> target, Map<Method, InvocationHandlerFactory.MethodHandler> dispatch,
                                    FallbackFactory fallbackFactory, FeignProperties feignProperties) {
        this.target = checkNotNull(target, "target");
        this.dispatch = checkNotNull(dispatch, "dispatch");
        this.fallbackFactory = fallbackFactory;
        this.fallbackMethodMap = toFallbackMethod(dispatch);
        this.feignProperties = feignProperties;
    }

    CustomSentinelInvocationHandler(Target<?> target, Map<Method, InvocationHandlerFactory.MethodHandler> dispatch) {
        this.target = checkNotNull(target, "target");
        this.dispatch = checkNotNull(dispatch, "dispatch");
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        if (EQUALS.equals(method.getName())) {
            try {
                Object otherHandler = args.length > 0 && args[0] != null ? Proxy.getInvocationHandler(args[0]) : null;
                return equals(otherHandler);
            } catch (IllegalArgumentException e) {
                return false;
            }
        } else if (HASH_CODE.equals(method.getName())) {
            return hashCode();
        } else if (TO_STRING.equals(method.getName())) {
            return toString();
        }

        Object result;
        InvocationHandlerFactory.MethodHandler methodHandler = this.dispatch.get(method);
        // only handle by HardCodedTarget
        if (target instanceof Target.HardCodedTarget) {
            Target.HardCodedTarget hardCodedTarget = (Target.HardCodedTarget) target;
            MethodMetadata methodMetadata = SentinelContractHolder.METADATA_MAP
                    .get(hardCodedTarget.type().getName() + Feign.configKey(hardCodedTarget.type(), method));
            CustomFeignClient annotation = AnnotationUtils.getAnnotation(target.type(), CustomFeignClient.class);
            RpcType rpcType = feignProperties.getRpcType() != null ? feignProperties.getRpcType()
                    : annotation.rpcType();
            String resourceName;
            Entry entry = null;

            try {
                // --------- 执行前初始化 sentinel
                resourceName = methodMetadata.template().method().toUpperCase() + StringPool.COLON
                        + hardCodedTarget.url() + methodMetadata.template().path();
                ContextUtil.enter(resourceName);
                entry = SphU.entry(resourceName, EntryType.OUT, 1, args);
                // --------- 执行前初始化 sentinel

                if (annotation != null) {
                    Object[] objects = new Object[]{JsonUtil.toJson(args)};
                    result = methodHandler.invoke(objects);
                } else {
                    result = methodHandler.invoke(args);
                }

            } catch (Throwable ex) {
                // fallback handle
                if (!BlockException.isBlockException(ex)) {
                    Tracer.trace(ex);
                }
                if (fallbackFactory != null) {
                    try {
                        Object fallbackResult = fallbackMethodMap.get(method).invoke(fallbackFactory.create(ex), args);
                        return fallbackResult;
                    } catch (IllegalAccessException e) {
                        // shouldn't happen as method is public due to being an
                        // interface
                        throw new AssertionError(e);
                    } catch (InvocationTargetException e) {
                        throw new AssertionError(e.getCause());
                    }
                } else {
                    throw ex;
                }
            } finally {
                if (entry != null) {
                    entry.exit(1, args);
                }
                ContextUtil.exit();
            }

        } else {
            // other target type using default strategy
            result = methodHandler.invoke(args);
        }

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SentinelInvocationHandler) {
            CustomSentinelInvocationHandler other = (CustomSentinelInvocationHandler) obj;
            return target.equals(other.target);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return target.hashCode();
    }

    @Override
    public String toString() {
        return target.toString();
    }

    static Map<Method, Method> toFallbackMethod(Map<Method, InvocationHandlerFactory.MethodHandler> dispatch) {
        Map<Method, Method> result = new LinkedHashMap<>();
        for (Method method : dispatch.keySet()) {
            method.setAccessible(true);
            result.put(method, method);
        }
        return result;
    }

}
