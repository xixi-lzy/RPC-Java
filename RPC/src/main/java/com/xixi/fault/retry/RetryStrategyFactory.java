package com.xixi.fault.retry;

import com.xixi.spi.SpiLoader;

public class RetryStrategyFactory {

    static{
        SpiLoader.load(RetryStrategy.class);
    }

    public static RetryStrategy DEFAULT_RETRY_STRATEGY = new NoRetryStrategy();

    public static RetryStrategy getInstance(String key){
        return SpiLoader.getInstance(RetryStrategy.class,key);
    }
}
