package com.xixi.config;

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

    private RegisterConfig registerConfig=new RegisterConfig();

    public boolean isMock() {
        return mock==true;
    }
}
