package com.secrething.rpc.proxy;

import com.secrething.rpc.client.ClientHandler;
import com.secrething.rpc.core.RemoteFuture;
import com.secrething.rpc.core.RemoteRequest;
import com.secrething.rpc.core.RemoteResponse;
import com.secrething.rpc.registry.ServiceConnectManage;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * Created by liuzz on 2018/6/19.
 */
public class JDKRemoteServiceProxy implements InvocationHandler {

    private String beanName;
    public JDKRemoteServiceProxy(String beanName){
        this.beanName = beanName;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RemoteRequest remoteRequest = new RemoteRequest();
        remoteRequest.setRequestId(UUID.randomUUID().toString());
        remoteRequest.setBeanName(beanName);
        remoteRequest.setMethodName(method.getName());
        remoteRequest.setParameterTypes(method.getParameterTypes());
        remoteRequest.setParameters(args);
        ClientHandler handler = ServiceConnectManage.getInstance().chooseHandler();
        if (null == handler)
            return RemoteResponse.defail(remoteRequest.getRequestId(),"net socket exception").getResult();
        RemoteFuture future = handler.sendRequest(remoteRequest);
        return future.get();
    }
}
