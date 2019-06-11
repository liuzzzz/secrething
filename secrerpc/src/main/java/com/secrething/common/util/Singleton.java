package com.secrething.common.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by liuzz on 2019-05-27 17:39.
 */
public class Singleton {
    private String test;

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    private Singleton(){
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        System.out.println(Arrays.toString(elements));
    }
    private static class Inner{
        private static final Singleton s = new Singleton();
    }
    public static Singleton getInstance(){
        return Inner.s;
    }

    public static void main(String[] args) throws Exception {
        Singleton singleton = getInstance();

        PropertyDescriptor pd = new PropertyDescriptor("test",Singleton.class);
        Method m = pd.getReadMethod();
        System.in.read();
    }
}
