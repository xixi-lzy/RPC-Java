package com.xixi.loadBalancer;

/**
 * 负载均衡器键名常量
 */
public interface LoadBalancerKeys {

    String ROUNDROBIN = "RoundRobin";

    String RANDOM = "Random";

    String CONSISTENTHASH = "ConsistentHash";
}
