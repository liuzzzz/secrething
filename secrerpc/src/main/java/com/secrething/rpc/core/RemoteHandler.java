package com.secrething.rpc.core;

/**
 * Created by Idroton on 2018/8/11.
 */
public interface RemoteHandler {
    RemoteFuture send(RemoteRequest remoteRequest);
}
