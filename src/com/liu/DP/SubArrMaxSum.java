package com.liu.DP;


/**
 * 返回一个数组中，选择的数字不能相邻的情况下，
 * 最大子序列累加和
 */
public class SubArrMaxSum {

    /**
     * 思路:1.只要i这个元素的位置
     *      2.取i位置的元素和i-2元素相加
     *      3.不要i位置的元素,取i - 1位置的元素
     */
    public static int maxSum(int[] arr) {
        if (arr.length == 0 || arr == null) {
            return 0;
        }
        if (arr.length == 1) {
            return arr[0];
        }
        if (arr.length == 2 ) {
            return Math.max(arr[0],arr[1]);
        }
        int n = arr.length;
        int[] dp = new int[arr.length];
        dp[0] = arr[0];
        dp[1] = Math.max(arr[0],arr[1]);
        for (int i = 2; i < arr.length; i++) {
            dp[i] = Math.max(Math.max(arr[i],dp[i - 1]),arr[i] + dp[i - 2]);
        }
        return dp[n - 1];
    }
}
