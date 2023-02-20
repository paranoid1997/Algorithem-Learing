package com.liu.Sort;

import java.util.Comparator;

public class Heap {
    public static class myMaxHeap {
        private int[] heap;
        private final int limit;
        private int heapSize;

        public myMaxHeap(int limit) {
            this.heap = new int[limit];
            this.limit = limit;
            heapSize = 0;
        }

        public boolean isEmpty() {
            return heapSize == 0;
        }

        public boolean isFull() {
            return heapSize == limit;
        }

        public void push(int value) {
            if (heapSize == limit) {
                throw new RuntimeException("Heap is full");
            }
            heap[heapSize] = value;
            heapInsert(heap, heapSize++);
        }

        public int pop() {
            int ans = heap[0];
            swap(heap, 0, --heapSize);//用最后一个元素和大根堆交换
            heapify(heap, 0, heapSize);//调整交换之后的堆结构
            return ans;
        }

        /**
         * 插入堆的结构里，向上移动变成大根堆
         */
        private static void heapInsert(int[] arr, int index) {
            while (arr[index] > arr[(index - 1)/2]) {//如果当前节点比父亲节点大
                swap(arr, index, (index - 1) / 2);//当前节点和父亲节点交换
                index = (index - 1) / 2;//当前节点指针来到父亲节点
            }
        }

        /**
         * 从index位置，往下看，不断的向下沉
         * 停：较大的孩子都不再比index位置的数大；已经没孩子了
         * 通常用于删除堆上一个元素，然后再调整堆结构
         */
        private static void heapify(int[] arr, int index, int heapSize) {
            int left = index * 2 + 1;//left指向左孩子节点
            while (left < heapSize) {//如果有左孩子，可能有右孩子也可能没有
                int largest = left + 1 < heapSize && arr[left + 1] > arr[left] ? left + 1 : left;//选出较大孩子
                //考虑父节点和左右节点谁的值更大
                // 然后把值最大的下标赋值给largest
                largest = arr[largest] > arr[index] ? largest : index;
                if (largest == index) {
                    break;//如果父节点不需要调整，则直接跳出整个循环
                }
                //index和较大值的孩子，需要互换
                swap(arr, largest, index);
                index = largest;//父节点index到下一个孩子节点上
                left = index * 2 + 1;//再继续往下比较需不需要往下沉
            }
        }

        /**
         * 对数器
         */
        public static class myMaxHeap2 {
            private int[] arr;
            private final int limit;
            private int size;

            public myMaxHeap2(int limit) {
                arr = new int[limit];
                this.limit = limit;
                size = 0;
            }

            public boolean isEmpty() {
                return size == 0;
            }

            public boolean isFull() {
                return size == limit;
            }

            public void push(int value) {
                if (size == limit) {
                    throw new RuntimeException("heap is full");
                }
                arr[size++] = value;
            }

            public int pop() {
                int maxIndex = 0;
                for (int i = 1; i < size; i++) {
                    if (arr[i] > arr[maxIndex]) {
                        maxIndex = i;
                    }
                }
                int ans = arr[maxIndex];
                //意思就是用数组的最后一个元素覆盖到刚刚弹出的那个元素上
                //数组原来的长度从原来的n变成n-1
                arr[maxIndex] = arr[--size];
                return ans;
            }
        }

        public static void swap(int[] arr, int i, int j) {
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }

        public static class MyComparator implements Comparator<Integer> {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;//大根堆
            }

        }

        public static void main(String[] args) {
            int value = 1000;
            int limit = 100;
            int testTimes = 1000000;
            System.out.println("测试开始");
            for (int i = 0; i < testTimes; i++) {
                int curLimit = (int) (Math.random() * limit)+ 1;
                myMaxHeap myMaxHeap = new myMaxHeap(curLimit);
                myMaxHeap2 testMaxHeap2 = new myMaxHeap2(curLimit);
                int curOpTimes = (int) ((Math.random() * limit) );//压入多少的元素
                for (int j = 0; j < curOpTimes; j++) {
                    if (myMaxHeap.isEmpty() != testMaxHeap2.isEmpty()) {
                        System.out.println("fuking code");
                    }
                    if (myMaxHeap.isFull() != testMaxHeap2.isFull()) {
                        System.out.println("fucking code");
                    }
                    if (myMaxHeap.isEmpty()) {
                        int curVal = (int) (Math.random() * value);
                        myMaxHeap.push(curVal);
                        testMaxHeap2.push(curVal);
                    }else if (myMaxHeap.isFull()) {
                        if (myMaxHeap.pop() != testMaxHeap2.pop()) {
                            System.out.println("fucking code");
                        }
                    }else {
                        if (Math.random() < 0.5) {
                            int curVal = (int) (Math.random() * value);
                            myMaxHeap.push(curVal);
                            testMaxHeap2.push(curVal);
                        }else {
                            if (myMaxHeap.pop() != testMaxHeap2.pop()) {
                                System.out.println("fucking code");
                            }
                        }
                    }
                }
            }
            System.out.println("测试结束");
        }
    }
}
