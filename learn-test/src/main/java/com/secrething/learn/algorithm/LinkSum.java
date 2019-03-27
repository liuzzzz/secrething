package com.secrething.learn.algorithm;

/**
 * Created by liuzz on 2019-03-26 20:14.
 */
public class LinkSum {
    /**
     * Definition for singly-linked list.
     * public class ListNode {
     * int val;
     * ListNode next;
     * ListNode(int x) { val = x; }
     * }
     */
    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    static class Solution {

        private static int getValue(ListNode node) {
            int num = 0;
            for (int i = 0;null != node;++i){

                num+= (int)Math.pow(10,i)*node.val;
                node = node.next;
            }
            return num;
        }
        private static ListNode num2List(int num){
            int h = num % 10;
            ListNode head = new ListNode(h);
            ListNode node = head;
            for(int n = num/10;n !=0;n = n/10){
                node.next = new ListNode(n%10);
                node = node.next;
            }



            return head;
        }
        public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
            int n1 = getValue(l1);
            int n2 = getValue(l2);
            int sum = n1 + n2;
            return num2List(sum);
        }
    }

    public static void main(String[] args) {
        ListNode node = new ListNode(1);
        node.next = new ListNode(5);
        node.next.next = new ListNode(3);
        System.out.println(Solution.addTwoNumbers(node,node));

    }
}
