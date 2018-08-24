package com.secrething.test;

import com.secrething.rpc.proxy.JDKProxyFactory;
import com.secrething.rpc.remote.netty.NettyClient;

import java.util.concurrent.TimeUnit;

/**
 * Created by Idroton on 2018/8/17 10:58 PM.
 */
public class Consumer {
    public static void main(String[] args) throws InterruptedException {
        NettyClient client = new NettyClient("127.0.0.1",9999);

        HelloService helloService = new JDKProxyFactory().proxyInstance(HelloService.class,"hello",client);

        for (int i = 0; i < 1 ; i++) {
            helloService.hello("zhangsan"+i);
            TimeUnit.SECONDS.sleep(1);
        }
    }
}
