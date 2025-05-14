package com.xixi.serializer;

import com.xixi.constant.RpcConstant;
import com.xixi.spi.SpiLoader;

import java.util.HashMap;
import java.util.Map;

// 序列化器工厂
public class SerializerFactory {

    static{
        SpiLoader.load(Serializer.class);
    }

    private static final Serializer DEFAULT_SERIALIZER = new JdkSerializer();

    public static Serializer getInstance(String key) {
        return SpiLoader.getInstance(Serializer.class, key);
    }
}
