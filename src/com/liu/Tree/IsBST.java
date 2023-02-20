package com.liu.Tree;

import java.util.ArrayList;

/**
 * 给定一棵二叉树的头节点head，
 * 返回这颗二叉树中是不是二叉搜索树
 */
public class IsBST {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }
    public static boolean isBST(Node head) {
        if (head == null) return true;
        return process(head).isBST;
    }
    public static class info {
        public boolean isBST;
        public int max;
        public int min;

        public info(boolean isBST, int max, int min) {
            this.isBST = isBST;
            this.max = max;
            this.min = min;
        }
    }

    /**
     *判断是否为二叉搜索树的四项原则
     * 1.X的左树是BST；
     * 2.X的右树是BST；
     * 3.X的左树的最大值max < x;
     * 4.X的右树的最小值min > x;
     */
    public static info process(Node x) {
        if (x == null) return null;//空树都没有值，不好设最小最大值
        info leftInfo = process(x.left);
        info rightInfo = process(x.right);
        int max = x.value;
        if (leftInfo != null) {
            max = Math.max(max,leftInfo.max);
        }
        if (rightInfo != null) {
            max = Math.max(max,rightInfo.max);
        }
        int min = x.value;
        if (leftInfo != null) {
            min = Math.min(min,leftInfo.min);
        }
        if (rightInfo != null) {
            min = Math.min(min,rightInfo.min);
        }
        boolean isBST = true;
        if (leftInfo != null && !leftInfo.isBST) {
            isBST = false;
        }
        if (rightInfo != null && !rightInfo.isBST) {
            isBST = false;
        }
        if (leftInfo != null && leftInfo.max >= x.value) {
            isBST = false;
        }
        if (rightInfo != null && rightInfo.min <= x.value) {
            isBST = false;
        }
        return new info(isBST, max, min);
    }

    /**
     * 用的Morris遍历解决的
     */
    public static boolean isBST2(Node head) {
        if (head == null) {
            return true;
        }
        Node cur = head;
        Node mostRight = null;
        Integer pre = null;
        boolean ans = true;
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) {
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) {
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    mostRight.right = null;
                }
            }
            if (pre != null && pre >= cur.value) {
                ans = false;
            }
            pre = cur.value;
            cur = cur.right;
        }
        return ans;
    }

    public static boolean isBST1(Node head) {
        if (head == null) {
            return true;
        }
        ArrayList<Node> arr = new ArrayList<>();
        in(head, arr);
        for (int i = 1; i < arr.size(); i++) {
            if (arr.get(i).value <= arr.get(i - 1).value) {
                return false;
            }
        }
        return true;
    }

    public static void in(Node head, ArrayList<Node> arr) {
        if (head == null) {
            return;
        }
        in(head.left, arr);
        arr.add(head);
        in(head.right, arr);
    }
    // for test
    public static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    // for test
    public static Node generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        Node head = new Node((int) (Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    public static void main(String[] args) {
        int maxLevel = 4;
        int maxValue = 100;
        int testTimes = 10000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (isBST1(head) != isBST(head) || isBST(head) != isBST2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("测试结束!");
    }
}
