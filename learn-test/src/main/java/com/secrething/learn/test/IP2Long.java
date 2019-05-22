package com.secrething.learn.test;

import java.util.Arrays;

/**
 * Created by liuzz on 2019-05-08 21:16.
 */
public class IP2Long {
    private static long seg = '1' - 1;
    public static void main(String[] args) {
        String[] arr = new String[]{"192.168.001.001","192.168.0.1"};
        System.out.println(java.util.Arrays.toString(ips2Long(arr)));
    }

    public static long[] ips2Long(String[] arr) {
        int len = arr.length;
        long[] res = new long[len];
        for (int i = 0; i < len; i++) {
            res[i] = ip2Long(arr[i]);
        }
        return res;
    }

    private static long ip2Long(String ip) {
        char[] chars = ip.toCharArray();
        int len = chars.length;
        long tmp = 0L;
        for (int i = 0, j = len - 4; i < len; i++) {
            char c = chars[i];
            if ('.' == c) {
                continue;
            }
            tmp += (c - seg)* pow(10,j);
            --j;
        }
        return tmp;

    }

    private static long pow(long base,int pow){
        if (pow == 0)
            return 1;

        long t = base;
        for (int i = 0; i < pow -1; i++) {
            t = t*base;
        }
        return t;
    }
}
