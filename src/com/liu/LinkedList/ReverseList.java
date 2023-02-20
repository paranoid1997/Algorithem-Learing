package com.liu.LinkedList;
import java.util.ArrayList;
import java.util.List;

public class ReverseList {
    /**4
     * 定义单链表结构
     */
    public static class Node {
        public int value;
        public Node next;

        public Node(int data) {
            this.value = data;
        }
    }

    /**
     * 定义双链表的结构
     */
    public static class DoubleNode {
        public int value;
        public DoubleNode last;//表示前驱指针
        public DoubleNode next;

        public DoubleNode(int value) {
            this.value = value;
        }
    }

    /**
     * 逆转单链表
     * @param head
     * @return
     */
    public static Node reverseLinkedList(Node head) {
        Node pre = null;
        Node next = null;
        while (head != null) {
            next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;
    }

    /**
     * 逆转双链表
     * @param head
     * @return
     */
    public static DoubleNode reverseDoubleList(DoubleNode head) {
        DoubleNode pre = null;
        DoubleNode next = null;
        while (head != null) {
            next = head.next;
            //head往后指的指针指向前面
            //head往前指的指针指向了后面
            head.next = pre;
            head.last = next;
            pre = head;
            head = next;
        }
        return pre;
    }

    /**
     * 删除链表中给定的值
     * @param head
     * @param num
     * @return
     */
    public static Node removeValue(Node head,int num) {
        //边界条件
        //如果一上来要删除的元素就是头节点
        while (head != null) {
            if (head.value != num) {
                break;
            }
            head = head.next;
        }
        Node pre = head;
        Node cur = head;
        while ( cur != null) {
            if (cur.value == num) {
                pre.next = cur.next;
            }else{
                pre = cur;
            }
            cur = cur.next;
        }
        return head;
    }
    /**
     * 测试单链表的逆置
     * @param head
     * @return
     */
    public static Node testReverseLinkedList(Node head) {
        if (head == null) return null;
        ArrayList<Node> list = new ArrayList<>();//创建一个动态数组
        while (head != null) {
            list.add(head);
            head = head.next;
        }
        list.get(0).next = null;//第一个节点后继设置为空
        int N = list.size();
        for (int i = 1; i < N; i++) {
            list.get(i).next = list.get(i - 1);//实现数组中链表的逆置
        }
        return list.get(N - 1);//下标是从0开始的，故返回N - 1
    }

    /**
     * 测试双链表的逆置
     * @param head
     * @return
     */
    public static DoubleNode testReverseDoubeleList(DoubleNode head) {
        if (head == null) return null;
        ArrayList<DoubleNode> list = new ArrayList<>();
        while (head != null) {
            list.add(head);
            head = head.next;
        }
        list.get(0).next = null;
        DoubleNode pre = list.get(0);
        int N = list.size();
        for (int i = 1; i < N; i++) {
            DoubleNode cur = list.get(i);
            cur.last = null;
            cur.next = pre;
            pre.last = cur;
            pre = cur;
        }
        return list.get(N - 1);
    }

    /**
     * 生成随机单链表
     * @param len
     * @param value
     * @return
     */
    public static Node generateRandomLinkedList(int len, int value) {
        int size = (int) (Math.random()*(len + 1));//[0,len]
        if (size == 0) return null;
        size--;//申请的一个头节点，故减去一个
        Node head = new Node((int) (Math.random()* (value + 1)));
        Node pre = head;
        while (size != 0) {
            Node cur = new Node((int) (Math.random()* (value + 1)));
            pre.next =cur;
            pre = cur;
            size--;
        }
        return head;
    }

    /**
     * 随机生成一个双链表
     * @param len
     * @param value
     * @return
     */
    public static DoubleNode generateRandomDoubleList(int len, int value) {
        int size = (int) (Math.random()*(len + 1));//[0,len]
        if (size == 0) return null;
        size--;//申请的一个头节点，故减去一个
       DoubleNode head = new DoubleNode((int) (Math.random()* (value + 1)));
       DoubleNode pre = head;
        while (size != 0) {
            DoubleNode cur = new DoubleNode((int) (Math.random()* (value + 1)));
            pre.next =cur;
            cur.last = pre;
            pre = cur;
            size--;
        }
        return head;
    }

    /**
     * 获取单链表原始的序列
     * @param head
     * @return
     */
    public static List<Integer> getLinkedListOriginOrder(Node head) {
        List<Integer> ans = new ArrayList<>();
        while (head != null) {
            ans.add(head.value);
            head = head.next;
        }
        return ans;
    }

    /**
     * 获取双链表的原始序列
     * @param head
     * @return
     */
    public static List<Integer> getDoubleListOriginOrder(DoubleNode head) {
        List<Integer> ans = new ArrayList<>();
        while (head != null) {
            ans.add(head.value);
            head = head.next;
        }
        return ans;
    }

    /**
     *检查单链表是否完成逆置
     * @param origin
     * @param head
     * @return
     */
    public static boolean checkLinkedListReverse(List<Integer> origin,Node head) {
        for (int i = origin.size() -1; i >=0 ; i--) {
             if (!origin.get(i).equals(head.value)) {
                 return false;
             }
             head = head.next;
        }
        return true;
    }
    public static boolean checkDoubleListReverse(List<Integer> origin, DoubleNode head) {
        DoubleNode end = null;
        //从后往前遍历看next指针是否完成正确的反转
        //这里的next相当于未反转前的pre指针
        for (int i = origin.size() - 1; i >= 0; i--) {
            if (!origin.get(i).equals(head.value)) {
                return false;
            }
            end = head;
            head = head.next;
        }
        //for循环遍历完之后,end已经来到了反转双链表的第一个节点
        //然后从前向后检查last指针是否完成正确的反转
        //这里的last相当于未反转前的next指针
        for (int i = 0; i < origin.size(); i++) {
            if (!origin.get(i).equals(end.value)) {
                return false;
            }
            end = end.last;
        }
        return true;
    }
    public static void main(String[] args) {
        int len = 50;
        int value = 100;
        int testTime = 100000;
        System.out.println("程序开始测试---------------------");
        for (int i = 0; i < testTime; i++) {
            Node node1 = generateRandomLinkedList(len, value);
            List<Integer> list1 = getLinkedListOriginOrder(node1);
            node1 = reverseLinkedList(node1);
            if (!checkLinkedListReverse(list1, node1)) {
                System.out.println("单链表程序出错啦！！！！");
            }
            DoubleNode node2 = generateRandomDoubleList(len, value);
            List<Integer> list2 = getDoubleListOriginOrder(node2);
            node2 = reverseDoubleList(node2);
            if (!checkDoubleListReverse(list2, node2)) {
                System.out.println("双链表程序出错啦！！！！");
            }
        }
        System.out.println("程序结束测试---------------------");

    }
}
