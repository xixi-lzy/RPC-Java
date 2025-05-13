package com.xixi.service;

import com.xixi.model.User;

public interface UserService {

    public User getUser(User user);

    default short getNumber(){
        return 1;
    }
}
