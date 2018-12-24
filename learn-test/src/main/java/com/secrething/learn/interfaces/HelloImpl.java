package com.secrething.learn.interfaces;

/**
 * Created by liuzz on 2018-12-21 17:59.
 */
public class HelloImpl implements Hello {
    @Override
    public String hello1() {
        return "hello1";
    }

    @Override
    public String hello2(String name) {
        return "hello2:" + name;
    }
}
