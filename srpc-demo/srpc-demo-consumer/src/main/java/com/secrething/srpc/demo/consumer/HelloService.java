package com.secrething.srpc.demo.consumer;

import com.secrething.rpc.annotation.RPCConsumer;
import com.secrething.srpc.demo.api.DemoHelloService;
import org.springframework.stereotype.Component;

/**
 * Created by liuzz on 2018/6/16.
 */
@Component
public class HelloService implements DemoHelloService {

    @RPCConsumer("helloService")
    private DemoHelloService helloService;

    @Override
    public String sayHello(String param) {
        return helloService.sayHello(param);
    }
}
