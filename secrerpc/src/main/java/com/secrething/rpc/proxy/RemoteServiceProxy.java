package com.secrething.rpc.proxy;

import com.secrething.rpc.client.ClientHandler;
import com.secrething.rpc.core.RemoteFuture;
import com.secrething.rpc.core.RemoteRequest;
import com.secrething.rpc.core.RemoteResponse;
import com.secrething.rpc.registry.ServiceConnectManage;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.util.UUID;

/**
 * Created by liuzengzeng on 2017/12/18.
 * 服务消费端动态代理 remote接口
 */
public class RemoteServiceProxy implements MethodInterceptor {
    private String beanName;

    public RemoteServiceProxy(String beanName) {
        if (StringUtils.isBlank(beanName))
            throw new NullPointerException("beanName is blank");
        this.beanName = beanName;
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
        RemoteRequest remoteRequest = new RemoteRequest();
        remoteRequest.setRequestId(UUID.randomUUID().toString());
        remoteRequest.setBeanName(beanName);
        remoteRequest.setMethodName(method.getName());
        remoteRequest.setParameterTypes(method.getParameterTypes());
        remoteRequest.setParameters(objects);
        ClientHandler handler = ServiceConnectManage.getInstance().chooseHandler();
        if (null == handler)
            return RemoteResponse.defail(remoteRequest.getRequestId(),"net socket exception").getResult();
        RemoteFuture future = handler.sendRequest(remoteRequest);
        return future.get();
    }
}
