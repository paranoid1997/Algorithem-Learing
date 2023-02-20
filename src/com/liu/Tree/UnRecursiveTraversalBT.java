package com.liu.Tree;

import java.util.Stack;

public class UnRecursiveTraversalBT {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    /**
     * 非递归实现前序遍历 根左右
     * 根节点先入栈，然后在弹出
     * 右节点左节点依次入栈然后弹出
     * @param head
     */
    public static void preOrder(Node head) {
        System.out.print("pre-order: ");
        if (head != null) {
                Stack<Node> stack = new Stack<Node>();
                stack.push(head);
                while (!stack.isEmpty()) {
                    head = stack.pop();
                    System.out.print(head.value + " ");
                    if (head.right != null) stack.push(head.right);
                    if (head.left != null) stack.push(head.left);
                }
        }
        System.out.println();
    }

    /**
     * 用栈实现非递归中序遍历
     * 什么时候遍历结束？
     * 栈为空和cur指针均为空的时候
     * 一直递归到左边界结束才弹栈,然后把cur变成右孩子
     * @param cur
     */
    public static void inOrder(Node cur) {
        System.out.print("in-order: ");
        if (cur != null) {
            Stack<Node> stack = new Stack<Node>();
            while (!stack.isEmpty() || cur != null) {
                if (cur != null) {
                    stack.push(cur);
                    cur = cur.left;
                }else {
                    cur = stack.pop();
                    System.out.print(cur.value + " ");
                    cur = cur.right;
                }
            }
        }
        System.out.println();
    }

    /**
     * 用两个栈实现非递归后序遍历
     * 后序遍历是->左右根->逆序是根右左
     * 故一个栈实现根右左，然后弹出进另一个栈->此时是根右左
     * 在另一个弹出，即可完成左右根
     * @param head
     */
    public static void postOrderWithTwoStack(Node head) {
        System.out.print("post-order with two stack:");
        if (head != null) {
            Stack<Node> stack1 = new Stack<Node>();
            Stack<Node> stack2 = new Stack<Node>();
            stack1.push(head);
            while (!stack1.isEmpty()) {
                head = stack1.pop();
                stack2.push(head);
                if (head.left != null) stack1.push(head.left);
                if (head.right != null) stack1.push(head.right);
            }
            while (!stack2.isEmpty()) {
                System.out.print(stack2.pop().value + " ");
            }
        }
        System.out.println();
    }

    /**
     * 用一个栈实现非递归后序遍历
     * 牛逼大发了这个！！！
     * @param head
     */
    public static void postOrderWithOneStack(Node head) {
        System.out.print("postOrder with one stack:");
        if (head != null) {
            Stack<Node> stack = new Stack<Node>();
            stack.push(head);
            Node cur = null;
            while (!stack.isEmpty()) {
                cur = stack.peek();
                if (cur.left != null && head != cur.left && head != cur.right) {
                    stack.push(cur.left);
                }else if (cur.right != null && head != cur.right) {
                    stack.push(cur.right);
                }else {
                    System.out.print(stack.pop().value + " ");
                    head = cur;
                }
            }
        }
        System.out.println();
    }
    public static void main(String[] args) {
            Node head = new Node(1);
            head.left = new Node(2);
            head.right = new Node(3);
            head.left.left = new Node(4);
            head.left.right = new Node(5);
            head.right.left = new Node(6);
            head.right.right = new Node(7);

            preOrder(head);
            System.out.println("========");
            inOrder(head);
            System.out.println("========");
            postOrderWithOneStack(head);
            System.out.println("========");
            postOrderWithTwoStack(head);
            System.out.println("========");
        }
    }
