package com.secrething.srpc.demo.provider;

import com.secrething.common.util.MesgFormatter;
import com.secrething.rpc.annotation.RPCProvidor;
import com.secrething.srpc.demo.api.DemoHelloService;

/**
 * Created by liuzengzeng on 2018/6/16.
 */
@RPCProvidor("helloService")
public class DemoHelloServiceImpl implements DemoHelloService {
    @Override
    public String sayHello(String client) {
        return MesgFormatter.format("{}:{}, hello","provider",client);
    }

    @Override
    public String sayBye(String param) {
        return MesgFormatter.format("{}:{}, bye!","provider",param);
    }
}
