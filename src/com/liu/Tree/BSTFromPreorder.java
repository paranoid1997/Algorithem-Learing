package com.liu.Tree;

public class BSTFromPreorder {
    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }

        public TreeNode(int val) {
            this.val = val;
        }

        public TreeNode bstFromPreorder(int[] preorder) {
            if (preorder == null || preorder.length == 0) {
                return null;
            }
            return process(preorder,0,preorder.length - 1);
        }

        private TreeNode process(int[] pre, int left, int right) {
            if (left > right) return null;
            int index = left + 1;
            for (; index <= right; index++) {
                if (pre[index] > pre[left]) {
                    break;
                }
            }
            TreeNode head = new TreeNode(pre[left]);
            head.left = process(pre,left + 1,index - 1);
            head.right = process(pre,index,right);
            return head;
        }

    }
}
