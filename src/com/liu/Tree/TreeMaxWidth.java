package com.liu.Tree;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class TreeMaxWidth {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    /**
     *用容器做，需要额外辅助空间O(N)
     * 辣鸡做法，和面试官聊这个做法一般没分
     */
    public static int maxWidthUseMap(Node head) {
        if (head == null) return 0;
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        HashMap<Node, Integer> levelMap = new HashMap<>();
        levelMap.put(head,1);//head在第一层
        int curLevel = 1;
        int curLevelNodes = 0;//当前层目前的节点数
        int max = 0;
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            int curNodesLevel = levelMap.get(cur);//当前节点所在的层
            if (cur.left != null) {
                levelMap.put(cur.left,curNodesLevel + 1);
                queue.add(cur.left);
            }
            if (cur.right != null) {
                levelMap.put(cur.right,curNodesLevel + 1);
                queue.add(cur.right);
            }
            if (curNodesLevel == curLevel) {
                curLevelNodes++;
            }else {
                max = Math.max(max,curLevelNodes);
                curLevel++;
                curLevelNodes = 1;
            }
        }
        max = Math.max(max, curLevelNodes);
        return max;
    }

    /**
     *用有限几个变量实现树的最大的宽度
     * 额外空间复杂度为O(1)为最优解
     */
    public static int maxWidthNoMap(Node head) {
        if (head == null) return 0;
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        Node curEnd = head;//当前层，最右节点是谁
        Node nextEnd = null;//下一层，最右节点是谁
        int max = 0;
        int curLevelNodes = 0;
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            if (cur.left != null) {
                queue.add(cur.left);
                nextEnd = cur.left;
            }
            if (cur.right != null) {
                queue.add(cur.right);
                nextEnd = cur.right;
            }
            curLevelNodes++;
            if (cur == curEnd) {
                max = Math.max(max,curLevelNodes);
                curLevelNodes = 0;
                curEnd = nextEnd;
                nextEnd = null;
            }
        }
        return max;
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
        int maxLevel = 10;
        int maxValue = 100;
        int testTimes = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (maxWidthUseMap(head) != maxWidthNoMap(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("测试结束!");
    }
}
