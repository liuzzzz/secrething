package com.secrething.common.util;


import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author liuzengzeng
 * @create 2018/1/13
 */
public class TreeBin {

    Node root;


    public void insert(int data) {
        Node b = new Node(data);
        if (null == root)
            root = b;
        else {
            Node p = null;
            Node next = root;
            boolean insl = false;
            while (next != null) {
                p = next;
                if (data <= next.data) {
                    insl = true;
                    next = next.left;
                } else {
                    insl = false;
                    next = next.right;
                }

            }
            b.parent = p;
            if (insl)
                p.left = b;
            else
                p.right = b;
        }
        return;

    }

    static class Node {
        int data;
        Node left;
        Node right;
        Node parent;
        boolean red;

        public Node(int data) {
            this.data = data;
        }
    }

    public static void main(String[] args) {
        TreeBin bin = new TreeBin();
        int[] arr = {12, 1, 9, 2, 0, 11, 7, 19, 4, 15, 18, 5, 14, 13, 10, 16, 6, 3, 8, 17};
        for (int i = 0; i < arr.length; i++) {
            bin.insert(arr[i]);
        }
        Node node = bin.root;
        LinkedBlockingQueue<Node> queue = new LinkedBlockingQueue<>();
        queue.offer(node);
        for (; ; ) {
            Node n = queue.poll();
            if (null == n) {
                break;
            }
            System.out.println(n.data);
            if (null != n.left)
                queue.offer(n.left);
            if (null != n.right)
                queue.offer(n.right);
        }

    }
}
