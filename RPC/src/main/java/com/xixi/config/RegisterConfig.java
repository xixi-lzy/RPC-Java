package com.xixi.config;

import lombok.Data;

/**
 * RPC 框架注册中心配置
 */
@Data
public class RegisterConfig {

    /**
     * 注册中心类别
     */
    private String registry = "etcd";

    /**
     * 注册中心地址
     */
    private String address = "http://localhost:2379";

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 超时时间（单位秒）
     */
    private Long timeout = 30L;
}

