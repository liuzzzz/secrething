package com.secrething.learn.test;

import java.util.Comparator;

/**
 * Created by liuzz on 2018/9/14 8:46 PM.
 */
public class RedBlackBST<T> {
    private final Comparator<T> comparator;
    private Node<T> root;

    private int size;

    public RedBlackBST(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    private static <T> Node<T> buildNode(T t) {
        Node<T> node = new Node<>();
        node.data = t;
        return node;
    }

    //插入节点
    public void insert(Node<T> parent, T t) {
        Node<T> nwe = buildNode(t);
        if (null == parent) {
            root = nwe;
            this.root.red = false;
        } else {
            if (comparator.compare(parent.data, t) >= 0) {
                if (null != parent.right) {
                    insert(parent.right, t);
                } else {
                    parent.right = buildNode(t);
                    checkTree(parent.left);
                }
            } else {
                if (null != parent.left) {
                    insert(parent.left, t);
                } else {
                    parent.left = buildNode(t);
                    checkTree(parent.left);
                }

            }
        }

    }

    //重新调整树
    public void checkTree(Node<T> node) {
        Node<T> parent = node.parent;
        //父亲是红色
        if (parent.red) {
            Node<T> uncle = node.uncle();
            //叔叔是红色
            if (null != uncle && uncle.red) {
                parent.red = uncle.red = false;
                node.grandParent().red = true;
                checkTree(node.grandParent());


            }else {//叔叔是黑色

            }

        }
        //如果父亲是黑的,对树没影响，不调整
    }

    private static class Node<T> {
        T data;
        Node<T> parent;
        Node<T> left;
        Node<T> right;
        boolean red = true;

        //祖父
        Node<T> grandParent() {
            if (null == parent)
                return null;
            return parent.parent;
        }

        //叔叔
        Node<T> uncle() {
            Node<T> grant = grandParent();
            if (grant == null) {
                return null;
            } else {
                if (parent == grant.left) {
                    return grant.right;
                } else {
                    return grant.left;
                }
            }
        }

        //兄弟
        Node<T> sibling() {
            if (parent == null) {
                return null;
            }
            if (parent.left == this) {
                return parent.right;
            } else {
                return parent.left;
            }
        }
    }
}
