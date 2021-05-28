package com.hutu.cloud.feign.sentinel.properties;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import lombok.Getter;
import lombok.Setter;

/**
 * 默认熔断策略配置 结构参考 {@link com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule}
 *
 * @author hutu
 * @date 2021/3/26 3:00 下午
 */
@Setter
@Getter
public class DegradeRuleProperties {

	private boolean enable = false;

	/**
	 * Circuit breaking strategy (0: average RT, 1: exception ratio, 2: exception count).
	 */
	private int grade = RuleConstant.DEGRADE_GRADE_RT;

	/**
	 * Threshold count.
	 */
	private double count = 1000d;

	/**
	 * Recovery timeout (in seconds) when circuit breaker opens. After the timeout, the
	 * circuit breaker will transform to half-open state for trying a few requests.
	 */
	private int timeWindow = 10 * 1000;

	/**
	 * Minimum number of requests (in an active statistic time span) that can trigger
	 * circuit breaking.
	 *
	 * @since 1.7.0
	 */
	private int minRequestAmount = RuleConstant.DEGRADE_DEFAULT_MIN_REQUEST_AMOUNT;

	/**
	 * The threshold of slow request ratio in RT mode.
	 */
	private double slowRatioThreshold = 0.2d;

	private int statIntervalMs = 2 * 1000;

}
