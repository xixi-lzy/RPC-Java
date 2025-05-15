package com.xixi.loadBalancer;

import com.xixi.model.ServiceMetaInfo;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 随机轮询负载均衡器
 */
public class RoundRobinLoadBalancer implements LoadBalancer {

    private final AtomicInteger index = new AtomicInteger(0);

    @Override
    public ServiceMetaInfo select(List<ServiceMetaInfo> services) {
        if(services.isEmpty()){
            return null;
        }
        int size = services.size();
        if(size==1){
            return services.get(0);
        }
        return services.get(index.getAndIncrement() % size);
    }
}
