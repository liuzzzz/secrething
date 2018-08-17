package com.secrething.test;

import com.secrething.rpc.registry.ServiceStorage;
import com.secrething.rpc.remote.SecretRpcServer;

/**
 * Created by Idroton on 2018/8/17 10:55 PM.
 */
public class Provider {
    public static void main(String[] args) {
        ServiceStorage.cacheService("hello",new HelloServiceImpl());

        SecretRpcServer server = new SecretRpcServer();
        server.start();

    }
}
