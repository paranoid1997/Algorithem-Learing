package com.liu.Tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * 给定一棵二叉树的头节点head，和另外两个节点a和b。
 * 返回a和b的最低公共祖先
 */
public class LowestAncestor {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }
    public static Node lowestAncestor(Node head, Node a, Node b) {
        if (head == null) return null;
        return process(head,a,b).ans;
    }
    public static class info {
        public boolean findA;
        public boolean findB;
        public Node ans;

        public info(boolean findA, boolean findB, Node ans) {
            this.findA = findA;
            this.findB = findB;
            this.ans = ans;
        }
    }
    /**
     *递归分析:
     * 1.若是以x为头节点
     * 1)若在x的左子树找到A，在x的右子树找到B
     * 则x为A，B的最低公共祖先
     * 2若是不以x为头节点
     * 可能在左子树找到ans,也可能在右子树找到ans
     */
    public static info process(Node x, Node a, Node b) {
        if (x == null) return new info(false,false,null);
        info leftInfo = process(x.left,a,b);
        info rightInfo = process(x.right,a,b);
        boolean findA = (x == a) || leftInfo.findA || rightInfo.findA;
        boolean findB = (x == b) || leftInfo.findB || rightInfo.findB;
        Node ans = null;
        if (leftInfo.ans != null) {
            //不以x为头节点
            ans = leftInfo.ans;
        }else if (rightInfo.ans != null) {
            ans = rightInfo.ans;
        }else {
            //以x为头节点
            if (findA && findB) {
                ans = x;
            }
        }
        return new info(findA,findB,ans);
    }
    public static Node lowestAncestor1(Node head,Node o1, Node o2) {
        if (head == null) return null;
        HashMap<Node, Node> parentMap = new HashMap<>();
        parentMap.put(head,null);//记录所有父节点
        fillParentMap(head,parentMap);
        HashSet<Node> o1Set = new HashSet<>();
        Node cur = o1;
        o1Set.add(cur);
        while (parentMap.get(cur) != null) {
            cur = parentMap.get(cur);
            o1Set.add(cur);
        }
        cur = o2;
        while (!o1Set.contains(cur)) {
            cur = parentMap.get(cur);
        }
        return cur;
    }
    public static void fillParentMap(Node head,HashMap<Node,Node> parentMap) {
        if (head.left != null) {
            parentMap.put(head.left,head);
            fillParentMap(head.left,parentMap);
        }
        if (head.right != null) {
            parentMap.put(head.right,head);
            fillParentMap(head.right,parentMap);
        }
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

    // for test
    public static Node pickRandomOne(Node head) {
        if (head == null) {
            return null;
        }
        ArrayList<Node> arr = new ArrayList<>();
        fillPrelist(head, arr);
        int randomIndex = (int) (Math.random() * arr.size());
        return arr.get(randomIndex);
    }

    // for test
    public static void fillPrelist(Node head, ArrayList<Node> arr) {
        if (head == null) {
            return;
        }
        arr.add(head);
        fillPrelist(head.left, arr);
        fillPrelist(head.right, arr);
    }
    public static void main(String[] args) {
        int maxLevel = 4;
        int maxValue = 100;
        int testTimes = 10000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            Node o1 = pickRandomOne(head);
            Node o2 = pickRandomOne(head);
            if (lowestAncestor1(head, o1, o2) != lowestAncestor(head, o1, o2)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("测试结束!");
    }
}
