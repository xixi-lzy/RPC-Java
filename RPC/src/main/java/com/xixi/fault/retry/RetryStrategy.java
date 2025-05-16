package com.xixi.fault.retry;

import com.xixi.model.RpcResponse;

import java.util.concurrent.Callable;

/**
 * 重试策略接口
 */
public interface RetryStrategy {

    RpcResponse doRetry(Callable<RpcResponse> callable) throws Exception;
}
