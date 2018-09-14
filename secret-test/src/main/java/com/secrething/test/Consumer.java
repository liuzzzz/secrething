package com.secrething.test;

import com.secrething.common.util.console;
import com.secrething.rpc.proxy.JDKProxyFactory;
import com.secrething.rpc.remote.netty.NettyClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by Idroton on 2018/8/17 10:58 PM.
 */
public class Consumer {
    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);
    public static void main(String[] args) throws InterruptedException {
        NettyClient client = new NettyClient("127.0.0.1",9999);

        HelloService helloService = new JDKProxyFactory().proxyInstance(HelloService.class,"hello",client);

        for (int i = 0; i < 5 ; i++) {
            String s = helloService.hello("zhangsan"+i);
            console.log(s);
            TimeUnit.SECONDS.sleep(1);
        }
    }
}
