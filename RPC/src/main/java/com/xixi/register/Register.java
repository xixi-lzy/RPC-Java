package com.xixi.register;

import com.xixi.config.RegisterConfig;
import com.xixi.model.ServiceMetaInfo;

import java.util.List;

/**
 * 注册中心
 */
public interface Register {

    /**
     * 初始化
     *
     * @param registryConfig
     */
    void init(RegisterConfig registryConfig);

    /**
     * 注册服务（服务端）
     *
     * @param serviceMetaInfo
     */
    void register(ServiceMetaInfo serviceMetaInfo) throws Exception;

    /**
     * 注销服务（服务端）
     *
     * @param serviceMetaInfo
     */
    void unRegister(ServiceMetaInfo serviceMetaInfo);

    /**
     * 服务发现（获取某服务的所有节点，消费端）
     *
     * @param serviceKey 服务键名
     * @return
     */
    List<ServiceMetaInfo> serviceDiscovery(String serviceKey);

    /**
     * 服务销毁
     */
    void destroy();

    /**
     * 心跳检测
     */
    void heartbeat();

    /**
     * 服务监测（消费端）
     */
    void watch(String serviceNodeKey);
}
