package com.liu.Queue;

/**
 * 用循环数组实现一个队列
 */
public class CycleArrayToQueue {
    public static class MyQueue {
        private int[] arr;
        private int pushi;//end用来实现加数
        private int polli;//begin实现减数
        private int size;
        private final int limit;
        public MyQueue(int limit) {
            arr = new int[limit];
            polli = 0;
            pushi = 0;
            this.limit = limit;
        }
        public void push(int value) {
            if (size == limit) throw new RuntimeException("队列满了，不能加了");
            size++;
            arr[pushi] = value;
            pushi = nextIndex(pushi);
        }
        public boolean isEmpty() {
            return size == 0;
        }
        public int pop() {
            if (size == 0) {
                throw new RuntimeException("队列空了，不能再拿了");
            }
            size--;
            int ans = arr[polli];
            polli = nextIndex(polli);
            return ans;
        }

        // 如果现在的下标是i，返回下一个位置
        //这一步用来实现循环数组i的下标
        private int nextIndex(int i) {
            return i < limit - 1 ? i + 1 : 0;
        }
    }
    public static void main(String[] args) {
        MyQueue queue = new MyQueue(2);
        queue.push(1);
        queue.push(2);
        queue.pop();
        System.out.println(queue);
    }
}
