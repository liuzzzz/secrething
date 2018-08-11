package com.secrething.rpc.proxy;

import com.secrething.common.util.Assert;
import com.secrething.rpc.core.RemoteFuture;
import com.secrething.rpc.core.RemoteHandler;
import com.secrething.rpc.core.RemoteRequest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * Created by liuzz on 2018/6/19.
 */
public class JDKRemoteServiceProxy implements InvocationHandler {

    private final String beanName;
    private final RemoteHandler handler;
    public JDKRemoteServiceProxy(String beanName, RemoteHandler handler){
        Assert.notBlank(beanName);
        Assert.notNull(handler);
        this.beanName = beanName;
        this.handler = handler;

    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RemoteRequest remoteRequest = new RemoteRequest();
        remoteRequest.setRequestId(UUID.randomUUID().toString());
        remoteRequest.setBeanName(beanName);
        remoteRequest.setMethodName(method.getName());
        remoteRequest.setParameterTypes(method.getParameterTypes());
        remoteRequest.setParameters(args);
        RemoteFuture future = handler.sendRequest(remoteRequest);
        return future.get();
    }
}
