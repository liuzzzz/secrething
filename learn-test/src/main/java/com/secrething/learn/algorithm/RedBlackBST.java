package com.secrething.learn.algorithm;

import com.secrething.common.contants.ConsoleColor;
import com.secrething.common.util.Out;

import java.util.Comparator;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by liuzz on 2018/9/14 8:46 PM.
 */
public class RedBlackBST<T> {
    private static final boolean BLACK = false;
    private static final boolean RED = true;
    static int serchCount = 0;
    private final Comparator<T> comparator;
    private Node<T> root;
    private int size;

    private Node<T> head;
    private Node<T> tail;

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

        for (int i = 0; i < 32; i++) {
            brt.insert(i);
        }
        brt.traversing();
        brt.delete(8);
        System.out.println("-------------------");
        brt.traversing();

        /*long begin = System.currentTimeMillis();
        brt.search(brt.root,9999999);
        System.out.println(System.currentTimeMillis() - begin);
        System.out.println(serchCount);*/
    }

    private static boolean colorOf(Node node) {
        return null == node ? BLACK : node.color;
    }

    private static <T> Node<T> leftOf(Node<T> node) {
        return null != node ? node.left : null;
    }

    private static <T> Node<T> rightOf(Node<T> node) {
        return null != node ? node.right : null;
    }

    private static <T> Node<T> parentOf(Node<T> node) {
        return null != node ? node.parent : null;
    }

    private static <T> Node<T> grandParentOf(Node<T> node) {
        Node<T> p = parentOf(node);
        return null != p ? parentOf(p) : null;
    }

    private static void setColor(Node node, boolean c) {
        if (null != node) {
            node.color = c;
        }
    }

    public void insert(T t) {
        if (null == t)
            return;
        insert(root, t);
        root.color = BLACK;
        size++;
    }

    public int size() {
        return size;
    }

    public void traversing() {

        Queue<Node<T>> queue = new LinkedBlockingQueue<>();
        queue.add(root);
        Node<T> prev = null;
        while (!queue.isEmpty()) {
            Node<T> node = queue.poll();
            if (null != prev && comparator.compare(prev.data, node.data) < 0) {
                System.out.println();
            }
            if (node.color == RED) {
                Out.print(node.data, ConsoleColor.RED);
            } else {
                Out.print(node.data);
            }
            Out.print("\t");
            if (null != leftOf(node))
                queue.add(node.left);
            if (null != rightOf(node))
                queue.add(node.right);
            prev = node;
        }
    }

    private Node<T> search(Node<T> p, T t) {
        if (null != p) {
            serchCount++;
            if (comparator.compare(p.data, t) == 0) {
                System.out.println("searched");
                return p;
                //要继续往下找么?
                /*if (null != p.right) {
                    search(p.right, t);
                }*/
            } else if (comparator.compare(p.data, t) > 0) {
                if (null != p.right) {
                    return search(p.right, t);
                } else {
                    return null;
                }
            } else if (comparator.compare(p.data, t) < 0) {
                if (null != p.left) {
                    return search(p.left, t);
                } else {
                    return null;
                }

            }
        }
        return null;
    }

    //插入节点
    private void insert(Node<T> parent, T t) {
        if (null == parent) {
            root = buildNode(t);
            head = tail = root;
            head.next = tail;
            tail.prev = head;
        } else {
            if (comparator.compare(parent.data, t) >= 0) {
                if (null != parent.right) {
                    insert(parent.right, t);
                } else {
                    Node<T> newNode = buildNode(t);
                    parent.right = newNode;
                    Node<T> prevTail = tail;
                    tail = newNode;
                    prevTail.next = tail;
                    tail.prev = prevTail;
                    parent.right.parent = parent;
                    fixUpAfterInsertion(parent.right);
                }
            } else {
                if (null != parent.left) {
                    insert(parent.left, t);
                } else {
                    Node<T> newNode = buildNode(t);
                    parent.left = newNode;
                    Node<T> prevTail = tail;
                    tail = newNode;
                    prevTail.next = tail;
                    tail.prev = prevTail;
                    parent.left.parent = parent;
                    fixUpAfterInsertion(parent.left);
                }

            }
        }

    }

    //重新调整树
    private void fixUpAfterInsertion(Node<T> nd) {
        Node<T> node = nd;
        //父亲是红色,无需判断空指针，如果父节点为null,那么会作为根节点不会走到这一步
        Node<T> parent = parentOf(node);
        while (null != parent && (colorOf(parent) == RED)) {
            Node<T> uncle = node.uncle();
            //叔叔是红色
            if (null != uncle && (colorOf(uncle) == RED)) {
                parent.color = uncle.color = BLACK;
                node.grandParent().color = RED;
                node = grandParentOf(node);
                parent = parentOf(node);
            } else { //叔叔是黑色
                if (node == leftOf(parent) && parent == leftOf(node.grandParent())) {
                    setColor(parent, BLACK);
                    setColor(node.grandParent(), RED);
                    rotateRight(parentOf(node));
                } else if (node == rightOf(parent) && parent == rightOf(node.grandParent())) {
                    setColor(parent, BLACK);
                    setColor(grandParentOf(node), RED);
                    rotateLeft(parent);
                } else if (node == leftOf(parent) && parent == rightOf(node.grandParent())) {
                    rotateLeft(node);
                    rotateRight(node);
                    setColor(node, BLACK);
                    setColor(leftOf(node), RED);
                    setColor(rightOf(node), RED);
                } else if (node == rightOf(parent) && parent == leftOf(node.grandParent())) {
                    rotateRight(node);
                    rotateLeft(node);
                    setColor(node, BLACK);
                    setColor(leftOf(node), RED);
                    setColor(rightOf(node), RED);
                }
                break;
            }

        }
        //如果父亲是黑的,对树没影响，不调整
    }

    //右旋 右上方
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

    //左上方左旋
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

    public Node<T> delete(T data) {
        Node<T> node = search(root, data);
        if (null != node) {
            deleteNode(node);
            if (node == tail) {
                if (null != node.prev) {
                    node.prev.next = null;
                }
                tail = node.prev;
            } else if (node == head) {
                head = node.next;
                head.prev = null;
            } else {
                Node<T> prev = node.prev;
                Node<T> next = node.next;
                prev.next = next;
                next.prev = prev;
            }
        }
        return node;
    }

    private void deleteNode(Node<T> node) {
        if (node == root && leftOf(node) == null && rightOf(node) == null) {
            root = null;
        } else {
            //被删除节点有两个孩子,在右孩子的左分支最底端找接替者,找到直接将接替者的值给被删除节点,接替者进入检查逻辑
            if (leftOf(node) != null && rightOf(node) != null) {
                Node<T> s = rightOf(node);
                while (null != leftOf(s)) {
                    s = leftOf(s);
                }
                node.data = s.data;
                node = s;
            }
            Node<T> child = leftOf(node) == null ? rightOf(node) : leftOf(node);
            //没有孩子节点
            if (null == child) {
                //被删除节点是黑色
                if (colorOf(node) == BLACK) {
                    fixUpAfterDeletion(node);
                }
                if (node == leftOf(parentOf(node))) {
                    parentOf(node).left = null;
                } else {
                    parentOf(node).right = null;
                }
                node.parent = null;
            } else { //有一个孩子节点
                child.parent = node.parent;
                if (node.parent == null) {
                    root = child;
                } else if (node == leftOf(parentOf(node))) {
                    parentOf(node).left = child;
                } else {
                    parentOf(node).right = child;
                }
                node.left = node.right = node.parent = null;
                //被删除节点是黑色 注意:被删除节点有一个孩子节点的情况,则被删除节点不可能是红色
                if (colorOf(node) == BLACK) {
                    fixUpAfterDeletion(child);
                }

            }
        }

    }

    //删除时 只讨论 被删除节点无子节点或只有一个子节点的情况, 如若被删除节点有两个子节点,会迭代向下寻找接替者,以接替者为调整节点
    //
    private void fixUpAfterDeletion(Node<T> x) {

        while (x != root && colorOf(x) == BLACK) {
            if (x == leftOf(parentOf(x))) {
                Node<T> sib = rightOf(parentOf(x));

                //兄弟节点是红色,一顿操作变黑色
                if (colorOf(sib) == RED) {
                    setColor(sib, BLACK);
                    setColor(parentOf(x), RED);
                    rotateLeft(rightOf(parentOf(x)));
                    sib = rightOf(parentOf(x));
                }
                //兄弟节点的左右孩子都是黑色,一顿操作,父亲接力向上递归(只有这种情况需要递归)
                if (colorOf(leftOf(sib)) == BLACK &&
                        colorOf(rightOf(sib)) == BLACK) {
                    setColor(sib, RED);
                    x = parentOf(x);
                } else {
                    //兄弟节点的右孩子是黑色,左孩子是一定是红色 一顿操作,将兄弟旋转着色为红色
                    if (colorOf(rightOf(sib)) == BLACK) {
                        setColor(leftOf(sib), BLACK);
                        setColor(sib, RED);
                        rotateRight(leftOf(sib));
                        sib = rightOf(parentOf(x));
                    }
                    setColor(sib, colorOf(parentOf(x)));
                    setColor(parentOf(x), BLACK);
                    setColor(rightOf(sib), BLACK);
                    rotateLeft(rightOf(parentOf(x)));
                    x = root;
                }
            } else {
                //以上操作 左右方向相反,仅此而已
                Node<T> sib = leftOf(parentOf(x));

                if (colorOf(sib) == RED) {
                    setColor(sib, BLACK);
                    setColor(parentOf(x), RED);
                    rotateRight(leftOf(parentOf(x)));
                    sib = leftOf(parentOf(x));
                }

                if (colorOf(rightOf(sib)) == BLACK &&
                        colorOf(leftOf(sib)) == BLACK) {
                    setColor(sib, RED);
                    x = parentOf(x);
                } else {
                    if (colorOf(leftOf(sib)) == BLACK) {
                        setColor(rightOf(sib), BLACK);
                        setColor(sib, RED);
                        rotateLeft(rightOf(sib));
                        sib = leftOf(parentOf(x));
                    }
                    setColor(sib, colorOf(parentOf(x)));
                    setColor(parentOf(x), BLACK);
                    setColor(leftOf(sib), BLACK);
                    rotateRight(leftOf(parentOf(x)));
                    x = root;
                }
            }
        }

        setColor(x, BLACK);
    }

    private static class Node<T> {
        T data;
        Node<T> parent;
        Node<T> left;
        Node<T> right;
        boolean color = RED;
        Node<T> next;
        Node<T> prev;

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
