package com.secrething.rpc.proxy;

import com.secrething.common.util.Assert;
import com.secrething.rpc.core.RemoteHandler;
import com.secrething.rpc.core.RemoteRequest;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.UUID;

/**
 * Created by liuzengzeng on 2017/12/18.
 * 服务消费端动态代理 remote接口
 */
public class RemoteServiceProxy implements MethodInterceptor {
    private final String beanName;
    private final RemoteHandler handler;

    public RemoteServiceProxy(String beanName, RemoteHandler handler) {
        Assert.notBlank(beanName);
        Assert.notNull(handler);
        this.beanName = beanName;
        this.handler = handler;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        if (Object.class == method.getDeclaringClass()) {
            String name = method.getName();
            if ("equals".equals(name)) {
                return o == objects[0];
            } else if ("hashCode".equals(name)) {
                return System.identityHashCode(o);
            } else if ("toString".equals(name)) {
                return o.getClass().getName() + "@" +
                        Integer.toHexString(System.identityHashCode(o)) +
                        ", with InvocationHandler " + this;
            } else {
                throw new IllegalStateException(String.valueOf(method));
            }
        }
        RemoteRequest remoteRequest = new RemoteRequest(RemoteRequest.PROXY);
        remoteRequest.setRequestId(UUID.randomUUID().toString());
        remoteRequest.setBeanName(beanName);
        remoteRequest.setMethodName(method.getName());
        remoteRequest.setParameterTypes(method.getParameterTypes());
        remoteRequest.setParameters(objects);
        return handler.send(remoteRequest);
    }
}
