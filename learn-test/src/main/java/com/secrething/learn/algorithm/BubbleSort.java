package com.secrething.learn.algorithm;

import java.util.Arrays;

/**
 * Created by liuzz on 2019-03-17 13:49.
 */
public class BubbleSort {
    public static void main(String[] args) {
        int[] i = {3,4,1,2,5,6};
        //downSort(i);
        upSort(i);
        System.out.println(Arrays.toString(i));
    }

    static void downSort(int[] arr){
        int count = 0;
        for (int i = arr.length-1; i > 0 ; i--) {
            boolean swapped = false;
            for (int j = 0; j < i; j++) {
                int pre = arr[j];
                if (arr[j+1] < pre){
                    arr[j] = arr[j+1];
                    arr[j+1] = pre;
                    swapped = true;
                }
                count++;
            }
            System.out.println(swapped);

            if (!swapped)
                break;
        }
        System.out.println(count);
    }

    static void upSort(int[] arr){
        for (int i = 0; i < arr.length-1; i++) {
            int t = arr[i];
            int k = i;
            boolean swapped =false;
            for (int j = i; j < arr.length - 1; j++) {
                if (arr[j+1] < t){
                    t = arr[j+1];
                    k = j+1;
                    swapped = true;
                }
            }
            if (!swapped){
                break;
            }
            int tmp = arr[i];
            arr[i] = arr[k];
            arr[k] = tmp;
        }
    }
}
