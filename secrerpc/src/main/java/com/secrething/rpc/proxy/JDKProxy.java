package com.secrething.rpc.proxy;

import com.secrething.common.util.Assert;
import com.secrething.rpc.remote.RemoteHandler;
import com.secrething.rpc.core.RemoteRequest;
import com.secrething.rpc.core.RemoteResponse;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * Created by liuzz on 2018/6/19.
 */
public class JDKProxy implements InvocationHandler {

    private final String beanName;
    private final String clzzName;
    private final RemoteHandler handler;

    public JDKProxy(String beanName, String clzzName, RemoteHandler handler) {
        Assert.notBlank(beanName);
        Assert.notNull(handler);
        this.beanName = beanName;
        this.clzzName = clzzName;
        this.handler = handler;

    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RemoteRequest remoteRequest = new RemoteRequest(RemoteRequest.PROXY);
        remoteRequest.setRequestId(UUID.randomUUID().toString());
        remoteRequest.setBeanName(beanName);
        remoteRequest.setClzzName(clzzName);
        remoteRequest.setMethodName(method.getName());
        remoteRequest.setParameterTypes(method.getParameterTypes());
        remoteRequest.setParameters(args);
        RemoteResponse response = handler.send(remoteRequest);
        if (null != response) {
            Throwable t = response.getThrowable();
            if (null != t)
                throw t;
            return response.getResult();
        }
        return null;
    }
}
