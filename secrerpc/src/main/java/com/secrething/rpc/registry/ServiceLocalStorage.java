package com.secrething.rpc.registry;

import com.google.common.collect.Maps;
import com.secrething.common.util.ConcurrentHashMap;
import com.secrething.common.util.ConcurrentMap;

import java.util.Map;
import java.util.function.Function;

/**
 * Created by Idroton on 2018/8/17 10:42 PM.
 */
public class ServiceLocalStorage {

    private static final ConcurrentMap<String, Invoker> cache = new ConcurrentHashMap<>();

    public static void cacheService(Class<?> interfs, Object serviceImpl) {

        Class clzz = serviceImpl.getClass();
        String className = interfs.getName();
        final Wrapper wrapper = Wrapper.getWrapper(clzz);
        cache.putIfAbsent(className, o -> new Invoker() {
            @Override
            public Object invoke(String methodName, Class<?>[] paramTypes, Object[] args) throws Exception {
                return wrapper.invokeMethod(serviceImpl, methodName, paramTypes, args);
            }
        });
    }

    public static Object remove(String name) {
        return cache.remove(name);
    }

    public static Invoker borrow(String name) {
        return cache.get(name);
    }


}
