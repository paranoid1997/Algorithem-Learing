package com.liu.Tree;

import java.util.LinkedList;

/**
 * 给定一棵二叉树的头节点head，
 * 返回这颗二叉树中是不是完全二叉树
 */
public class IsCBT {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    /**
     *运用二叉树递归模板套路解题
     */
    public static boolean isCBT(Node head) {
        if (head == null) return true;
        return process(head).isCBT;
    }
    public static class info {
        public boolean isFull;
        public boolean isCBT;
        public int height;

        public info(boolean isFull, boolean isCBT, int height) {
            this.isFull = isFull;
            this.isCBT = isCBT;
            this.height = height;
        }
    }
    public static info process(Node x) {
        if (x == null) {
            return new info(true,true,0);
        }
        info leftInfo = process(x.left);
        info rightInfo = process(x.right);

        int height = Math.max(leftInfo.height,rightInfo.height) + 1;
        //怎么定义一颗树的子树为满树？
        //左子树是满树，右子树是满的，且左右子树高度一致
        //故认为这颗树的子树是满的
        boolean isFull = leftInfo.isFull &&
                         rightInfo.isFull &&
                         leftInfo.height == rightInfo.height;
        boolean isCBT = false;
        if (isFull) {
            isCBT = true;
        }else {//以x为头的整棵树不满
            if (leftInfo.isCBT && rightInfo.isCBT) {
                if (leftInfo.isCBT && rightInfo.isFull && leftInfo.height == rightInfo.height + 1) {
                    isCBT = true;
                }
                if (leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height + 1) {
                    isCBT = true;
                }
                if (leftInfo.isFull && rightInfo.isCBT && leftInfo.height == rightInfo.height) {
                    isCBT = true;
                }
            }
        }
        return new info(isFull,isCBT,height);
    }

    /**
     * 1.如果遇到一个节点，它没有左孩子只有有孩子，必然不是完全二叉树
     * 2.在满足1的基础上，如果遇到不双全的节点,则以后的节点都必须是叶子节点
     */
    public static boolean isCBT1(Node head) {
        if (head == null) return true;
        LinkedList<Node> queue = new LinkedList<>();
        boolean leaf = false;
        Node left = null;
        Node right = null;
        queue.add(head);
        while (!queue.isEmpty()) {
            head = queue.poll();
            left = head.left;
            right = head.right;
            if ((leaf &&(left != null || right != null)) || (left == null && right != null)) {
                //情况一与情况二都包含了
                return false;
            }
            if (left != null) {
                queue.add(left);
            }
            if (right != null) {
                queue.add(right);
            }
            if (left == null || right == null) {
                leaf = true;
            }
        }
        return true;
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
        int maxLevel = 5;
        int maxValue = 100;
        int testTimes = 10000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (isCBT1(head) != isCBT(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("测试结束!");
    }
}
