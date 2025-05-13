package com.xixi.serializer;

import com.xixi.constant.RpcConstant;

import java.util.HashMap;
import java.util.Map;

// 序列化器工厂
public class SerializerFactory {

    //序列化映射
    private static final Map<String, Serializer> KEY_SEARIALIZER_MAP = new HashMap<String, Serializer>(){
        {
            put(SerializerKeys.JDK, new JdkSerializer());
            put(SerializerKeys.JSON, new JsonSerializer());
            put(SerializerKeys.HESSIAN, new HessianSerializer());
            put(SerializerKeys.KRYO, new KryoSerializer());
        }
    };

    //默认序列化器
    private static final Serializer DEFAULT_SERIALIZER = KEY_SEARIALIZER_MAP.get(RpcConstant.DEFAULT_SERIALIZER);

    public static Serializer getInstance(String key) {
        return KEY_SEARIALIZER_MAP.getOrDefault(key,DEFAULT_SERIALIZER);
    }
}
