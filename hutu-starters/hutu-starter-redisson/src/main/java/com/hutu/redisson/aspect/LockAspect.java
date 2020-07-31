package com.hutu.redisson.aspect;

import cn.hutool.core.util.StrUtil;
import com.hutu.redisson.exception.LockException;
import com.hutu.redisson.utils.SpelUtil;
import com.hutu.core.constant.StringPool;
import com.hutu.core.lock.DistributedLock;
import com.hutu.redisson.annotation.Lock;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * 分布式锁切面
 *
 * @author hutu
 * @date 2020/6/22 2:30 下午
 */
@Slf4j
@Aspect
@AllArgsConstructor
public class LockAspect {

    private DistributedLock distributedLock;


    @Around("@within(lock) || @annotation(lock)")
    public Object aroundLock(ProceedingJoinPoint point, Lock lock) throws Throwable {

        Object lockObj = null;
        if (lock == null) {
            // 获取类上的注解
            lock = point.getTarget().getClass().getDeclaredAnnotation(Lock.class);
        }
        String lockKey = lock.key();

        if (StrUtil.isEmpty(lockKey)) {
            throw new LockException("lockKey is null");
        }

        if (lockKey.contains(StringPool.HASH)) {
            MethodSignature methodSignature = (MethodSignature) point.getSignature();
            //获取方法参数值
            Object[] args = point.getArgs();
            lockKey = SpelUtil.getValBySpEL(lockKey, methodSignature, args);
        }
        log.info("lockKey: {}", lockKey);
        try {
            //加锁
            if (lock.waitTime() > 0) {

                lockObj = distributedLock.tryLock(lockKey, lock.waitTime(), lock.leaseTime(), lock.unit(), lock.isFair());
            } else {
                lockObj = distributedLock.lock(lockKey, lock.leaseTime(), lock.unit(), lock.isFair());
            }

            if (lockObj != null) {
                return point.proceed();
            } else {
                throw new LockException("锁等待超时");
            }
        } finally {
            distributedLock.unlock(lockObj);
        }
    }


}
