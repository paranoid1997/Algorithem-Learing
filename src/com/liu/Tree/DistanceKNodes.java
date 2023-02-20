package com.liu.Tree;

import java.util.*;

/**
 * 给定三个参数:
 * 二叉树的头节点head,树上某个节点 target,正数K
 * 从 target开始，可以向上走或者向下走
 * 返回与 target的距离是K的所有节点
 */
public class DistanceKNodes {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    public static List<Node> distanceKNodes(Node root,Node target,int k) {
        HashMap<Node, Node> parent = new HashMap<>();
        parent.put(root,null);
        parentMap(root,parent);
        List<Node> ans = new ArrayList<>();
        Queue<Node> queue = new LinkedList<>();
        HashSet<Node> visited = new HashSet<>();
        int curlevel = 0;
        queue.add(target);
        visited.add(target);
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                Node cur = queue.poll();
                if (curlevel == k) {
                    ans.add(cur);
                }
                if (cur.left != null && !visited.contains(cur.left)) {
                    visited.add(cur.left);
                    queue.add(cur.left);
                }
                if (cur.right != null && !visited.contains(cur.right)) {
                    visited.add(cur.right);
                    queue.add(cur.right);
                }
                if (parent.get(cur) != null && !visited.contains(parent.get(cur))) {
                    visited.add(parent.get(cur));
                    queue.add(parent.get(cur));
                }
            }
            curlevel++;
            if (curlevel > k) break;
        }

        return ans;
    }

    private static void parentMap(Node cur, HashMap<Node, Node> parent) {
        if (cur == null) {
            return;
        }
        if (cur.left != null) {
            parent.put(cur.left,cur);
            parentMap(cur.left,parent);
        }
        if (cur.right != null);
        parent.put(cur.right,cur);
        parentMap(cur.right,parent);
    }

    public static void main(String[] args) {
        Node n0 = new Node(0);
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n4 = new Node(4);
        Node n5 = new Node(5);
        Node n6 = new Node(6);
        Node n7 = new Node(7);
        Node n8 = new Node(8);

        n3.left = n5;
        n3.right = n1;
        n5.left = n6;
        n5.right = n2;
        n1.left = n0;
        n1.right = n8;
        n2.left = n7;
        n2.right = n4;

        Node root = n3;
        Node target = n5;
        int K = 2;

        List<Node> ans = distanceKNodes(root, target, K);
        for (Node o1 : ans) {
            System.out.println(o1.value);
        }
    }
}
