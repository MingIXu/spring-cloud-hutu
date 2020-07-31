package com.hutu.core.threadpool;

import cn.hutool.core.util.StrUtil;

import javax.validation.constraints.NotNull;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程工厂
 *
 * @author hutu
 * @date 2018/8/2 16:05
 */
public class CustomThreadFactory implements ThreadFactory {

    /**
     * 原子操作保证每个线程都有唯一的
     */
    private static final AtomicInteger threadNumber = new AtomicInteger(1);

    private final AtomicInteger mThreadNum = new AtomicInteger(1);

    private final String prefix;
    /**
     * 是否是守护进程
     */
    private final boolean daemonThread;

    private final ThreadGroup threadGroup;

    public CustomThreadFactory() {
        this("threadpool-" + threadNumber.getAndIncrement(), false);
    }

    public CustomThreadFactory(String prefix) {
        this(prefix, false);
    }


    public CustomThreadFactory(String prefix, boolean isDaemon) {
        this.prefix = StrUtil.isNotEmpty(prefix) ? prefix + "-thread-" : "";
        daemonThread = isDaemon;
        SecurityManager s = System.getSecurityManager();
        threadGroup = (s == null) ? Thread.currentThread().getThreadGroup() : s.getThreadGroup();
    }

    @Override
    public Thread newThread(@NotNull Runnable runnable) {
        String name = prefix + mThreadNum.getAndIncrement();
        Thread ret = new Thread(threadGroup, runnable, name, 0);
        ret.setDaemon(daemonThread);
        return ret;
    }
}
