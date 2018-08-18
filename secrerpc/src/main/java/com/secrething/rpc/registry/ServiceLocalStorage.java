package com.secrething.rpc.registry;

import com.google.common.collect.Maps;
import com.secrething.rpc.remote.ServerProcessService;

import java.util.Map;

/**
 * Created by Idroton on 2018/8/17 10:42 PM.
 *
 */
public class ServiceLocalStorage {

    private static final Map<String, Object> cache = Maps.newConcurrentMap();

    public static void cacheService(String name, Object serviceImpl) {

        Class clz = serviceImpl.getClass();
        Class<?>[] interfaces = clz.getInterfaces();
        cache.put(name, serviceImpl);
        if (interfaces.length > 0)
            for (Class cls : interfaces) {
                cache.put(cls.getName(), serviceImpl);
            }

    }

    public static Object borrow(String name) {
        return cache.get(name);
    }

}
