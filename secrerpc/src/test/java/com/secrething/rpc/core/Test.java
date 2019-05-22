package com.secrething.rpc.core;

/**
 * Created by liuzz on 2019-04-24 17:02.
 */
public class Test {

    public static void main(String[] args) {
        System.out.println(convert("483092812"));
    }

    static int convert(String s){
        char[] chars = s.toCharArray();
        int tmp = 0;
        for (int i = 0,j = chars.length-1; i < chars.length ; i++) {
            tmp += (((int)chars[i]) - 48)* (int)(Math.pow(10,j));
            --j;
        }
        return tmp;
    }


}
