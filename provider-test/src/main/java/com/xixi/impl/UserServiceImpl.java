package com.xixi.impl;

import com.xixi.annotation.RpcService;
import com.xixi.model.User;
import com.xixi.service.UserService;

@RpcService(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {

    public User getUser(User user) {
        System.out.println("用户名:"+user.getName());
        return user;
    }
}
