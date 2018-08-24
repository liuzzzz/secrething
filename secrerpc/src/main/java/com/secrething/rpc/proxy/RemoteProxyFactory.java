package com.secrething.rpc.proxy;

import com.secrething.rpc.annotation.Provider;
import com.secrething.rpc.remote.RemoteHandler;

/**
 * Created by Idroton on 2018/8/24 11:58 PM.
 */
public interface RemoteProxyFactory {
    /**
     * create proxy instance
     *
     * @param clzz     proxy class may be interface usually
     * @param beanName beanName Provider's value {@link Provider#value()}
     * @param <T>      interface's type
     * @return 代理对象
     */
    <T> T proxyInstance(Class<T> clzz, String beanName, RemoteHandler handler);
}
