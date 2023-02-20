package com.liu.LinkedList;

import java.util.HashMap;


/**
 * 已知一个消息流会不断地吐出整数 1~N，
 * 但不一定按照顺序吐出。如果上次打印的数为 i，
 * 那么当 i+1 出现时，请打印 i+1 及其之后接收过的并且连续的所有数，
 * 直到 1~N 全部接收 并打印完，请设计这种接收并打印的结构。
 */
public class ReceiveAndPrint {

    public static class Node {
        public String info;
        public Node next;

        public Node(String info) {
            this.info = info;
        }
    }

    public static class Messages {
        private HashMap<Integer,Node> tailMap; //尾表
        private HashMap<Integer,Node> headMap; //头表
        private int waitNum; //需要等待才能一起发送数据的数字

        public Messages() {
            tailMap = new HashMap<>();
            headMap = new HashMap<>();
            waitNum = 1;  //消息一定是从1开始发送
        }

        //接受消息并缓存下来
        public void receive(int num,String info) {
            if (num < 1) return;
            Node cur = new Node(info);
            tailMap.put(num,cur);
            headMap.put(num,cur);
            //4-4|5:头|尾-头
            //查询x -> cur
            if (tailMap.containsKey(num - 1)) {
                tailMap.get(num - 1).next = cur;
                tailMap.remove(num -1);
                headMap.remove(num);
            }

            //查询cur -> x
            if (headMap.containsKey(num + 1)) {
                //5|6-6:头|尾-头
                cur.next = headMap.get(num + 1);
                tailMap.remove(num);
                headMap.remove(num + 1);
            }
            if (num == waitNum) {
                print();
            }
        }

        //遇到那个等待的数字，一起发送消息并打印
        public void print() {
            Node node = headMap.get(waitNum);
            headMap.remove(waitNum);
            while (node != null) {
                System.out.print(node.info + " ");
                node = node.next;
                waitNum++;
            }
            //while循环结束后,waitNum到达下一个需要等待的点
            tailMap.remove(waitNum - 1);
            System.out.println();
      }
    }
    public static void main(String[] args) {
        // MessageBox only receive 1~N
        Messages box = new Messages();
        box.receive(2,"B"); // - 2"
        box.receive(1,"A"); // 1 2 -> print, trigger is 1
        box.receive(4,"D"); // - 4
        box.receive(5,"E"); // - 4 5
        box.receive(7,"G"); // - 4 5 - 7
        box.receive(8,"H"); // - 4 5 - 7 8
        box.receive(6,"F"); // - 4 5 6 7 8
        box.receive(3,"C"); // 3 4 5 6 7 8 -> print, trigger is 3
        box.receive(9,"I"); // 9 -> print, trigger is 9
        box.receive(10,"J"); // 10 -> print, trigger is 10
        box.receive(12,"L"); // - 12
        box.receive(13,"M"); // - 12 13
        box.receive(11,"K"); // 11 12 13 -> print, trigger is 11
    }
}
