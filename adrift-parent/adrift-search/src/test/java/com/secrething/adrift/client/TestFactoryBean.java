package com.secrething.adrift.client;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * Created by liuzz on 2018-12-19 18:10.
 */
@Component("hello")
public class TestFactoryBean<T> implements FactoryBean<T> {
    public static class Test{

    }
    @Override
    public T getObject() throws Exception {
        return (T)new Test();
    }

    @Override
    public Class<T> getObjectType() {
        return null;
    }
}
