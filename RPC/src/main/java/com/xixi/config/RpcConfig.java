package com.xixi.config;

import lombok.Data;

@Data
public class RpcConfig {

    private boolean mock=false;

    private String name="RPC-Java";

    private String version="1.0";

    private String serverHost="localhost";

    private Integer serverPort=8080;

    public boolean isMock() {
        return mock==true;
    }
}
