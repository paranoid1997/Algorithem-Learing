package com.liu.LinkedList;

import java.util.Stack;

/**
 * 判断这个链表是不是回文链表
 * 思路:1.用快慢直指针找到链表的中心节点
 * 2.把后部分的链表逆置
 * 3.设置两个指针left和right，一个指向表头，一个指向表尾
 * 4.一次对应是否相等，如果相等，则为回文链表
 * 5.最后还需要把后半部分逆序的单链表调成正确的才能return
 */
public class IsPalindromeList {
    public static class Node {
        public int value;
        public Node next;

        public Node(int data) {
            this.value = data;
        }
    }
    public static boolean isPalindrome(Node head) {
        if (head == null || head.next == null) return true;
        Node slow = head;
        Node fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        //slow为中点,fast指向链表的尾部
        fast = slow.next;//找到需要逆转的后半部分链表
        slow.next = null;//断开
        Node next = null;
        while (fast != null) {
            next = fast.next;
            fast.next = slow;
            slow = fast;
            fast = next;
        }
        //此时slow指向单链表最后一个节点
        //n2 fast还是指向整个空
        fast = head;//fast指向第一个单链表的链首
        next = slow;//next指向后部分单链表的链尾
        //n3    n1
        boolean res = true;
        //fast指向单链表头节点，slow指针指向单链表尾节点
        while (fast != null && slow != null) {
            if (fast.value != slow.value) {
                res = false;
                break;
            }
            fast = fast.next;
            slow = slow.next;
        }
        //fast,slow均指向中点,
        // next指向整体链表的链尾
        slow = next.next;
        next.next = null;
        while (slow != null) {//恢复逆序单链表
            fast = slow.next;
            slow.next = next;
            next = slow;
            slow = fast;
        }
        return res;
    }
    public static boolean isPalindrome2(Node head) {
        Stack<Node> stack = new Stack<Node>();
        Node cur = head;
        while(cur != null) {
            stack.push(cur);
            cur = cur.next;
        }
        while (head != null) {
            if (head.value != stack.pop().value) {
                return false;
            }
            head = head.next;
        }
        return true;
    }
    public static void printLinkedList(Node node) {
        System.out.print("Linked List: ");
        while (node != null) {
            System.out.print(node.value + " ");
            node = node.next;
        }
        System.out.println();
    }
    public static void main(String[] args) {
        Node head = null;
        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(2);
        head.next.next.next.next = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindrome(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        printLinkedList(head);
        System.out.print(isPalindrome(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");
    }
}
