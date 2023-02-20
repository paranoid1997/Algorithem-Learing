package com.liu.Bfprt;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 给定一个无序数组arr中，长度为N, 给定一个正数k,返回topk个最大的数
 * 不同时间复杂度三个方法
 * 1)O(N*logn)
 * 2)O(N+k*logN)
 * 3)O(N+ k*logk)
 */
public class MaxTopK {

    /**
     *解法一:傻排序
     * 时间复杂度O(n * logn)
     */
    public static int[] maxTopK1(int[] arr,int k) {
        if (arr == null || arr.length == 0) return new int[0];
        int n = arr.length;
        k = Math.min(n,k);
        Arrays.sort(arr);
        int[] ans = new int[k];
        for (int i = n - 1,j = 0; j < k; i--,j++) {
            ans[j] = arr[i];
        }
        return ans;
    }

    /**
     *解法二:用堆解
     * 时间复杂度O(N + KlogN)
     */
    public static int[] maxTopK2(int[] arr,int k) {
        if (arr == null || arr.length == 0) {
            return new int[0];
        }
        int N = arr.length;
        k = Math.min(N, k);
        // 从底向上建堆，时间复杂度O(N)
        for (int i = N - 1; i >= 0; i--) {
            heapify(arr, i, N);
        }
        // 只把前K个数放在arr末尾，然后收集，O(K*logN)
        int heapSize = N;
        swap(arr, 0, --heapSize);
        int count = 1;
        while (heapSize > 0 && count < k) {
            heapify(arr, 0, heapSize);
            swap(arr, 0, --heapSize);
            count++;
        }
        int[] ans = new int[k];
        for (int i = N - 1, j = 0; j < k; i--, j++) {
            ans[j] = arr[i];
        }
        return ans;
    }

    public static void heapInsert(int[] arr,int index) {
        while (arr[index] > arr[(index - 1) / 2]);
        swap(arr,index,(index - 1) /2);
        index = (index - 1) / 2;
    }
    public static void  heapify(int[] arr,int index,int heapSize) {
        int left = index * 2 + 1;
        while (left < heapSize) {
            int largest = left + 1 < heapSize && arr[left + 1] > arr[left] ? left + 1 : left;
            largest = arr[largest] > arr[index] ? largest : index;
            if (largest == index) break;
            swap(arr,largest,index);
            index = largest;
            left = index * 2 + 1;
        }
    }

    /**
     *解法三:用priorityQueue
     */
    public static int[] maxTopK3(int[] arr,int k) {
        int N = arr.length;
        k = Math.min(N, k);
        PriorityQueue<Integer> maxheap = new PriorityQueue<>(new maxHeapComparator());
        int[] ans = new int[k];
        for (int i = 0; i < N; i++) {
            maxheap.add(arr[i]);
        }
        for (int i = 0; i < k; i++) {
            ans[i] = maxheap.poll();
        }
        return ans;
    }

    /**
     *用改进的快排
     * 最优解
     * 实现复杂度为O(N + K*logK)
     */

    public static int[] maxTopK4(int[] arr,int k) {
        if (arr == null || arr.length == 0) {
            return new int[0];
        }
        //要求topK大的数，可以求N - topK小的数
        int N = arr.length;
        k = Math.min(N, k);
        int num = minKth(arr,N - k);//第100大的数
        int[] ans = new int[k];
        int index = 0;
        for (int i = 0; i < N; i++) {
            if (arr[i] > num) {
                ans[index++] = arr[i];
            }
        }
        for (; index < k; index++) {
            ans[index] = num;
        }
        //O(K * logK)
        Arrays.sort(ans);
        for (int L = 0,R = k - 1;L < R; L++,R--) {
            swap(ans,L,R);
        }
        return ans;
    }
    // 时间复杂度O(N)
    public static int minKth(int[] arr, int index) {
        int L = 0;
        int R = arr.length - 1;
        int pivot = 0;
        int[] range = null;
        while (L < R) {
            pivot = arr[L + (int) (Math.random() * (R - L + 1))];
            range = partition(arr, L, R, pivot);
            if (index < range[0]) {
                R = range[0] - 1;
            } else if (index > range[1]) {
                L = range[1] + 1;
            } else {
                return pivot;
            }
        }
        return arr[L];
    }

    public static int[] partition(int[] arr, int L, int R, int pivot) {
        int less = L - 1;
        int more = R + 1;
        int cur = L;
        while (cur < more) {
            if (arr[cur] < pivot) {
                swap(arr, ++less, cur++);
            } else if (arr[cur] > pivot) {
                swap(arr, cur, --more);
            } else {
                cur++;
            }
        }
        return new int[] { less + 1, more - 1 };
    }

    public static class maxHeapComparator implements Comparator<Integer> {

        @Override
        public int compare(Integer o1, Integer o2) {
            return o2 - o1;
        }
    }
    public static void swap(int[] arr, int i1, int i2) {
        int tmp = arr[i1];
        arr[i1] = arr[i2];
        arr[i2] = tmp;
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            // [-? , +?]
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        return arr;
    }

    // for test
    public static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i];
        }
        return res;
    }

    // for test
    public static boolean isEqual(int[] arr1, int[] arr2) {
        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
            return false;
        }
        if (arr1 == null && arr2 == null) {
            return true;
        }
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        boolean pass = true;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int k = (int) (Math.random() * maxSize) + 1;
            int[] arr = generateRandomArray(maxSize, maxValue);

            int[] arr1 = copyArray(arr);
            int[] arr2 = copyArray(arr);
            int[] arr3 = copyArray(arr);
            int[] arr4 = copyArray(arr);

            int[] ans1 = maxTopK1(arr1, k);
            int[] ans2 = maxTopK2(arr2, k);
            int[] ans3 = maxTopK3(arr3, k);
            int[] ans4 = maxTopK3(arr4, k);
            if (!isEqual(ans1, ans2) || !isEqual(ans3, ans4)) {
                pass = false;
                System.out.println("出错了！");
                break;
            }
        }
        System.out.println("测试结束了");
    }
}
