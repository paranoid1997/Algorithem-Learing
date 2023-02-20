package com.liu.Stack;

/**
 * 给定一个数组arr，
 * 返回所有子数组最小值的累加和
 */
public class SumOfSubMinimums {

    /**
     *暴力方法
     */
    public static int subMinSum(int[] arr) {
        long ans = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                int min = Integer.MAX_VALUE;
                for (int k = i; k <= j; k++) {
                    min = Math.min(min,arr[k]);
                }
                ans += min;
                ans %= 1000000007;
            }
        }
        return (int) ans;
    }

    /**
     *单调栈解法
     */
    public static int subMinSum2(int[] arr) {
        int[] stack = new int[arr.length];
        int[] left = nearLessEqualLeft(arr,stack);
        int[] right = nearLessRight(arr,stack);
        long ans = 0;
        for (int i = 0; i < arr.length; i++) {
            long start = i - left[i];
            long end = right[i] - i;
            ans += start * end *(long)arr[i];
            ans %= 1000000007;
        }
        return (int) ans;
    }

    private static int[] nearLessRight(int[] arr, int[] stack) {
        int n = arr.length;
        int[] right = new int[n];
        int size = 0;
        for (int i = 0; i < n; i++) {
            while (size != 0 && arr[i] < arr[stack[size - 1]]) {
                right[stack[--size]] = i;
            }
            stack[size++] = i;
        }
        while (size != 0) {
            right[stack[--size]] = n;
        }
        return right;
    }

    private static int[] nearLessEqualLeft(int[] arr, int[] stack) {
        int n = arr.length;
        int[] left = new int[n];
        int size = 0;
        for (int i = n - 1; i >= 0; i--) {
            while (size != 0 && arr[i] <= arr[stack[size - 1]]) {
                left[stack[--size]] = i;
            }
            stack[size++] = i;
        }
        while (size != 0) {
            left[stack[--size]] = -1;
        }
        return left;
    }
    public static int[] randomArray(int len, int maxValue) {
        int[] ans = new int[len];
        for (int i = 0; i < len; i++) {
            ans[i] = (int) (Math.random() * maxValue) + 1;
        }
        return ans;
    }

    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
    public static void main(String[] args) {
        int maxLen = 100;
        int maxValue = 50;
        int testTime = 100000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * maxLen);
            int[] arr = randomArray(len, maxValue);
            int ans1 = subMinSum(arr);
            int ans2 = subMinSum2(arr);
            if (ans1 != ans2 ) {
                printArray(arr);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("出错了！");
                break;
            }
        }
        System.out.println("测试结束");
    }
}
