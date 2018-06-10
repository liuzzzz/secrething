package com.secrething.rpc.proxy;

import com.secrething.common.util.Assert;
import net.sf.cglib.reflect.FastMethod;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by liuzz on 2018/4/16.
 */
public class ProxyOperation {
    private final Object target;
    private final FastMethod fastMethod;

    public ProxyOperation(Object target, FastMethod fastMethod) {
        Assert.notNull(target);
        Assert.notNull(fastMethod);
        this.target = target;
        this.fastMethod = fastMethod;
    }
    public Object invoke(Object... params) throws InvocationTargetException {
        return fastMethod.invoke(target,params);
    }
}
