package com.hutu.common.threadpool;

/**
 * 线程任务测试
 * @author hutu
 * @date 2018/8/2 16:05
 */
public class TestRunnable implements Runnable {
    @Override
    public void run() {
        try {
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getName() + " run");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
