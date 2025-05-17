package com.xixi.processor;

import com.xixi.RpcApplication;
import com.xixi.annotation.RpcService;
import com.xixi.config.RegisterConfig;
import com.xixi.config.RpcConfig;
import com.xixi.model.ServiceMetaInfo;
import com.xixi.register.LocalRegister;
import com.xixi.register.Register;
import com.xixi.register.RegisterFactory;
import org.reflections.Reflections;

import java.util.Set;

/**
 * 服务注解处理器
 */
public class ServiceAnnotationProcessor {

    /**
     * 处理服务注解
     * @param basePackage 基础包路径
     * @return
     */
    public static void process(String basePackage) {
        Reflections reflections = new Reflections(basePackage);
        Set<Class<?>> services = reflections.getTypesAnnotatedWith(RpcService.class);

        for (Class<?> serviceClass : services) {
            RpcService annotation = serviceClass.getAnnotation(RpcService.class);
            Class<?> interfaceClass = annotation.interfaceClass();
            // 注册本地服务
            LocalRegister.register(interfaceClass.getName(), serviceClass);

            // 注册到注册中心
            RpcConfig rpcConfig = RpcApplication.getRpcConfig();
            RegisterConfig registryConfig = rpcConfig.getRegisterConfig();
            Register registry = RegisterFactory.getInstance(registryConfig.getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(interfaceClass.getName());
            serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
            serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
            try {
                registry.register(serviceMetaInfo);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}