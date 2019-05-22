package com.secrething.learn.algorithm;


import java.util.Arrays;

/**
 * Created by liuzz on 2019-03-17 12:58.
 */
public class QuickSort {
    public static void main(String[] args) {
        int[] a = {5,6,3,1,911,32,32,45,45,45,45};
        quickSort(a,0,a.length-1);
        System.out.println(Arrays.toString(a));
    }

    static void quickSort(int[] a,int l,int r){
        if (null == a || a.length < 1 || l >= r || l < 0 || r >= a.length){
            return;
        }
        int key = a[l];
        int i = l;
        int j = r;
        while (i < j){
            while (a[j]>=key && i < j){
                j--;
            }
            if (i < j)
                a[i] = a[j];
            while (a[i]<= key && i < j){
                i++;
            }
            if (i <j){
                a[j] = a[i];
            }
        }
        a[i] = key;
        quickSort(a,l, i-1);
        quickSort(a,i+1,r);
    }

    private static void swap(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

}
