package com.secrething.learn.algorithm;

import java.util.concurrent.TimeUnit;

/**
 * Created by liuzz on 2018/9/20 1:35 PM.
 */
public class TestBitMap {
    public static boolean contains(String s1,String s2){
        char[] chars = s1.toCharArray();
        int[] ints = new int[1024];
        int m = ~Integer.MAX_VALUE;
        for (char c : chars) {
            int i = (int) c;
            int idx = i >> 5;
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
            int idx = i >> 5;
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
