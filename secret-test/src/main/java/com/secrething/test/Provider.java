package com.secrething.test;

import com.secrething.rpc.registry.ServiceLocalStorage;
import com.secrething.rpc.remote.netty.SecretRpcServer;

/**
 * Created by Idroton on 2018/8/17 10:55 PM.
 */
public class Provider {
    public static void main(String[] args) {
        ServiceLocalStorage.cacheService(HelloService.class,new HelloServiceImpl());

        SecretRpcServer server = new SecretRpcServer();
        server.start();

    }
}
