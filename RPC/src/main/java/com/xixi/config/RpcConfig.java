package com.xixi.config;

import cn.hutool.core.annotation.Alias;
import com.xixi.fault.retry.RetryStrategyKeys;
import com.xixi.fault.tolerant.TolerantStrategyKeys;
import com.xixi.loadBalancer.LoadBalancerKeys;
import com.xixi.serializer.SerializerKeys;
import lombok.Data;

@Data
public class RpcConfig {

    private boolean mock=false;

    private String name="RPC-Java";

    private String version="1.0";

    private String serverHost="localhost";

    private Integer serverPort=8080;

    private String serializer= SerializerKeys.JDK;

    private String loadBalancer= LoadBalancerKeys.ROUNDROBIN;

    private String retryStrategy= RetryStrategyKeys.NO;

    private String tolerantStrategy= TolerantStrategyKeys.FAIL_FAST;

    //Alias注解是为了在配置文件中使用别名 将registerConfig 映射到 register
    @Alias("register")
    private RegisterConfig registerConfig=new RegisterConfig();

    public boolean isMock() {
        return mock==true;
    }
}
