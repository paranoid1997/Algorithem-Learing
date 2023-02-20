package com.liu.Tree;

import java.util.ArrayList;

/**
 * 给定一棵二叉树的头节点head，
 * 返回二叉搜索子树的最大值
 */
public class MaxSubBSTSize {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    public static int maxSubBSTSize(Node head) {
        if (head == null) return 0;
        return process(head).maxBSTSubTreeSize;
    }
    public static class info {
        public int maxBSTSubTreeSize;
        public int allSize;
        public int max;
        public int min;

        public info(int maxBSTSubTreeSize, int allSize, int max, int min) {
            this.maxBSTSubTreeSize = maxBSTSubTreeSize;
            this.allSize = allSize;
            this.max = max;
            this.min = min;
        }
    }

    /**
     * 递归分析
     * 1.x不做头
     * 1)x的左maxBSTSubTreeSize
     * 2)x的右maxBSTSubTreeSize
     * 2.x做头
     * 1)x左右子树是否为BST
     * 2)x的左max < x.val,x的右min > max
     * 3)x的左右size
     */
    public static info process(Node x) {
        if (x == null) return null;
        info leftInfo = process(x.left);
        info rightInfo = process(x.right);
        int max = x.value;
        int min = x.value;
        int allSize = 1;
        if (leftInfo != null) {
            max = Math.max(leftInfo.max,max);
            min = Math.min(leftInfo.min,min);
            allSize += leftInfo.allSize;
        }
        if (rightInfo != null) {
            max = Math.max(rightInfo.max,max);
            min = Math.min(rightInfo.min,min);
            allSize += rightInfo.allSize;
        }
        //x不做头
        int p1 = -1;
        if (leftInfo != null) {
            p1 = leftInfo.maxBSTSubTreeSize;
        }
        int p2 = -1;
        if (rightInfo != null) {
            p2 = rightInfo.maxBSTSubTreeSize;
        }
        int p3 = -1;
        //如果x为头节点，如果leftInfo.maxBSTSubTreeSize == leftInfo.allSize
        //就表示整个树为搜索二叉树，反之就不是搜索二叉树
        boolean leftBST = leftInfo == null ? true :(leftInfo.maxBSTSubTreeSize == leftInfo.allSize);
        boolean rightBST = rightInfo == null ? true :(rightInfo.maxBSTSubTreeSize == rightInfo.allSize);
        if (leftBST && rightBST) {
            boolean leftMaxLessX = leftInfo == null ? true : (leftInfo.max < x.value);
            boolean rightMinMoreX = rightInfo == null ? true : (x.value < rightInfo.min);
            if (leftMaxLessX && rightMinMoreX) {
                int leftSize = leftInfo == null ? 0 : leftInfo.allSize;
                int rightSize = rightInfo == null ? 0 : rightInfo.allSize;
                p3 = leftSize + rightSize + 1;
            }
        }
        return new info(Math.max(p1, Math.max(p2, p3)), allSize, max, min);
    }

    public static int getBSTSize(Node head) {
        if (head == null) {
            return 0;
        }
        ArrayList<Node> arr = new ArrayList<>();
        in(head, arr);
        for (int i = 1; i < arr.size(); i++) {
            if (arr.get(i).value <= arr.get(i - 1).value) {
                return 0;
            }
        }
        return arr.size();
    }

    public static void in(Node head, ArrayList<Node> arr) {
        if (head == null) {
            return;
        }
        in(head.left, arr);
        arr.add(head);
        in(head.right, arr);
    }

    public static int maxSubBSTSize1(Node head) {
        if (head == null) {
            return 0;
        }
        int h = getBSTSize(head);
        if (h != 0) {
            return h;
        }
        return Math.max(maxSubBSTSize1(head.left), maxSubBSTSize1(head.right));
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
            if (maxSubBSTSize1(head) != maxSubBSTSize(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("测试结束!");
    }
}
