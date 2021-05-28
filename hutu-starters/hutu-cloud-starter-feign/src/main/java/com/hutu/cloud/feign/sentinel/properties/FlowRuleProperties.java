package com.hutu.cloud.feign.sentinel.properties;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import lombok.Getter;
import lombok.Setter;

/**
 * 默认限流策略配置，结构参考 {@link com.alibaba.csp.sentinel.slots.block.flow.FlowRule}
 *
 * @author hutu
 * @date 2021/3/26 3:00 下午
 */
@Setter
@Getter
public class FlowRuleProperties {

	private boolean enable = false;

	/**
	 * The threshold type of flow control (0: thread count, 1: QPS).
	 */
	private int grade = RuleConstant.FLOW_GRADE_QPS;

	/**
	 * Flow control threshold count.
	 */
	private double count;

	/**
	 * Flow control strategy based on invocation chain.
	 *
	 * {@link RuleConstant#STRATEGY_DIRECT} for direct flow control (by origin);
	 * {@link RuleConstant#STRATEGY_RELATE} for relevant flow control (with relevant
	 * resource); {@link RuleConstant#STRATEGY_CHAIN} for chain flow control (by entrance
	 * resource).
	 */
	private int strategy = RuleConstant.STRATEGY_DIRECT;

	/**
	 * Reference resource in flow control with relevant resource or context.
	 */
	private String refResource;

	/**
	 * Rate limiter control behavior. 0. default(reject directly), 1. warm up, 2. rate
	 * limiter, 3. warm up + rate limiter
	 */
	private int controlBehavior = RuleConstant.CONTROL_BEHAVIOR_DEFAULT;

	private int warmUpPeriodSec = 10;

	/**
	 * Max queueing time in rate limiter behavior.
	 */
	private int maxQueueingTimeMs = 500;

	private boolean clusterMode;

}
