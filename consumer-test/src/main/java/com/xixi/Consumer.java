package com.xixi;

import com.xixi.config.RpcConfig;
import com.xixi.model.User;
import com.xixi.proxy.ServiceProxyFactory;
import com.xixi.service.UserService;

public class Consumer {

    public static void main(String[] args) {
//        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
//        User user = new User();
//        user.setName("xixi");
//        // 调用
//        User newUser = userService.getUser(user);
//        if (newUser != null) {
//            System.out.println(newUser.getName());
//        } else {
//            System.out.println("user == null");
//        }
        RpcConfig rpcConfig=RpcApplication.getRpcConfig();
        System.out.println(rpcConfig);
    }
}
