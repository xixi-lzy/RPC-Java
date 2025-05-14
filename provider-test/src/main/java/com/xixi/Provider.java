package com.xixi;

import com.xixi.config.RegisterConfig;
import com.xixi.config.RpcConfig;
import com.xixi.model.ServiceMetaInfo;
import com.xixi.register.LocalRegister;
import com.xixi.register.Register;
import com.xixi.register.RegisterFactory;
import com.xixi.server.HttpServer;
import com.xixi.server.VertxHttpServer;
import com.xixi.service.UserService;

import java.rmi.registry.Registry;

/**
 * 服务提供者示例
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @learn <a href="https://codefather.cn">编程宝典</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
public class Provider {

    public static void main(String[] args) {
        // RPC 框架初始化
        RpcApplication.init();

        // 注册服务
        String serviceName = UserService.class.getName();
        LocalRegister.register(serviceName, UserServiceImpl.class);

        // 注册服务到注册中心
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        RegisterConfig registryConfig = rpcConfig.getRegisterConfig();
        Register registry = RegisterFactory.getInstance(registryConfig.getRegistry());
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName(serviceName);
        serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
        serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
        try {
            registry.register(serviceMetaInfo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // 启动 web 服务
        HttpServer httpServer = new VertxHttpServer();
        httpServer.start(RpcApplication.getRpcConfig().getServerPort());
    }
}
