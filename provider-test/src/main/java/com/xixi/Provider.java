package com.xixi;

import com.xixi.register.LocalRegister;
import com.xixi.server.HttpServer;
import com.xixi.server.VertxHttpServer;
import com.xixi.service.UserService;

public class Provider {

    public static void main(String[] args) {

        LocalRegister.register(UserService.class.getName(), UserServiceImpl.class);

        HttpServer httpServer=new VertxHttpServer();
        httpServer.start(8080);
    }
}
