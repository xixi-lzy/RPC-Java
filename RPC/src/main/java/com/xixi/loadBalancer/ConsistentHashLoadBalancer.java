package com.xixi.loadBalancer;

import com.xixi.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;
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
    public ServiceMetaInfo select(Map<String,Object> requestParams,List<ServiceMetaInfo> services) {
        if(services.isEmpty()) return null;

        //构建虚拟节点环
        for(ServiceMetaInfo service : services) {
            for(int i = 0; i < VIRTUAL_NODE_NUM; i++) {
                int hash = getHash(service.getServiceName() + "#" + i);
                virtualNodes.put(hash, service);
            }
        }

        //获取调用的hash值
        int hash=getHash(requestParams);

        //找到第一个大于等于hash的节点
        Map.Entry<Integer, ServiceMetaInfo> entry = virtualNodes.ceilingEntry(hash);
        if(entry == null){
            //如果没有找到，从第一个节点开始
            entry = virtualNodes.firstEntry();
        }

        return entry.getValue();
    }

    private int getHash(Object key) {
        return key.hashCode();
    }
}
