package com.secrething.learn.test;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Created by liuzz on 2018/9/28 2:34 PM.
 */
public class TestUnsafe {
    static final Unsafe U;
    static class Node{
        int count;
        private String heo = "hello";
        private TestUnsafe testUnsafe = new TestUnsafe();
    }
    static class Node2{
        int he;
    }
    static Node[] nodes = new Node[50];
    static Node2[] nodess = new Node2[50];
    static final int BASE_ARRAY_OFFSET;
    static final int ARRAY_SCALE;
    static {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            U = (Unsafe) f.get(null);
            ARRAY_SCALE = U.arrayIndexScale(Node[].class);
            BASE_ARRAY_OFFSET = U.arrayBaseOffset(Node[].class);
        }catch (Throwable t){
            throw new Error();
        }
        for (int i = 0; i < 50; i++) {
            Node n = new Node();
            n.count = i;
            nodes[i] = n;
        }
    }
    public static <T> boolean casArrayObjByIndex(T[] arr,int index,T old,T n){
        Class arrClass = arr.getClass();
        long base = U.arrayBaseOffset(arrClass);
        int scale = U.arrayIndexScale(arrClass);
        int sft = 31 - Integer.numberOfLeadingZeros(scale);
        return U.compareAndSwapObject(arr,((long)index<<sft)+base,old,n);
    }
    //这个是没用的😂,知道index 为啥还要用 Unsafe 去取🙃
    public static <T> T getObjectFromArray(T[] arr,int index){
        Class arrClass = arr.getClass();
        long base = U.arrayBaseOffset(arrClass);
        int scale = U.arrayIndexScale(arrClass);
        int sft = 31 - Integer.numberOfLeadingZeros(scale);
        return (T)U.getObject(arr,((long)index<<sft)+base);
    }
    public static void main(String[] args) {
        boolean b = casArrayObjByIndex(nodes,0,nodes[0],nodes[1]);
        System.out.println(b);
    }
}
