package com.liu.DP;

import java.util.LinkedList;

/**
 * 给定一个数组arr，和一个正数M
 * 返回在子数组长度不大于M的情况下，最大的子数组累加和
 */
public class MaxSumLengthNoMore {

    /**
     * 暴力解
     */
    public static int test(int[] arr,int m) {
        if (arr == null || arr.length == 0 || m < 1) {
            return 0;
        }
        int n = arr.length;
        int max = Integer.MIN_VALUE;
        for (int left = 0; left < n; left++) {
            for (int right = left; right < n; right++) {
                if (right - left + 1 > m) {
                    break;
                }
                int sum = 0;
                for (int i = left; i <= right; i++) {
                    sum += arr[i];
                }
                max = Math.max(max,sum);
            }
        }
        return max;
    }
    // O(N)的解法，最优解
    public static int maxSum(int[] arr, int M) {
        if (arr == null || arr.length == 0 || M < 1) {
            return 0;
        }
        int N = arr.length;
        int[] sum = new int[N];
        sum[0] = arr[0];
        for (int i = 1; i < N; i++) {
            sum[i] = sum[i - 1] + arr[i];
        }
        LinkedList<Integer> qmax = new LinkedList<>();
        int i = 0;
        int end = Math.min(N, M);
        for (; i < end; i++) {
            while (!qmax.isEmpty() && sum[qmax.peekLast()] <= sum[i]) {
                qmax.pollLast();
            }
            qmax.add(i);
        }
        int max = sum[qmax.peekFirst()];
        int L = 0;
        for (; i < N; L++, i++) {
            if (qmax.peekFirst() == L) {
                qmax.pollFirst();
            }
            while (!qmax.isEmpty() && sum[qmax.peekLast()] <= sum[i]) {
                qmax.pollLast();
            }
            qmax.add(i);
            max = Math.max(max, sum[qmax.peekFirst()] - sum[L]);
        }
        for (; L < N - 1; L++) {
            if (qmax.peekFirst() == L) {
                qmax.pollFirst();
            }
            max = Math.max(max, sum[qmax.peekFirst()] - sum[L]);
        }
        return max;
    }
    //for test
    public static int[] randomArray(int len, int max) {
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * max) - (int) (Math.random() * max);
        }
        return arr;
    }

    public static void main(String[] args) {
        int maxN = 50;
        int maxValue = 100;
        int testTime = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int N = (int) (Math.random() * maxN);
            int M = (int) (Math.random() * maxN);
            int[] arr = randomArray(N, maxValue);
            int ans1 = test(arr, M);
            int ans2 = maxSum(arr, M);
            if (ans1 != ans2) {
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("Oops!");
            }
        }
        System.out.println("测试结束");
    }
}
