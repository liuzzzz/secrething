package com.secrething.learn.interfaces;

import java.lang.reflect.Proxy;

/**
 * Created by liuzz on 2018-12-21 18:01.
 */
public class Test {
    public static void main(String[] args) {
        Hello h = (Hello) Proxy.newProxyInstance(Hello.class.getClassLoader(),new Class[]{Hello.class},new Invoker(new HelloImpl()));
        System.out.println(h.hello1());
        System.out.println(h.hello2("secret"));
    }
}
