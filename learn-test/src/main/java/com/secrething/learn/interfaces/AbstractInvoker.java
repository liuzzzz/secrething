package com.secrething.learn.interfaces;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by liuzz on 2019-03-14 15:56.
 */
public abstract class AbstractInvoker implements InvocationHandler {
    private Object target;
    private Intercepter intercepter;

    private AbstractInvoker() {
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        boolean hasIntecepter = null != intercepter;
        Object preSult = null;
        PreWrapper preWrapper = null;
        if (hasIntecepter) {
            preWrapper = new PreWrapper();
            preWrapper.setTarget(target);
            preWrapper.setTargetProxy(proxy);
            preWrapper.setMethod(method);
            preWrapper.setArgs(args);
            preSult = intercepter.pre(preWrapper);
        }
        Object result = invokeMethod(target, method.getName(), args);
        AfterWrapper<Object> wrapper = new AfterWrapper<>();
        wrapper.setResult(result);
        wrapper.setPreResult(preSult);
        wrapper.setPreWrapper(preWrapper);
        if (hasIntecepter) {
            Object afterSult = intercepter.after(wrapper);
            wrapper.setAfterResult(afterSult);
        }
        return wrapper;
    }

    public abstract Object invokeMethod(Object target, String methodName, Object[] args) throws Throwable;

    private Intercepter getIntercepter() {
        return intercepter;
    }

    void setTarget(Object target) {
        this.target = target;
    }

    void setIntercepter(Intercepter intercepter) {
        this.intercepter = intercepter;
    }
    private Class makeClass(Object target){
        Class clzz = target.getClass();
        return null;
    }
    public static InvocationHandler InvocationHandler(Object target,Intercepter intercepter){

        return null;
    }
}
