package com.liu.Tree;

/**
 * 判断二叉树是否是满二叉树
 * 思路:只需要判断2^h - 1 = Node?
 */
public class IsFull {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    /**
     *    二叉树的模板解题
     *    第一种方法
     * 	 收集整棵树的高度h，和节点数n
     * 	只有满二叉树满足 : 2 ^ h - 1 == n
     */
    public static boolean isFull(Node head) {
        if (head == null) return true;
        info all = process(head);//用all接住递归结构体中的所有的内容
        return Math.pow(2,all.height) - 1 == all.nodes;
    }
    public static class info {
        public int height;
        public int nodes;

        public info(int height, int nodes) {
            this.height = height;
            this.nodes = nodes;
        }
    }
    public static info process(Node x) {
        if (x == null) {
            return new info(0,0);
        }
        info leftInfo = process(x.left);
        info rightInfo = process(x.right);
        int height = Math.max(leftInfo.height,rightInfo.height) + 1;
        int nodes = leftInfo.nodes + rightInfo.nodes + 1;
        return new info(height,nodes);
    }
    // 第二种方法
    // 收集子树是否是满二叉树
    // 收集子树的高度
    // 左树满 && 右树满 && 左右树高度一样 -> 整棵树是满的
    public static boolean isFull2(Node head) {
        if (head == null) {
            return true;
        }
        return process2(head).isFull;
    }

    public static class Info2 {
        public boolean isFull;
        public int height;

        public Info2(boolean f, int h) {
            isFull = f;
            height = h;
        }
    }

    public static Info2 process2(Node h) {
        if (h == null) {
            return new Info2(true, 0);
        }
        Info2 leftInfo = process2(h.left);
        Info2 rightInfo = process2(h.right);
        boolean isFull = leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height;
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        return new Info2(isFull, height);
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
            if (isFull(head) != isFull2(head)) {
                System.out.println("出错了!");
            }
        }
        System.out.println("测试结束");
    }
}
