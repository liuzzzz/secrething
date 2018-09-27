package com.secrething.learn.test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Idroton on 2018/9/16 7:12 PM.
 */
public class Heap {
    private final Node[] arr = new Node[3];
    int idx = 0;
    private static class Node {
        private String id;
        private int count;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
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
    private void buildHeap() {
        int len = arr.length;
        int beginIdx = (len - 2) >> 1;
        for (int i = beginIdx; i >= 0; i--) {
            siftDown(len, i);
        }
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
            int m = idx;
            if (compare(idx, p)) {
                m = p;
            }
            if (m != idx) {
                swap(m, idx);
                siftUp(len, p);
            }
        }
    }


    private boolean compare(int i, int j) {
        if (arr[i] == null)
            return true;
        else if (arr[i] != null && arr[j] == null) {
            return false;
        } else {
            return arr[i].count < arr[j].count;
        }

    }

    private void swap(int idx1, int idx2) {
        if (idx1 < arr.length && idx2 < arr.length){
            Node tmp = arr[idx1];
            arr[idx1] = arr[idx2];
            arr[idx2] = tmp;
        }

    }

    public static Map<String,Integer> buildMap(String[] idtable){
        Map<String,Integer> map = new HashMap<>();
        for (String id:idtable) {
            Integer i = map.get(id);
            if (i != null){
                map.put(id,++i);
            }else {
                map.put(id,1);
            }
        }
        return map;
    }

    public static void begin(String[] idtable){
        Map<String,Integer> map = buildMap(idtable);
        Heap heap = new Heap();
        for (Map.Entry<String,Integer> e:map.entrySet()) {
            Node node = new Node();
            node.setId(e.getKey());
            node.setCount(e.getValue());
            if (heap.idx < 2){
                heap.arr[heap.idx] = node;
                heap.idx += 1;
            }else if (heap.idx == 2){
                heap.buildHeap();
                if (node.getCount() > heap.arr[0].getCount()){
                    heap.arr[0] = node;
                    heap.siftDown(3,0);
                }
            }else {
                if (node.getCount() > heap.arr[0].getCount()){
                    heap.arr[0] = node;
                    heap.siftDown(3,0);
                }
            }

        }
        for (Node n:heap.arr) {
            System.out.print(n.getId()+"\t");
        }
    }

}
