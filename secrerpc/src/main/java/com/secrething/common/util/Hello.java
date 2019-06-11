package com.secrething.common.util;

import java.lang.reflect.Constructor;

/**
 * Created by liuzz on 2019-04-24 14:39.
 * 饿汉单例 防反射
 */
public class Hello {
    private Hello() {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        if (!"<clinit>".equals(elements[2].getMethodName())){
           throw new UnsupportedOperationException("unsupport create in this way");
        }
    }

    public static void he() {
        System.out.println("he");
    }

    private static final Hello instance = new Hello();

    public static Hello getInstance() {
        return instance;
    }

    public static void main(String[] args) throws Exception {
        Singleton.getInstance();
    }
}
