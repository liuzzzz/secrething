package com.secrething.service;

import com.secrething.remote.HelloService;
import com.secrething.rpc.registry.ServiceLocalStorage;
import com.secrething.rpc.remote.netty.SecretRpcServer;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Idroton on 2018/8/17 10:55 PM.
 */
public class Provider {
    public static void main(String[] args) {
        ServiceLocalStorage.cacheService(HelloService.class,new HelloServiceImpl());

        SecretRpcServer server = new SecretRpcServer();
        server.start();

        try {
            System.out.println(InetAddress.getLocalHost());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }
}
