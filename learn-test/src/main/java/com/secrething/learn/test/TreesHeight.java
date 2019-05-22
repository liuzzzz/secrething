package com.secrething.learn.test;

/**
 * Created by liuzz on 2019-05-08 20:43.
 */
public class TreesHeight {
    public static void main(String[] args) {
        TreeNode<Integer> treeNode1 = new TreeNode<>(1);
        TreeNode<Integer> treeNode2 = new TreeNode<>(2);
        TreeNode<Integer> treeNode3 = new TreeNode<>(3);
        TreeNode<Integer> treeNode4 = new TreeNode<>(4);
        TreeNode<Integer> treeNode5 = new TreeNode<>(5);
        TreeNode<Integer> treeNode6 = new TreeNode<>(6);
        TreeNode<Integer> treeNode7 = new TreeNode<>(7);
        TreeNode<Integer> treeNode8 = new TreeNode<>(8);
        TreeNode<Integer> treeNode9 = new TreeNode<>(9);
        TreeNode<Integer> treeNode10 = new TreeNode<>(10);
        TreeNode<Integer> treeNode11 = new TreeNode<>(11);
        TreeNode<Integer> treeNode12 = new TreeNode<>(12);
        TreeNode<Integer> treeNode13 = new TreeNode<>(13);
        treeNode1.left = treeNode2;
        treeNode1.right = treeNode3;
        treeNode2.left = treeNode4;
        treeNode2.right = treeNode5;
        treeNode3.right = treeNode7;
        //4
        treeNode5.left = treeNode6;
        treeNode6.right = treeNode13;
        treeNode7.left = treeNode8;
        treeNode8.left = treeNode9;
        treeNode9.left = treeNode10;
        treeNode9.right = treeNode11;
        treeNode10.right = treeNode12;
        System.out.println(getTreeHeight(treeNode1));

    }
    public static int getTreeHeight(TreeNode<Integer> root){
        Queue<TreeNode<Integer>> queue = new Queue<>();
        Queue<Integer> level = new Queue<>();
        queue.offer(root);
        level.offer(0);
        Integer tmp = 0;
        while (!queue.isEmpty()) {
            TreeNode<Integer> node = queue.poll();
            Integer levelIdx = level.poll();
            if (!tmp.equals(levelIdx)){
                tmp = levelIdx;
            }
            if (null != node.left){
                queue.offer(node.left);
                level.offer(tmp+1);
            }
            if (null != node.right){
                queue.offer(node.right);
                level.offer(tmp+1);
            }
        }
        return tmp;
    }

    public static class TreeNode<T>{
        T data;
        TreeNode<T> left;
        TreeNode<T> right;

        public TreeNode(T data) {
            this.data = data;
        }
    }

    public static class QueueNode<T>{
        T data;
        QueueNode<T> prev;
        QueueNode<T> next;

        public QueueNode(T data) {
            this(data,null,null);
        }

        public QueueNode(T data, QueueNode<T> prev, QueueNode<T> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
    }

    public static class Queue<T>{
        QueueNode<T> head;
        QueueNode<T> tail;
        int size;
        public T poll(){
            if (null == head)
                return null;
            QueueNode<T> t = head;
            if (this.head == this.tail){
                this.head = this.tail = null;
            }else
                this.head = t.next;
            --size;
            return t.data;
        }
        public void offer(T data){
            QueueNode<T> nNode = new QueueNode<>(data);
            if (null == head){
                this.head = nNode;
                this.tail = nNode;
            }else if (this.head == this.tail){
                this.tail = nNode;
                this.head.next = nNode;
                nNode.prev = this.head;
            }else {
                QueueNode<T> prevTail = this.tail;
                this.tail = nNode;
                prevTail.next = nNode;
                nNode.prev = prevTail;

            }
            ++size;
        }
        public boolean isEmpty(){
            return 0 == size;
        }
     }
}
