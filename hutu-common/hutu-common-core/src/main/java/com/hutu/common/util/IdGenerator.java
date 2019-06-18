package com.hutu.common.util;

import com.hutu.common.util.sequence.Sequence;

/**
 * 高效分布式ID生成算法(sequence),基于Snowflake算法优化实现64位自增ID算法。
 * 其中解决时间回拨问题的优化方案如下：
 * 1. 如果发现当前时间少于上次生成id的时间(时间回拨)，着计算回拨的时间差
 * 2. 如果时间差(offset)小于等于5ms，着等待 offset * 2 的时间再生成
 * 3. 如果offset大于5，则直接抛出异常
 *
 * @author zlt
 * @date 2019/3/5
 */
public class IdGenerator {
    private static Sequence WORKER = new Sequence(1L);

    public static long getId() {
        return WORKER.nextId();
    }

    public static String getIdStr() {
        return String.valueOf(WORKER.nextId());
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(getId());
        }
    }
}
