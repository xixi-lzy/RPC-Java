package com.xixi.loadBalancer;

import com.xixi.spi.SpiLoader;

public class LoadBalancerFactory {

    static{
        SpiLoader.load(LoadBalancer.class);
    }

    private static LoadBalancer DEFAULT_LOAD_BALANCER = new RoundRobinLoadBalancer();

    public static LoadBalancer getInstance(String loadBalancer){
        LoadBalancer loadBalancerInstance = SpiLoader.getInstance(LoadBalancer.class, loadBalancer);
        return loadBalancerInstance == null ? DEFAULT_LOAD_BALANCER : loadBalancerInstance;
    }
}
