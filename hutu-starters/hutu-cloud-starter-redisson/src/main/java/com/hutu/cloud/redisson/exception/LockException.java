package com.hutu.cloud.redisson.exception;

/**
 * 分布式锁异常
 *
 * @author hutu
 * @date 2020/6/22 2:35 下午
 */
public class LockException extends RuntimeException {

	private static final long serialVersionUID = 6610083281801529147L;

	public LockException(String message) {
		super(message);
	}

}
