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

    public static void main(String[] args) {
        RedBlackBST<Integer> brt = new RedBlackBST<>(((o1, o2) -> o2 - o1));

        for (int i = 0; i < 32 ; i++) {
            brt.insert(i);
        }
        System.out.println(brt);
    }

    public void insert(T t) {
        insert(root, t);
        root.red = false;
    }

    //插入节点
    private void insert(Node<T> parent, T t) {
        if (null == parent) {
            root = buildNode(t);
        } else {
            if (comparator.compare(parent.data, t) >= 0) {
                if (null != parent.right) {
                    insert(parent.right, t);
                } else {
                    parent.right = buildNode(t);
                    parent.right.parent = parent;
                    checkTree(parent.right);
                }
            } else {
                if (null != parent.left) {
                    insert(parent.left, t);
                } else {
                    parent.left = buildNode(t);
                    parent.left.parent = parent;
                    checkTree(parent.left);
                }

            }
        }

    }

    //重新调整树
    private void checkTree(Node<T> node) {
        Node<T> parent = node.parent;
        //父亲是红色,无需判断空指针，如果父节点为null,那么会作为根节点不会走到这一步
        if (null != parent && parent.red) {
            Node<T> uncle = node.uncle();
            //叔叔是红色
            if (null != uncle && uncle.red) {
                parent.red = uncle.red = false;
                node.grandParent().red = true;
                checkTree(node.grandParent());
            } else {//叔叔是黑色
                if (node == parent.left && parent == node.grandParent().left) {
                    parent.red = false;
                    node.grandParent().red = true;
                    rotateRight(node.parent);
                } else if (node == parent.right && parent == node.grandParent().right) {
                    parent.red = false;
                    node.grandParent().red = true;
                    rotateLeft(node.parent);
                } else if (node == parent.left && parent == node.grandParent().right) {
                    rotateLeft(node);
                    rotateRight(node);
                    node.red = false;
                    node.left.red = true;
                    node.right.red = true;
                } else if (node == parent.right && parent == node.grandParent().left) {
                    rotateRight(node);
                    rotateLeft(node);
                    node.red = false;
                    node.left.red = true;
                    node.right.red = true;
                }
            }

        }
        //如果父亲是黑的,对树没影响，不调整
    }

    //右旋
    private void rotateRight(Node<T> p) {
        if (p.parent == null) {
            root = p;
            return;
        }
        //祖父节点
        Node<T> gp = p.grandParent();
        //父节点
        Node<T> fa = p.parent;
        //右节点
        Node<T> y = p.right;
        //将右孩子给父节点作为父节点的左节点
        fa.left = y;
        if (null != y) {
            //右节点的父亲引用指向自己父亲
            y.parent = fa;
        }
        //将自己右节点指向父亲
        p.right = fa;
        //父亲的父节点指向自己
        fa.parent = p;
        //如果父节点是根，将根指向自己
        if (root == fa) {
            root = p;
        }
        //将父节点指向祖父节点
        p.parent = gp;
        if (gp != null) {
            //如果父亲节点的左节点是父节点，将祖父节点的左节点指向自己
            if (gp.left == fa) {
                gp.left = p;
            } else {//如果父亲节点的右节点是父节点，将祖父节点的右节点指向自己
                gp.right = p;
            }
        }


    }

    private void rotateLeft(Node<T> p) {
        if (p.parent == null) {
            root = p;
            return;
        }
        Node<T> gp = p.grandParent();
        Node<T> fa = p.parent;
        Node<T> y = p.left;

        fa.right = y;

        if (y != null) {
            y.parent = fa;
        }
        p.left = fa;
        fa.parent = p;

        if (root == fa)
            root = p;
        p.parent = gp;

        if (gp != null) {
            if (gp.left == fa)
                gp.left = p;
            else
                gp.right = p;
        }
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
