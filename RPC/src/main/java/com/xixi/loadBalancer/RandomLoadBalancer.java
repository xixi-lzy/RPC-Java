package com.xixi.loadBalancer;

import com.xixi.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class RandomLoadBalancer implements LoadBalancer{

    private static final Random random = new Random();

    @Override
    public ServiceMetaInfo select(Map<String,Object> requestParams,List<ServiceMetaInfo> services) {
        int size = services.size();
        if(size == 0){
            return null;
        }
        if(size == 1){
            return services.get(0);
        }
        int index = random.nextInt(size);
        return services.get(index);
    }
}
