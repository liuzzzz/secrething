package com.secrething.learn.algorithm;

import com.secrething.common.util.Assert;

import java.util.Arrays;

/**
 * Created by Idroton on 2018/9/16 7:12 PM.
 */
public class Heap {
    private static final boolean MIN = false;
    private static final boolean MAX = true;
    private final int[] arr;
    private final boolean type;

    private void buildHeap() {
        int len = arr.length;
        int beginIdx = (len - 2) >> 1;
        for (int i = beginIdx; i >= 0; i--) {
            siftDown(len, i);
        }
    }

    private Heap(int[] arr, boolean type) {
        this.arr = arr;
        this.type = type;
        buildHeap();
    }

    private void siftDown(int len, int idx) {
        if (idx < len) {
            int left = left(idx);
            int right = right(idx);
            int m = idx;
            if (left < len && compare(left, m)) {
                m = left;
            }
            if (right < len && compare(right, m)) {
                m = right;
            }
            if (m != idx) {
                swap(m, idx);
                siftDown(len, m);
            }

        }
    }

    private void siftUp(int len, int idx) {
        if (idx < len && idx > 0) {
            int p = parent(idx);
            int left = left(p);
            int right = right(p);
            int m = p;
            if (left < len && compare(left, m)) {
                m = left;
            }
            if (right < len && compare(right, m)) {
                m = right;
            }
            if (m != idx) {
                swap(m, idx);
                siftUp(len, p);
            }
        }
    }

    private boolean compare(int i, int j) {
        if (type == MAX) {
            return arr[i] > arr[j];
        } else {
            return arr[i] < arr[j];
        }
    }

    private void swap(int idx1, int idx2) {
        Assert.isTrue(idx1 < arr.length && idx2 < arr.length);
        int tmp = arr[idx1];
        arr[idx1] = arr[idx2];
        arr[idx2] = tmp;
    }

    public static final Heap maxHeap(final int[] arr) {
        Assert.notNull(arr);
        return new Heap(arr, MAX);
    }

    public static final Heap minHeap(final int[] arr) {
        Assert.notNull(arr);
        return new Heap(arr, MIN);
    }

    private static int left(int p) {
        return (p << 1) + 1;
    }

    private static int right(int p) {
        return left(p) + 1;
    }

    private static int parent(int c) {
        if (c == 0)
            return 0;
        return (c - 1) >> 1;
    }
    private void sort(){
        for (int i = arr.length -1; i >= 0; i--) {
            swap(0,i);
            siftDown(i,0);
        }
    }
    public static void main(String[] args) {
        int[] arr = {4, 3, 3, 7, 6, 9, 12};
        Heap heap = minHeap(arr);
        heap.sort();
        System.out.println(Arrays.toString(arr));

    }

}
