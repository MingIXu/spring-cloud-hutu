package com.hutu.common.threadpool;

import com.hutu.common.exception.GlobalException;
import com.hutu.common.enums.ErrorMsgEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池拒绝策略
 * @author hutu
 * @date 2018/8/2 15:32
 */
public class CustomRejectedExecutionHandler implements RejectedExecutionHandler {
    private static final Logger logger = LoggerFactory.getLogger(CustomRejectedExecutionHandler.class);
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

        // 记录异常
        // 报警处理等
        logger.info("ThreadPool overflow");
        throw new GlobalException(ErrorMsgEnum.THREAD_POOL_OVERFLOW);
    }
}
