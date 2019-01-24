package com.hutu.common.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池初始化
 * @author hutu
 * @date 2018/8/2 14:23
 */
public class ThreadPoolExecutorTemplate {

    private ThreadPoolExecutor pool = null;


    public ThreadPoolExecutorTemplate() {
        init();
    }

    /**
     * 线程池初始化方法
     * <p>
     * corePoolSize 核心线程池大小----10
     * maximumPoolSize 最大线程池大小----30
     * keepAliveTime 线程池中超过corePoolSize数目的空闲线程最大存活时间----30+单位TimeUnit
     * TimeUnit keepAliveTime时间单位----TimeUnit.MINUTES
     * workQueue 阻塞队列----new ArrayBlockingQueue<Runnable>(10)====10容量的阻塞队列
     * threadFactory 新建线程工厂----new CustomThreadFactory()====定制的线程工厂
     * rejectedExecutionHandler 当提交任务数超过maxmumPoolSize+workQueue之和时,
     * 即当提交第41个任务时(前面线程都没有执行完,此测试方法中用sleep(100)),
     * 任务会交给RejectedExecutionHandler来处理
     */

    private final static int CORE_POOL_SIZE = 10;
    private final static int MAXIMUM_POOL_SIZE = 30;
    private final static long KEEP_ALIVE_TIME = 30;
    private final static int QUEUE_SIZE = 10;
    public void init() {
        pool = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAXIMUM_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.MINUTES,
                new ArrayBlockingQueue<>(QUEUE_SIZE),
                new CustomThreadFactory(),
                new CustomRejectedExecutionHandler());
    }


    public void destory() {
        if(pool != null) {
            pool.shutdownNow();
        }
    }


    public ExecutorService getThreadPoolExecutor() {
        return this.pool;
    }

//    public static void main(String[] args) {
//        ExecutorService threadPoolExecutor = new ThreadPoolExecutorTemplate().getThreadPoolExecutor();
//        for (int i = 0; i < 100; i++) {
//            threadPoolExecutor.execute(new TestRunnable());
//        }
//
//    }
}
