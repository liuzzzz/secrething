package com.secrething.rpc.core;

import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import sun.misc.ProxyGenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;

/**
 * Created by liuzz on 2019-04-24 17:02.
 */
public class Test {

    @org.junit.Test
    public void test() {
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "/Users/liuzz58/Desktop/tmp/");
        Enhancer enhancer = new Enhancer();
        enhancer.setInterfaces(new Class[]{Hello.class});
        //继承被代理类
        //enhancer.setSuperclass(Hello1.class);
        //设置回调
        enhancer.setCallback(new Intercepter());
        //生成代理类对象
        Hello h = (Hello) enhancer.create();
        h.hello();
    }

    static int convert(String s) {
        char[] chars = s.toCharArray();
        int tmp = 0;
        for (int i = 0, j = chars.length - 1; i < chars.length; i++) {
            tmp += (((int) chars[i]) - 48) * (int) (Math.pow(10, j));
            --j;
        }
        return tmp;
    }


    interface Hello {
        void hello();
    }

    public static class Intercepter implements MethodInterceptor{

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            System.out.println(method.getDeclaringClass());
            return obj;
        }
    }
}
