package com.secrething.rpc.core;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by Idroton on 2018/8/11.
 * send request to server
 */
public interface RemoteHandler {
    RemoteResponse send(RemoteRequest remoteRequest) throws ExecutionException, InterruptedException;
    RemoteResponse send(RemoteRequest remoteRequest, long timeout, TimeUnit unit) throws ExecutionException, InterruptedException, TimeoutException;
}
