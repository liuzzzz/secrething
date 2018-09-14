package com.secrething.rpc.remote.netty;

import com.secrething.rpc.core.RemoteFuture;
import com.secrething.rpc.core.RemoteResponse;
import com.secrething.rpc.core.ProcessService;
import io.netty.channel.Channel;

/**
 * Created by Idroton on 2018/8/12.
 *
 */
public class ClientProcessService implements ProcessService<RemoteResponse> {
    @Override
    public RemoteFuture process(Channel channel, RemoteResponse inputMsg) {
        RemoteFuture future = RemoteFuture.removeFuture(inputMsg.getId());
        if (null != future) {
            future.done(inputMsg);
        }
        return future;
    }
}
