package com.liu.LinkedList;

/**
 * 给定两个可能有环也可能无环的单链表，头节点head1和head2。
 * 请实现一个函数，如果两个链表相交，请返回相交的第一个节点。如果不相交，返回null
 * 如果两个链表长度之和为N，时间复杂度请达到O(N)，额外空间复杂度 请达到O(1)。
 */
public class FindFirstIntersectNode {

    public static class Node {
        public int value;
        public Node next;

        public Node(int data) {
            this.value = data;
        }
    }
    public static Node getIntersectNode(Node head1,Node head2) {
        if (head1 == null || head2 == null) return null;
        Node loop1 = getLoopNode(head1);
        Node loop2 = getLoopNode(head2);
        if (loop1 == null && loop2 == null) {
            //如果两个都无环
            return noLoop(head1,head2);
        }
        if (loop1 != null && loop2 != null) {
            //如果两个都有环
            return bothLoop(head1,loop1,head2,loop2);
        }
        return null;//一个有环一个无环，不存在这种单链表
    }

    /**
     *找到链表第一个入环节点，如果无环，返回null
     * 思想:1.快慢指针:快指针一次走两步，慢指针一次走两步，一直走到它们相遇
     * 2.如果相遇，fast指针指向头，slow指针呆在相遇的原地，两个指针依次只走一步
     * 3.若再次相遇，则那个地方为第一个入环节点
     */
    public static Node getLoopNode(Node head) {
        if (head == null || head.next == null || head.next.next == null) {
            return null;
        }
        Node slow = head.next;
        Node fast = head.next.next;
        while (slow != fast) {
            if (fast.next == null || fast.next.next == null) {
                //如果快指针提前蹦到结束，则必无环
                return null;
            }
            fast = fast.next.next;
            slow = slow.next;
        }
        fast = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }

    /**
     * 如果两个链表都无环，
     * 返回第一个相交节点，如果不想交，返回null
     */
    public static Node noLoop(Node head1,Node head2) {
        if (head1 == null || head2 == null) {
            return null;
        }
        Node cur1 = head1;
        Node cur2 = head2;
        int n = 0;
        while (cur1.next != null) {
            n++;
            cur1 = cur1.next;
        }
        while (cur2.next != null) {
            n--;
            cur2 = cur2.next;
        }
        //执行到这里可以计算出两个链表相差的n值
        if (cur1 != cur2) {
            //若相交，则它们最后的内存地址是一样的
            //反之，则不相交
            return null;
        }
        cur1 = n > 0 ? head1 : head2;//谁长，谁的头变成cur1
        cur2 = cur1 == head1 ? head2 : head1;// 谁短，谁的头变成cur2
        n = Math.abs(n);
        while (n != 0) {
            n--;
            cur1 = cur1.next;
        }
        while (cur1 != cur2) {
            cur1 = cur1.next;
            cur2 = cur2.next;
        }
        return cur1;
    }

    /**
     *      两个有环链表，返回第一个相交节点，如果不想交返回null
     *      一共有三种情况
     *      1.loop1有环，loop2有环，但是它们两个并不相交
     *      2.loop1有环，loop2有环，它们相交于同一点
     *      3.loop1有环，loop2有环，它们相交于不是同一个点
     */
    public static Node bothLoop(Node head1,Node loop1,Node head2,Node loop2) {
            Node cur1 = null;
            Node cur2 = null;
            if (loop1 == loop2) {
                cur1 = head1;
                cur2 = head2;
                int n = 0;
                while (cur1 != loop1) {
                    n++;
                    cur1 = cur1.next;
                }
                while (cur2 != loop2) {
                    n--;
                    cur2 = cur2.next;
                }
                cur1 = n > 0 ? head1 : head2;
                cur2 = cur1 == head1 ? head2 : head1;
                n = Math.abs(n);
                while (n != 0) {
                    n--;
                    cur1 = cur1.next;
                }
                while (cur1 != cur2) {
                    cur1 = cur1.next;
                    cur2 = cur2.next;
                }
                return cur1;
            } else {
                cur1 = loop1.next;
                while (cur1 != loop1) {
                    if (cur1 == loop2) {
                        return loop1;//情况三
                    }
                    cur1 = cur1.next;
                }
                //loop1都走完了还没有遇到loop2,
                // 说明这两个并不相交
                return null;//情况一
            }
    }
    public static void main(String[] args) {
        // 1->2->3->4->5->6->7->null
        Node head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1.next.next.next = new Node(4);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(6);
        head1.next.next.next.next.next.next = new Node(7);

        // 0->9->8->6->7->null
        Node head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next.next.next.next.next; // 8->6
        System.out.println(getIntersectNode(head1, head2).value);

        // 1->2->3->4->5->6->7->4...
        head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1.next.next.next = new Node(4);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(6);
        head1.next.next.next.next.next.next = new Node(7);
        head1.next.next.next.next.next.next = head1.next.next.next; // 7->4

        // 0->9->8->2...
        head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next; // 8->2
        System.out.println(getIntersectNode(head1, head2).value);

        // 0->9->8->6->4->5->6..
        head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next.next.next.next.next; // 8->6
        System.out.println(getIntersectNode(head1, head2).value);
    }
}
