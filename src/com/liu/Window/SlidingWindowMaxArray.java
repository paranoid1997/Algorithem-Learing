package com.liu.Window;

import java.util.LinkedList;

/**
 * 假设一个固定窗口为w的窗口，依次划过arr,
 * 返回每一次滑出状态的最大值
 * 例如，arr = [4,3,5,4,3,3,6,7] w = 3
 * 返回：[5,5,5,4,6,7]
 */
public class SlidingWindowMaxArray {

    /**
     *暴力方法
     */
    public static int[] maxArray(int[] arr,int w) {
        if (arr == null || arr.length == 0 ||
                arr.length < w || w < 1) {
            return null;
        }
        int n = arr.length;
        int[] res = new int[n - w + 1];
        int index = 0;
        int left = 0;
        int right = w - 1;
        while (right < n) {
            int max = arr[left];
            for (int i = left + 1; i <= right; i++) {
                max = Math.max(max,arr[i]);
            }
            res[index++] = max;
            left++;
            right++;
        }
        return res;
    }

    /**
     *滑动窗口解法
     */
    public static int[] getMaxWindow(int[] arr,int w) {
        if (arr == null || arr.length == 0 ||
                arr.length < w || w < 1) {
            return null;
        }
        //双端队列
        LinkedList<Integer> qmax = new LinkedList<>();
        int[] res = new int[arr.length - w + 1];
        int index = 0;
        for (int right = 0; right < arr.length; right++) {
            while (!qmax.isEmpty() && arr[qmax.peekLast()] <= arr[right]) {
                //保证队列里面留下来的都是最大值
                qmax.pollLast();
            }
            qmax.addLast(right);
            if (qmax.peekFirst() == right - w) {
                //这是如果窗口过期了
                //过期的窗口弹出
                qmax.pollFirst();
            }
            if (right >= w - 1) {
                //已经达到了最大窗口了
                //准备收集答案
                res[index++] = arr[qmax.peekFirst()];
            }
        }
        return res;
    }
    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1));
        }
        return arr;
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
        int testTime = 100000;
        int maxSize = 100;
        int maxValue = 100;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            int w = (int) (Math.random() * (arr.length + 1));
            int[] ans1 = getMaxWindow(arr, w);
            int[] ans2 = maxArray(arr, w);
            if (!isEqual(ans1, ans2)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("测试结束");
    }
}
