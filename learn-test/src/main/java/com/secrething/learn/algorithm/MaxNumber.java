package com.secrething.learn.algorithm;

import java.util.Arrays;

/**
 * Created by liuzz on 2019-04-08 13:31.
 */
public class MaxNumber {

    public static void maxNumber(String[] arr) {
        for (int i = 0; i < arr.length - 1; ++i) {

            int maxIdx = i;
            String si = arr[maxIdx];
            for (int j = i + 1; j < arr.length; ++j) {
                String sj = arr[j];
                int m1 = Integer.parseInt(si + sj);
                int m2 = Integer.parseInt(sj + si);
                if (m1 < m2) {
                    maxIdx = j;
                }
            }
            if (maxIdx != i)
                swap(arr, i, maxIdx);
        }

    }

    private static void swap(String[] arr, int i, int j) {
        String tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static void main(String[] args) {
        String[] arr = {"98", "98", "9", "23", "334", "3", "9"};
        maxNumber(arr);
        System.out.println(Arrays.toString(arr));
    }
}
