package com.secrething.learn.interfaces;

import java.lang.reflect.Method;

/**
 * Created by liuzz on 2019-03-14 16:38.
 */
public class PreWrapper {

    private Object target;
    private Object targetProxy;
    private Method method;
    private Object[] args;

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public Object getTargetProxy() {
        return targetProxy;
    }

    public void setTargetProxy(Object targetProxy) {
        this.targetProxy = targetProxy;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
}
