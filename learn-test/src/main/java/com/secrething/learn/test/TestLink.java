package com.secrething.learn.test;

/**
 * Created by liuzz on 2018/8/29 10:05 PM.
 * 能用栈解决的问题，都能用递归解决
 */
public class TestLink {


    public static void main(String[] args) {
        LinkedNode link = LinkedNode.buildLink(3, 6, 2, 9, 5, 1);
        //System.out.log(link);
        //convertOutLink(link.getHead());
        Node node = reverse(link.getHead());
        System.out.println(node);
    }

    /**
     * 递归倒序输出
     * @param node
     */
    public static void convertOutLink(Node node){
        if (null == node){
            return;
        }
        Node next = node.getNext();
        if (null != next){
            convertOutLink(next);
        }
        System.out.print(node.getData()+"\t");

    }

    /**
     * 反转链表
     * @param head
     * @return
     */
    public static Node reverse(Node head){
        if (null == head){
            return null;
        }
        if (null == head.getNext()){
            return head;
        }
        Node prev  = head;
        Node curr = head.getNext();
        prev.setNext(null);
        do {
            Node next = curr.getNext();
            curr.setNext(prev);
            prev = curr;
            curr = next;
        }while (curr != null);
        return prev;

    }
    /**
     *
     */
    public static class Node {
        Node next;
        Integer data;

        public Node(Integer data) {
            this.data = data;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public Integer getData() {
            return data;
        }

        public void setData(Integer data) {
            this.data = data;
        }
    }

    public static class LinkedNode {
        Node head;


        public static LinkedNode buildLink(Integer... datas) {
            if (datas.length == 0) {
                return null;
            }
            LinkedNode linkedNode = new LinkedNode();
            Node curr = null;
            for (int i = 0; i < datas.length; i++) {

                Integer data = datas[i];
                if (null == curr) {
                    curr = new Node(data);
                    linkedNode.setHead(curr);
                } else {
                    Node node = new Node(data);
                    curr.setNext(node);
                    curr = node;
                }
            }
            return linkedNode;
        }

        public Node getHead() {
            return head;
        }

        public void setHead(Node head) {
            this.head = head;
        }
    }
}
