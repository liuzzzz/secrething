package com.secrething.learn.test;

import javassist.*;

/**
 * Created by Idroton on 2018/9/2 19:49.
 */
public class ByteCodeTest {
    public static void main(String[] args) throws Exception {
        String clzzName = "com.secrething.learn.test.HelloWorld";

        String helloMehodCode = "public void hello() {" +
                "System.out.println(this.action);" +
                "}";
        //pool
        ClassPool classPool = ClassPool.getDefault();
        //class
        CtClass ctclzz = classPool.makeClass(clzzName);
        //field
        CtField field = CtField.make("private String action;",ctclzz);
        ctclzz.addField(field);
        //interface
        CtClass inteface = classPool.get(Hello.class.getName());
        ctclzz.setInterfaces(new CtClass[]{inteface});
        //method
        CtMethod m = CtNewMethod.make(helloMehodCode,ctclzz);
        ctclzz.addMethod(m);
        //constructor
        CtConstructor ctConstructor = CtNewConstructor.make("public HelloWorld(){this.action = \"sayHello\";}",ctclzz);
        ctclzz.addConstructor(ctConstructor);
        //toClass
        Class clzz = ctclzz.toClass();
        //invoke
        Hello hello = (Hello) clzz.newInstance();
        hello.hello();

    }
}
