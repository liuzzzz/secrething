package com.secrething.rpc.remote;

import com.secrething.rpc.core.RemoteFuture;
import com.secrething.rpc.core.RemoteResponse;
import com.secrething.rpc.core.TaskCache;
import com.secrething.rpc.protocol.ProcessService;

/**
 * Created by Idroton on 2018/8/12.
 */
public class ClientProcessService implements ProcessService<RemoteResponse, Void> {
    @Override
    public Void process(RemoteResponse inputMsg) {
        RemoteFuture future = TaskCache.remove(inputMsg.getRequestId());
        if (null != future) {
            future.done(inputMsg);
        }
        return null;
    }
}
