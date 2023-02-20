package com.liu.Bfprt;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 无序数组中找第k小的数
 */
public class FindMinKth {

    /**
     *解法一:利用大根堆或小根堆
     * 时间复杂度O(Nlogk)
     */
    public static int minKth(int[] arr,int k) {
        PriorityQueue<Integer> maxheap = new PriorityQueue<>(new maxHeapComparator());
//        for (int i = 0; i < k; i++) {
//            //从大到小排序
//            maxheap.update(arr[i]);
//        }
//        for (int i = k; i < arr.length; i++) {
//            if (arr[i] < maxheap.peek()) {
//                maxheap.poll();
//                maxheap.update(arr[i]);
//            }
//        }
//        return maxheap.peek();
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(new minHeapComparator2());
        for (int i = 0; i < arr.length; i++) {
            minHeap.add(arr[i]);
        }
        int ans = 0;
        for (int i = 0; i < k; i++) {
            ans = minHeap.poll();
        }
        return ans;
    }

    /**
     * 改写快排
     * 时间复杂度为O(N)
     */
    public static int minKth2(int[] arr,int k ) {
        int[] array = copy(arr);
        return process(arr,0,arr.length - 1,k - 1);
    }

    private static int process(int[] arr, int left, int right, int index) {
        if (left == right) return arr[left];
        int pivot = arr[(int) (left + (Math.random() * (right - left + 1)))];
        int[] mid = partition(arr,left,right,pivot);
        if (index >= mid[0] && index <= mid[1]) {
            return arr[index];
        }else if (index < mid[0]) {
            return process(arr,left,mid[0] - 1,index);
        }else {
            return process(arr,mid[1] + 1,right,index);
        }
    }

    private static int[] partition(int[] arr, int left, int right, int pivot) {
        if (left > right) return new int[]{-1,-1};//数组里面的值随便取
        if (left == right) return new int[]{left,right};
        int less = left - 1;
        int more = right + 1;
        int index = left;
        while (index < more) {
            if (arr[index] < pivot) {
                swap(arr,++less,index++);
            }else if (arr[index] > pivot) {
                swap(arr, index, --more);
            }else {
                index++;
            }
        }
        return new int[]{less + 1,more - 1};
    }

    public static int minKth3(int[] array, int k) {
        int[] arr = copy(array);
        return bfprt(arr, 0, arr.length - 1, k - 1);
    }

    /**
     *解法三:用bfprt算法，性能和改进的快排一样优秀
     * 为什么用这个算法呢?
     * 答:因为改进的快排用的太多了
     * 用bfprt纯属为了装逼，提高自己的身价
     */
    public static int bfprt(int[] arr, int left, int right, int index) {
        if (left == right) {
            return arr[left];
        // 每一个小组内部排好序
    }
    // L...R  每五个数一组
        // 小组的中位数组成新数组
        // 这个新数组的中位数返回
        int pivot = medianOfMedians(arr, left, right);
        int[] range = partition(arr, left, right, pivot);
        if (index >= range[0] && index <= range[1]) {
            return arr[index];
        } else if (index < range[0]) {
            return bfprt(arr, left, range[0] - 1, index);
        } else {
            return bfprt(arr, range[1] + 1, right, index);
        }
    }

    private static int medianOfMedians(int[] arr, int left, int right) {
        int size = right - left + 1;
        int offset = size % 5 == 0 ? 0 : 1;
        int[] mArr = new int[size / 5 + offset];
        for (int team = 0; team < mArr.length; team++) {
            int teamFirst = left + team * 5;//分为5组，每组的第一个
            //这句代码的意思是最后一位不好以5划分时候
            //最后还剩下多少就选多少
            mArr[team] = getMedian(arr,teamFirst,Math.min(right,teamFirst + 4));
        }
        //在mArr中找到中位数
        return bfprt(mArr,0,mArr.length - 1,mArr.length / 2);
    }

    private static int getMedian(int[] arr, int left, int right) {
        insert(arr,left,right);
        return arr[(left + right) / 2];
    }

    private static void insert(int[] arr, int left, int right) {
       if (arr == null || arr.length < 2) return;
        for (int i = left + 1; i <= right; i++) {
            for (int j = i - 1; j >= left && arr[j] > arr[j + 1]; j--) {
                swap(arr,j,j + 1);
            }
        }
    }


    public static void swap(int[] arr, int i1, int i2) {
        int tmp = arr[i1];
        arr[i1] = arr[i2];
        arr[i2] = tmp;
    }

    public static int[] copy(int[] arr) {
        int[] ans = new int[arr.length];
        for (int i = 0; i != ans.length; i++) {
            ans[i] = arr[i];
        }
        return ans;
    }

    public static class maxHeapComparator implements Comparator<Integer> {

        @Override
        public int compare(Integer o1, Integer o2) {
            return o2 - o1;
        }
    }
    public static class minHeapComparator2 implements Comparator<Integer> {

        @Override
        public int compare(Integer o1, Integer o2) {
            return o1 - o2;
        }
    }
    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) (Math.random() * maxSize) + 1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1));
        }
        return arr;
    }
    public static void main(String[] args) {
        int testTime = 1000000;
        int maxSize = 100;
        int maxValue = 100;
        System.out.println("开始测试");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            int k = (int) (Math.random() * arr.length) + 1;
            int ans1 = minKth(arr, k);
            int ans2 = minKth2(arr, k);
            int ans3 = minKth3(arr, k);
            if (ans1 != ans2 || ans2 != ans3) {
                System.out.println("Oops!");
            }
        }
        System.out.println("结束测试");
    }
}
