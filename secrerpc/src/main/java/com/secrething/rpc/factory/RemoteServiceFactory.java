package com.secrething.rpc.factory;

import com.secrething.rpc.proxy.RemoteServiceProxy;
import net.sf.cglib.proxy.Enhancer;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by liuzengzeng on 2017/12/18.
 * 动态代理工厂
 */
public class RemoteServiceFactory {
    public static <T> T getProxyInstance(Class<T> clzz,String beanName) {
        Enhancer enhancer = new Enhancer();
        enhancer.setInterfaces(new Class[]{clzz});
        enhancer.setCallback(new RemoteServiceProxy(beanName));
        Object instance = enhancer.create();
        return (T)instance;
    }

}
