package com.secrething.learn.test;

/**
 * Created by liuzz on 2018/9/29 5:04 PM.
 */
public class Demo {
    public static void main(String[] args) {
        new Demo();
    }
    public Demo(){
        Demo demo1 = this;
        Demo demo2 = this;
        synchronized (demo1){
            try {
                demo2.wait();
                System.out.println("Waiting");
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("Interrupted");
            }catch (Exception e){
                System.out.println("Other Exception");
            }finally {
                System.out.println("Finally");
            }
            System.out.println("OK");
        }
    }
}
