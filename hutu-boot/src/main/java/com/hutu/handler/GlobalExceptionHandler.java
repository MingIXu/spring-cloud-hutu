package com.hutu.handler;

import com.hutu.core.R;
import com.hutu.core.exception.GlobalException;
import com.hutu.core.enums.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理器
 * @author hutu
 * @date 2018/3/28 10:21
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * 处理自定义异常
	 */
	@ExceptionHandler(GlobalException.class)
	public R handleGlobalException(GlobalException e){
		log.error(e.getMessage(), e);
		return R.error(e.getCode(),e.getMessage());
	}
	/**
	 * 数据绑定异常
	 */
	@ExceptionHandler(BindException.class)
	public R handleBindException(BindException e){
		log.error(e.getMessage(), e);
		return R.error(e.getBindingResult().getFieldError().getDefaultMessage());
	}

	/**
	 * 空指针异常
	 */
	@ExceptionHandler(NullPointerException.class)
	public R handleNullPointerException(NullPointerException e){
		log.error(e.getMessage(), e);
		return R.error(ResultCode.INTERNAL_SERVER_ERROR);
	}

	/**
	 * 通用异常处理
	 */
	@ExceptionHandler(Exception.class)
	public R handleException(Exception e){
		log.error(e.getMessage(), e);
		return R.error();
	}
}
