package com.xixi;

import com.xixi.model.User;
import com.xixi.service.UserService;

public class UserServiceImpl implements UserService {

    public User getUser(User user) {
        System.out.println("用户名:"+user.getName());
        return user;
    }
}
