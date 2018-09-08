package com.secrething.learn.test;

import javassist.*;

import java.util.ServiceLoader;

/**
 * Created by Idroton on 2018/9/2 19:49.
 */
public class ByteCodeTest {
    public static void main(String[] args) throws Exception {

        //Location.getInfo();
        testServiceLoader();

    }
    public static class Location {
        public static void getInfo(){
            String location="";
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            location = "类名："+stacks[2].getClassName() + "\n函数名：" + stacks[2].getMethodName()
                    + "\n文件名：" + stacks[2].getFileName() + "\n行号："
                    + stacks[2].getLineNumber() + "";
            System.out.println(location);
        }
    }
    static void testServiceLoader(){
        System.out.println(ServiceLoader.class.getClassLoader());
    }
    static void testByteCode() throws Exception{
        String clzzName = "com.secrething.learn.test.HelloWorld";

        String helloMehodCode = "public void hello() {" +
                "System.out.println(this.action);" +
                "}";
        //pool
        ClassPool classPool = new ClassPool(null);
        classPool.appendSystemPath();
        //classPool.appendClassPath("com.secrething.learn.test");
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        //System.out.println(cl.getResource(""));
        classPool.appendClassPath(new LoaderClassPath(cl));
        //class
        CtClass ctclzz = classPool.makeClass(clzzName);
        //field
        CtField field = CtField.make("private String action;",ctclzz);
        ctclzz.addField(field);
        //interface
        CtClass inteface = classPool.getCtClass(Hello.class.getName());
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
        Hello hello = (Hello) clzz.getConstructor().newInstance();
        hello.hello();

    }
}
