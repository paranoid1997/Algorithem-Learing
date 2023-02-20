package com.liu.Morris;

public class MorrisTraversal {

    public static class Node {
        public int value;
        Node left;
        Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    /**
     * Morris遍历
     * 特点:任何节点只要有左树会来到两次
     * 实现遍历二叉树空间复杂度O(1)
     */
    public static void morris(Node head) {
        if (head == null) return;
        Node cur = head;
        Node mostRight = null;//左子树最右的孩子节点
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
                }else {
                    //mostRight.right == cur
                    mostRight.right = null;
                }
            }
            cur = cur.right;
        }
    }

    /**
     * Morris遍历该先序遍历
     * 1)如果二叉树无左子树，刚到它自己这个节点就需要打印
     * 2）如果二叉树有左子树，第一次到达就打印
     * 实现遍历二叉树空间复杂度O(1)
     */
    public static void morrisPre(Node head) {
        if (head == null) return;
        Node cur = head;
        Node mostRight = null;//左子树最右的孩子节点
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) {//有左子树
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) {
                    //有左子树，第一次到达就打印
                System.out.print(cur.value + " ");
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                }else {
                    //mostRight.right == cur
                    mostRight.right = null;
                }
            }else {
                System.out.print(cur.value + " ");
            }
            cur = cur.right;
        }
        System.out.println();
    }
    /**
     * Morris遍历该中序遍历
     * 1)如果二叉树无左子树，刚到它自己这个节点就需要打印
     * 2）如果二叉树有左子树，打印第二次
     * 实现遍历二叉树空间复杂度O(1)
     */
    public static void morrisIn(Node head) {
        if (head == null) return;
        Node cur = head;
        Node mostRight = null;//左子树最右的孩子节点
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) {//有左子树
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) {
                    //有左子树，第一次到达就打印
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                }else {
                    //mostRight.right == cur
                    mostRight.right = null;
                }
            }
            //打印第二次
            System.out.print(cur.value + " ");
            cur = cur.right;
        }
        System.out.println();
    }

    /**
     * Morris遍历该后序遍历
     * 1)找到会打印两次的节点的位置
     * 2)逆序的打印找到节点左树的右边界
     * 3)Morris跑完以后单独的打印整棵树左树的右边界
     */
    public static void morrisPos(Node head) {
        if (head == null) return;
        Node cur = head;
        Node mostRight = null;//左子树最右的孩子节点
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
                }else {
                    //mostRight.right == cur
                    mostRight.right = null;
                    //只有真正到达节点两次的时候
                    //逆序的打印左树的右边界
                    printEdge(cur.left);
                }
            }
            cur = cur.right;
        }
        //整棵树都弄完了,打印整棵树的右边界
        printEdge(head);
        System.out.println();
    }

    /**
     * 先链表反转，打印节点
     * 反转完了以后再反转回去
     */
    public static void printEdge(Node head) {
        Node tail = reverseEdge(head);
        Node cur = tail;
        while (cur != null) {
            System.out.print(cur.value + " ");
            cur = cur.right;
        }
        reverseEdge(tail);
    }
    public static Node reverseEdge(Node head) {
        Node pre = null;
        Node next = null;
        while (head != null) {
            next = head.right;
            head.right = pre;
            pre = head;
            head = next;
        }
        return pre;
    }


    public static void main(String[] args) {
        Node head = new Node(4);
        head.left = new Node(2);
        head.right = new Node(6);
        head.left.left = new Node(1);
        head.left.right = new Node(3);
        head.right.left = new Node(5);
        head.right.right = new Node(7);
        morrisPre(head);
        morrisIn(head);
        morrisPos(head);
    }
}
