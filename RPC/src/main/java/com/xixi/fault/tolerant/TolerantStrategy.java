package com.xixi.fault.tolerant;

import com.xixi.model.RpcResponse;

import java.util.Map;
import java.util.concurrent.Callable;

public interface TolerantStrategy {

    /**
     * 容错
     *
     * @param context 上下文，用于传递数据
     * @return
     */
    RpcResponse doTolerant(Map<String,Object> context,Exception e);
}
