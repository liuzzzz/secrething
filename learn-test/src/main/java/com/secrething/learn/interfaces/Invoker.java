package com.secrething.learn.interfaces;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by liuzz on 2018-12-21 18:00.
 */
public class Invoker implements InvocationHandler {

    private final Object target;

    public Invoker(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("invoked");
        return method.invoke(target,args);
    }
}
