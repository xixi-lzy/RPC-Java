package com.xixi.fault.tolerant;

/**
 * 容错策略
 */
public interface TolerantStrategyKeys {

    /**
     * 快速失败
     */
    String FAIL_FAST = "failFast";

    /**
     * 静默处理
     */
    String FAIL_SAFE = "failSafe";

    /**
     * 转移到其他服务节点
     */
    String FAIL_OVER = "failOver";

    /**
     * 降级到其他服务
     */
    String FAIL_BACK = "failBack";
}
