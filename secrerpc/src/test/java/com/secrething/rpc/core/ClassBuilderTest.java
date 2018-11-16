package com.secrething.rpc.core;

import com.secrething.common.util.Out;
import org.junit.Test;

/**
 * Created by Idroton on 2018/9/9 2:27 PM.
 */
public class ClassBuilderTest {

    @Test
    public void toClass() throws Exception{
       test1();


       /* ClassPool classPool = new ClassPool(null);
        classPool.appendSystemPath();
        //classPool.appendClassPath("com.secrething.learn.algorithm");
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
    public void test1() throws Exception{
        for (int i = 0; i <2 ; i++) {
            String clzzName = "com.secrething.rpc.core.HelloWorld";

            String helloMehodCode = "public java.util.List hello(String name) " +
                    "{" +
                        "return new java.util.ArrayList();" +
                    "}";
            //pool
            //String finalize = "protected void finalize() throws Throwable { System.out.println(\"class gc\"); }";
            ClassLoader loader = new MyLoader();
            ClassBuilder builder = new ClassBuilder(loader);
            builder.clazz(clzzName);
            builder.addMethod(helloMehodCode);
            //builder.importPackage("java.util");
            //builder.addMethod(finalize);
            builder.addInterface(HelloService.class);
            Class<?> clzz = builder.toClass();
            HelloService helloService = (HelloService) clzz.getConstructor().newInstance();
            Out.log(helloService.hello("zhangsan").size());
            loader = null;
            builder = null;
            clzz = null;
            clzzName = null;
            helloService = null;
            System.gc();
            System.gc();
            Thread.sleep(5000);

        }
    }
    private static class MyLoader extends ClassLoader{
        @Override
        protected void finalize() throws Throwable {
            super.finalize();
            Out.log("gc###");
        }
    }
}