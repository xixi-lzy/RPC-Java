package com.xixi.register;

import java.util.concurrent.ConcurrentHashMap;

public class LocalRegister {

    //本地注册服务
    private static final ConcurrentHashMap<String,Class<?>> map=new ConcurrentHashMap<>();

    public static void register(String serviceName,Class<?> clazz){
        map.put(serviceName,clazz);
    }

    public static Class<?> get(String serviceName){
        return map.get(serviceName);
    }

    public static void remove(String serviceName){
        map.remove(serviceName);
    }
}
