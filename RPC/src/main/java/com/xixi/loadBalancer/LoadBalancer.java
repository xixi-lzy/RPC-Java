package com.xixi.loadBalancer;

import com.xixi.model.ServiceMetaInfo;

import java.util.List;

/**
 * 负载均衡器
 */
public interface LoadBalancer {

    /**
     * 选择服务
     *
     * @param services
     * @return
     */
    ServiceMetaInfo select(List<ServiceMetaInfo> services);
}
