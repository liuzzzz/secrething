package com.secrething.rpc.factory;

import com.secrething.rpc.annotation.RPCProvidor;
import com.secrething.rpc.proxy.JDKRemoteServiceProxy;
import com.secrething.rpc.proxy.RemoteServiceProxy;
import net.sf.cglib.proxy.Enhancer;

import java.lang.reflect.Proxy;

/**
 * Created by liuzengzeng on 2017/12/18.
 * 动态代理工厂
 */
public class RemoteServiceFactory {
    /**
     * cglib方式,创建代理对象
     * @param clzz
     * @param beanName
     * @param <T>
     * @return
     */
    public static <T> T getProxyInstance(Class<T> clzz, String beanName) {
        Enhancer enhancer = new Enhancer();
        enhancer.setInterfaces(new Class[]{clzz});
        enhancer.setCallback(new RemoteServiceProxy(beanName));
        Object instance = enhancer.create();
        return (T) instance;
    }
    /**
     *  jdk方式,创建代理对象
     * @param clzz 代理的类(正常情况下,就是一个interface)
     * @param beanName beanName 提供方名称 {@link RPCProvidor#value()}
     * @param <T> 接口类 类型
     * @return 代理对象
     */
    public static <T> T getJDKProxyInstance(Class<T> clzz, String beanName) {
        Object proxy = Proxy.newProxyInstance(clzz.getClassLoader(), new Class[]{clzz}, new JDKRemoteServiceProxy(beanName));
        return (T) proxy;
    }
}
