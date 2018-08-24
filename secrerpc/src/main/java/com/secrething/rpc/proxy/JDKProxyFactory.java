package com.secrething.rpc.proxy;

import com.secrething.rpc.remote.RemoteHandler;

import java.lang.reflect.Proxy;

/**
 * Created by liuzengzeng on 2017/12/18.
 * Dynamic proxy by jdk
 */
public class JDKProxyFactory implements RemoteProxyFactory{

    @Override
    public <T> T proxyInstance(Class<T> clzz, String beanName, RemoteHandler handler) {
        Object proxy = Proxy.newProxyInstance(clzz.getClassLoader(), new Class[]{clzz}, new JDKProxy(beanName, clzz.getName(), handler));
        return (T) proxy;
    }
}
