package com.liu.DP;

import java.util.Arrays;

/**
 * 一条直线上有居民点，邮局只能建在居民点上。
 * 给定一个有序正数数组arr,每个值表示居民点的一维坐标
 * 再给定一个正数num,表示邮局数量。选择num个居民点，建立num个邮局
 * 使所有的居民点到最近的邮局的总距离最短，返回最短的总距离。
 */
public class PostOfficeProblem {

    /**
     * 动态规划之不优化枚举
     */
    public static int min1(int[] arr,int num) {
        if (arr == null || arr.length == 0 || arr.length < num || num < 1) {
            return 0;
        }
        int n = arr.length;
        int[][] w = new int[n + 1][n + 1];//表示上一个的最短的距离
        int[][] dp = new int[n][num + 1];
        for (int left = 0; left < n; left++) {
            for (int right = left + 1; right < n; right++) {
                w[left][right] = w[left][right - 1] + arr[right] - arr[(left + right) / 2];
            }
        }
        for (int i = 0; i < n; i++) {
            dp[i][1] = w[0][i];
        }
        for (int i = 1; i < n; i++) {
            for (int j = 2; j <= Math.min(i,num); j++) {
                //j > i表示一共有j个邮局 却只有i个居民点无意义
                int ans = Integer.MAX_VALUE;
                for (int k = 0; k <= i; k++) {
                    ans = Math.min(ans,dp[k][j - 1] + w[k + 1][i]);
                }
                dp[i][j] = ans;
            }
        }
        return dp[n - 1][num];
    }

    /**
     * 四边形不等式优化
     * 左下
     */
    public static int min2(int[] arr,int num) {
        if (arr == null || arr.length == 0 || arr.length < num || num < 1) {
            return 0;
        }
        int n = arr.length;
        int[][] w = new int[n + 1][n + 1];//表示上一个的最短的距离
        int[][] dp = new int[n][num + 1];
        int[][] best = new int[n][num + 1];
        for (int left = 0; left < n; left++) {
            for (int right = left + 1; right < n; right++) {
                w[left][right] = w[left][right - 1] + arr[right] - arr[(left + right) / 2];
            }
        }
        for (int i = 0; i < n; i++) {
            dp[i][1] = w[0][i];
            best[i][1] = -1;
        }
        for (int j = 2; j <= num; j++) {
            for (int i = n - 1; i > 0; i--) {
                int down = best[i][j - 1];//左
                int up = i == n - 1 ? n - 1 : best[i + 1][j];//下
                int bestChoose = -1;
                int ans = Integer.MAX_VALUE;
                for (int leftEnd = down; leftEnd <= up; leftEnd++) {
                    int leftCost = leftEnd == -1 ? 0 : dp[leftEnd][j - 1];
                    int rightCost = leftEnd == i ? 0 : w[leftEnd + 1][i];
                    int cur = leftCost + rightCost;
                    if (cur < ans) {
                        ans = cur;
                        bestChoose = leftEnd;
                    }
                }
                dp[i][j] = ans;
                best[i][j] = bestChoose;
            }
        }
        return dp[n - 1][num];
    }
    // for test
    public static int[] randomSortedArray(int len, int range) {
        int[] arr = new int[len];
        for (int i = 0; i != len; i++) {
            arr[i] = (int) (Math.random() * range);
        }
        Arrays.sort(arr);
        return arr;
    }

    // for test
    public static void printArray(int[] arr) {
        for (int i = 0; i != arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
    public static void main(String[] args) {
        int N = 30;
        int maxValue = 100;
        int testTime = 100000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * N) + 1;
            int[] arr = randomSortedArray(len, maxValue);
            int num = (int) (Math.random() * N) + 1;
            int ans1 = min1(arr, num);
            int ans2 = min2(arr, num);
            if (ans1 != ans2) {
                printArray(arr);
                System.out.println(num);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("Oops!");
            }
        }
        System.out.println("测试结束");
    }
}
