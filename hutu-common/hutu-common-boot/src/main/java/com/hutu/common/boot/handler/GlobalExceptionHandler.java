package com.hutu.common.boot.handler;

import com.hutu.common.core.entity.R;
import com.hutu.common.core.enums.ErrorMsgEnum;
import com.hutu.common.core.exception.GlobalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理器
 *
 * @author hutu
 * @date 2018/3/28 10:21
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(GlobalException.class)
    public R handleGlobalException(GlobalException e) {
        logger.error(e.getMessage(), e);
        return R.error(e.getCode(),e.getMessage());
    }

    /**
     * 数据绑定异常
     */
    @ExceptionHandler(BindException.class)
    public R handleBindException(BindException e) {
        logger.error(e.getMessage(), e);
        return R.error(e.getBindingResult().getFieldError().getDefaultMessage());
    }

    /**
     * 空指针异常
     */
    @ExceptionHandler(NullPointerException.class)
    public R handleNullPointerException(NullPointerException e) {
        logger.error(e.getMessage(), e);
        return R.error(ErrorMsgEnum.NULL_POINTER_EXCEPTION);
    }

    /**
     * 通用异常处理
     */
    @ExceptionHandler(Exception.class)
    public R handleException(Exception e) {
        logger.error(e.getMessage(), e);
        return R.error();
    }
}
