package com.xixi.loadBalancer;

import com.xixi.model.ServiceMetaInfo;

import java.util.List;
import java.util.TreeMap;

public class ConsistentHashLoadBalancer implements LoadBalancer {

    /**
     * 一致性hash环，存放虚拟节点
     */
    private final TreeMap<Integer,ServiceMetaInfo> virtualNodes = new TreeMap<>();

    /**
     * 虚拟节点个数
     */
    private static final int VIRTUAL_NODE_NUM = 100;

    @Override
    public ServiceMetaInfo select(List<ServiceMetaInfo> services) {
        if(services.isEmpty()) return null;

        //构建虚拟节点环
        for(ServiceMetaInfo service : services) {
            for(int i = 0; i < VIRTUAL_NODE_NUM; i++) {
                int hash = getHash(service.getServiceName() + "#" + i);
                virtualNodes.put(hash, service);
            }
        }

        //todo 一致性hash实现
        return services.get(0);
    }

    private int getHash(Object key) {
        return key.hashCode();
    }
}
