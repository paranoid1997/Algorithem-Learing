package com.liu.Tree;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 设计一个算法，可以将 N 叉树编码为二叉树，并能将该二叉树解码为原 N 叉树。
 * 一个 N 叉树是指每个节点都有不超过 N 个孩子节点的有根树。
 * 类似地，一个二叉树是指每个节点都有不超过 2 个孩子节点的有根树。
 * 你的编码 / 解码的算法的实现没有限制，你只需要保证一个 N 叉树可以编码为二叉树且该二叉树可以解码回原始 N 叉树即可。
 *
 * 例如，你可以将下面的 3-叉 树以该种方式编码：
 *
 * 注意，上面的方法仅仅是一个例子，可能可行也可能不可行。你没有必要遵循这种形式转化，你可以自己创造和实现不同的方法。
 *
 */
public class EncodeNaryTreeToBinaryTree {

    public static class Node {
        public int val;
        public List<Node> children;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    };

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
    // Encodes an n-ary tree to a binary tree.

    /**
     *  将N叉树转换为二叉树
     * 本质上就是左孩子右兄弟法
     */
    public static TreeNode encode(Node root) {
        if (root == null) return null;
        TreeNode head = new TreeNode(root.val);
        head.left = en(root.children);
        return head;
    }

    private static TreeNode en(List<Node> children) {
        TreeNode head = null;
        TreeNode cur = null;
        for (Node child : children) {
          TreeNode tNode = new TreeNode(child.val);
          if (head == null) {
              head = tNode;
          }else {
              cur.right = tNode;
          }
          cur = tNode;
          cur.left = en(child.children);
        }
        return head;
    }

    /**
     *把一颗二叉树还原成多叉树
     */
    public Node decode(TreeNode root) {
        if (root == null) return null;
        return new Node(root.val,de(root.left));
    }

    public List<Node> de(TreeNode root) {
        List<Node> children = new ArrayList<>();
        while (root != null) {
            Node cur = new Node(root.val, de(root.left));
            children.add(cur);
            root = root.right;
        }
        return children;
    }
}