package com.secrething.learn.algorithm;

import lombok.Builder;

/**
 * Created by liuzz on 2019-04-08 21:40.
 */
public class BinaryTree {

    private Node<Integer> root;

    @Builder
    private static class Node<T>{

        private T data;
        private Node<T> left;
        private Node<T> right;
    }

    public void insert(Integer data){
        Node.NodeBuilder<Integer> builder = Node.builder();
        builder.data(data);
        Node<Integer> nNode = builder.build();

        if (null == root){
            root = nNode;
            return;
        }
        Node<Integer> node = root;
        int levelLeft = 0;
        int levelRight = 0;
        Node<Integer> preLeft = null;
        while (null != node){
            preLeft = node;
            node = node.left;
            levelLeft++;
        }
        node = root;
        Node<Integer> preRight = null;
        while (null != node){
            preRight = node;
            node = node.right;
            levelRight++;
        }
        if (levelRight < levelLeft){
            preRight.right = nNode;
        }else {
            preLeft.left = nNode;
        }

    }

    public static void main(String[] args) {
        BinaryTree t = new BinaryTree();
        for (int i = 0; i <10 ; i++) {
            t.insert(i);
        }
        System.out.println(t);
    }

}
