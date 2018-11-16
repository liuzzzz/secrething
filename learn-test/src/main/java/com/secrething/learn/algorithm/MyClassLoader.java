package com.secrething.learn.algorithm;


import java.io.File;
import java.io.FileInputStream;
import java.util.concurrent.TimeUnit;

/**
 * Created by liuzz on 2018/11/8 下午3:10.
 */
public class MyClassLoader extends ClassLoader {


    public static void main(String[] args) throws Exception{
        MyClassLoader loader = new MyClassLoader();
        File file = new File("/Users/liuzz58/Desktop/Heap.class");
        FileInputStream fis = new FileInputStream(file);
        byte[] bytes = new byte[fis.available()];
        fis.read(bytes);
        Class clzz = loader.defineClass("com.secrething.learn.algorithm.Heap",bytes,0,bytes.length);
        //loader.resolveClass(clzz);
        Object o = clzz.newInstance();
        System.out.println(clzz);
        System.out.println(o);
        loader = null;
        o = null;
        clzz = null;
        System.gc();
        System.gc();
        System.gc();
        System.gc();
        TimeUnit.SECONDS.sleep(500);
    }

}
