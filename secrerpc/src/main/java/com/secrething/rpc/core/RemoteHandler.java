package com.secrething.rpc.core;

/**
 * Created by liuzengzeng on 2018/8/11.
 */
public interface RemoteHandler {
    RemoteFuture sendRequest(RemoteRequest remoteRequest);
}
