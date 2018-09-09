package com.secrething.rpc.core;

import com.secrething.common.util.Console;
import org.junit.Test;

/**
 * Created by Idroton on 2018/9/9 2:27 PM.
 */
public class ClassBuilderTest {

    @Test
    public void toClass() throws Exception{
        String clzzName = "com.secrething.rpc.core.HelloWorld";

        String helloMehodCode = "public String hello(String name) {" +
                "return \"hello :\" + name;" +
                "}";
        //pool
        ClassBuilder builder = new ClassBuilder();
        builder.clazz(clzzName);
        builder.addMethod(helloMehodCode);
        builder.addInterface(HelloService.class);
        Class<?> clzz = builder.toClass();
        HelloService helloService = (HelloService) clzz.getConstructor().newInstance();
        Console.log(helloService.hello("zhangsan"));
       /* ClassPool classPool = new ClassPool(null);
        classPool.appendSystemPath();
        //classPool.appendClassPath("com.secrething.learn.test");
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        //System.out.log(cl.getResource(""));
        classPool.appendClassPath(new LoaderClassPath(cl));
        //class
        CtClass ctclzz = classPool.makeClass(clzzName);
        //field
        CtField field = CtField.make("private String action;",ctclzz);
        ctclzz.addField(field);
        //interface
        CtClass inteface = classPool.getCtClass(HelloService.class.getName());
        ctclzz.setInterfaces(new CtClass[]{inteface});
        //method
        CtMethod m = CtNewMethod.make(helloMehodCode,ctclzz);
        ctclzz.addMethod(m);
        //constructor
        CtConstructor ctConstructor = CtNewConstructor.make("public HelloWorld(){this.action = \"sayHello\";}",ctclzz);
        ctclzz.addConstructor(ctConstructor);
        //toClass
        Class clzz = ctclzz.toClass(Thread.currentThread().getContextClassLoader(),classPool.getClass().getProtectionDomain());
        //invoke
        HelloService hello = (HelloService) clzz.getConstructor().newInstance();
        hello.hello();*/
    }

}