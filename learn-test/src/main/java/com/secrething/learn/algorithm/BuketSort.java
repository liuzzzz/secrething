package com.secrething.learn.algorithm;

import java.util.Arrays;

/**
 * Created by liuzz on 2019-03-17 14:24.
 */
public class BuketSort {

    private static class Node{
        int value;
        Node next;
    }
    static void sort(int[] arr){
        int min = arr[0];
        int max = arr[0];
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < min){
                min = arr[i];
            }
            if (arr[i] > max){
                max = arr[i];
            }
        }
        Node[] nodes = new Node[max-min+1];
        for (int i = 0; i < arr.length; i++) {
            int idx = arr[i] - min;
            Node newNode = new Node();
            newNode.value = arr[i];
            Node n = nodes[idx];
            if (null == n){
                n = newNode;
                nodes[idx] = n;

            }else {
                while (null != n.next){
                    n = n.next;
                }
                n.next = newNode;
            }
        }
        int index = 0;
        for (int i = 0; i <nodes.length ; i++) {
            Node n = nodes[i];
            while (null != n){
                arr[index] = n.value;
                n = n.next;
                ++index;
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {2,3,2,5,4,3,6,7,7,7,7,11};
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
