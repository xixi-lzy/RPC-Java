package com.xixi.bootstrap;

import com.xixi.RpcApplication;
import com.xixi.config.RpcConfig;
import com.xixi.processor.ServiceAnnotationProcessor;
import com.xixi.server.tcp.VertxTcpServer;

/**
 * 服务提供者初始化
 */
public class ProviderBootstrap {

    /**
     * 初始化
     */
    public static void init() {
        // RPC 框架初始化（配置和注册中心初始化）
        RpcApplication.init();

        // 获取全局配置
        final RpcConfig rpcConfig = RpcApplication.getRpcConfig();

        //服务注册
        ServiceAnnotationProcessor.process(rpcConfig.getServicePath());

        // 启动服务器
        VertxTcpServer vertxTcpServer = new VertxTcpServer();
        vertxTcpServer.start(rpcConfig.getServerPort());

    }
}
