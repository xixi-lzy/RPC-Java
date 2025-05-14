package com.xixi.register;

import com.xixi.spi.SpiLoader;

public class RegisterFactory {

    static{
        SpiLoader.load(Register.class);
    }

    private static final Register DEFAULT_REGISTER = new EtcdRegister();

    public static Register getInstance(String key){
        return SpiLoader.getInstance(Register.class,key);
    }
}
