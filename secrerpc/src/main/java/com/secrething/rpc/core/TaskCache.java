package com.secrething.rpc.core;

import com.secrething.common.util.GuavaCacheFactory;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by Idroton on 2018/8/12.
 */
public class TaskCache {
    private static final ConcurrentMap<String, RemoteFuture> cache = GuavaCacheFactory.mapInstance(10000, 10, TimeUnit.SECONDS, 10, TimeUnit.SECONDS);

    public static void cache(RemoteFuture future) {
        cache.put(future.getRemoteRequest().getRequestId(), future);
    }

    public static RemoteFuture remove(String reqId) {
        return cache.remove(reqId);
    }
}
