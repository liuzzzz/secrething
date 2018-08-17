package com.secrething.rpc.registry;

import com.google.common.collect.Maps;
import com.secrething.rpc.remote.ServerProcessService;

import java.util.Map;

/**
 * Created by Idroton on 2018/8/17 10:42 PM.
 */
public class ServiceStorage {

    private static final Map<String, Object> cache = Maps.newConcurrentMap();

    public static void cacheService(String name, Object serviceImpl) {

        Class clzz = serviceImpl.getClass();
        Class<?>[] interfaces = clzz.getInterfaces();
        cache.put(name, serviceImpl);
        if (interfaces.length > 0)
            for (Class clz : interfaces) {
                cache.put(clz.getName(), serviceImpl);
            }

    }

    public static Object borrow(String name) {
        return cache.get(name);
    }

    public static void main(String[] args) {
        cacheService("hello", new ServerProcessService());
    }
}
