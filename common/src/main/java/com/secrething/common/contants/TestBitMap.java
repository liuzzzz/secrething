package com.secrething.common.contants;

import java.util.concurrent.TimeUnit;

/**
 * Created by liuzz on 2018/9/20 1:35 PM.
 */
public class TestBitMap {
    static Integer count =1;
    public static void main(String[] args) throws InterruptedException {
        /*String s = " qdasdp";
        String s1 = "qp";
        Integer i = new Integer(1);
        Integer q = new Integer(1);
        System.out.println(Integer.valueOf(129) == Integer.valueOf(129));*/


        new Thread(() -> {
            synchronized (count){
                try {
                    TimeUnit.SECONDS.sleep(10);
                    System.out.println("???????");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        TimeUnit.SECONDS.sleep(1);
        synchronized (count){
            //这一句，要在上面线程执行完才会执行
            System.out.println("我走了");
            count++;
        }

    }
    public static boolean contains(String s1,String s2){
        char[] chars = s1.toCharArray();
        int[] ints = new int[1024];
        int m = ~Integer.MAX_VALUE;
        for (char c : chars) {
            int i = (int) c;
            int idx = i / 32;
            if (idx >= ints.length){
                int[] narr = new int[ints.length*2];
                System.arraycopy(ints,0,narr,0,ints.length);
                ints = narr;
            }
            int old = ints[idx];
            int offset = i % 32;

            int v = m >> offset;
            ints[idx] = (old | v);

        }
        for (char c:s2.toCharArray()) {
            int i = (int) c;
            int idx = i / 32;
            int old = ints[idx];
            int offset = i % 32;
            int v = m >> offset;
            if ((v & old) == 0){
                return false;
            }
        }
        return true;
    }

}
