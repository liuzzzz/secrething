package com.secrething.learn.algorithm;

/**
 * Created by liuzz on 2019-05-15 17:57.
 */
public class Dynamic {


    public static int largestSequence(String str1,String str2){
        char[] chars1 = str1.toCharArray();
        char[] chars2 = str2.toCharArray();
        int[][] mem = new int[chars1.length][chars2.length];
        return 0;
    }

    public static int countStep(int n){
        if (n <= 2)
            return n;
        int[] arr = new int[n+1];
        arr[0] = 0;
        arr[1] = 1;
        arr[2] = 2;
        return fillArr(n,arr);

    }
    private static int fillArr(int n,int[] arr){
        if (n <= 2)
            return arr[n];
        arr[n] = fillArr(n-1,arr)+fillArr(n-2,arr);
        return arr[n];
    }

    public static void main(String[] args) {
        System.out.println(countStep(5));
    }
}
