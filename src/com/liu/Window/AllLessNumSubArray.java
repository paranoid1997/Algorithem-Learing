package com.liu.Window;

import java.util.LinkedList;

/**
 * 给定一个整型数组arr，和一个整数num
 * 某个arr中的子数组sub，如果想达标，必须满足：
 * sub中最大值 – sub中最小值 <= num，
 * 返回arr中达标子数组的数量
 */
public class AllLessNumSubArray {

    /**
     *暴力方法
     */
    public static int subArrNums(int[] arr, int sum) {
        if (arr == null || arr.length == 0 || sum < 0) return 0;
        int n = arr.length;
        int count = 0;
        for (int left = 0; left < n; left++) {
            for (int right = left; right < n; right++) {
                int max = arr[left];
                int min = arr[left];
                for (int i = left + 1; i <= right; i++) {
                 max = Math.max(max,arr[i]);
                 min = Math.min(min,arr[i]);
                }
                if (max - min <= sum) count++;
            }
        }
        return count;
    }

    /**
     *滑动窗口解法
     * 结论：如果left 到 right上都达标，那么这个子范围上的子数组也全部达标
     * 如果left 到 right上有一个不达标，那么这个范围上往后的数组也也全部不达标
     * 故用rihgt - left则能找到全部达标的子数组
     */
    public static int subArrNums2(int[] arr, int sum) {
        if (arr == null || arr.length == 0 || sum < 0) {
            return 0;
        }
        int n = arr.length;
        int count = 0;
        LinkedList<Integer> maxWindow = new LinkedList<>();
        LinkedList<Integer> minWindow = new LinkedList<>();
        int right = 0;
        for (int left = 0; left < n; left++) {
            while (right < n) {
                while (!maxWindow.isEmpty() && arr[maxWindow.peekLast()] <= arr[right]) {
                    maxWindow.pollLast();
                }
                maxWindow.addLast(right);
                while (!minWindow.isEmpty() && arr[minWindow.peekLast()] >= arr[right]) {
                    minWindow.pollLast();
                }
                minWindow.addLast(right);
                if (arr[maxWindow.peekFirst()] - arr[minWindow.peekFirst()] > sum) {
                    break;
                }else {
                    right++;
                }
            }
            count += right - left;
            if (maxWindow.peekFirst() == left) {
                maxWindow.pollFirst();
            }
            if (minWindow.peekFirst() == left) {
                minWindow.pollFirst();
            }
        }
        return count;
    }
    // for test
    public static int[] generateRandomArray(int maxLen, int maxValue) {
        int len = (int) (Math.random() * (maxLen + 1));
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1)) - (int) (Math.random() * (maxValue + 1));
        }
        return arr;
    }
    // for test
    public static void printArray(int[] arr) {
        if (arr != null) {
            for (int i = 0; i < arr.length; i++) {
                System.out.print(arr[i] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int maxLen = 100;
        int maxValue = 200;
        int testTime = 100000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxLen, maxValue);
            int sum = (int) (Math.random() * (maxValue + 1));
            int ans1 = subArrNums(arr, sum);
            int ans2 = subArrNums2(arr, sum);
            if (ans1 != ans2) {
                System.out.println("Oops!");
                printArray(arr);
                System.out.println(sum);
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("测试结束");
    }
}
