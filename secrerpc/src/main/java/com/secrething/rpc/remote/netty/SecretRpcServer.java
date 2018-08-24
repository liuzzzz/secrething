package com.secrething.rpc.remote.netty;

import com.secrething.rpc.protocol.ProcessService;

/**
 * Created by Idroton on 2018/8/17 10:51 PM.
 */
public class SecretRpcServer extends NettyServer {
    @Override
    public ProcessService getProcessService() {
        return new ServerProcessService();
    }
}
