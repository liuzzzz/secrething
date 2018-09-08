package com.secrething.rpc.registry;

/**
 * Created by Idroton on 2018/9/5 10:45 PM.
 */
public abstract class Invoker {
    public abstract Object invoke(String methodName, Class<?>[] paramTypes, Object[] args) throws Exception;
}
