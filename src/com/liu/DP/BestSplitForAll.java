package com.liu.DP;

/**
 * 给定一个非负数组arr,长度为N
 * 那么又N-1种方案可以把arr切分成左右两个部分
 * 每一个方案都有，min{左部分的累加和，右部分的累加和}
 * 求多种方案中，min{左部分的累加和，右部分的累加和}的最大值是多少？
 * 要求时间复杂度为O(N)
 */
public class BestSplitForAll {
    /**
     * 暴力解
     */
    public static int spilt1(int[] arr) {
        if (arr == null || arr.length < 2) return 0;
        int ans = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            int sumLeft = 0;
            for (int left = 0; left <= i; left++) {
                sumLeft += arr[left];
            }
            int sumRight = 0;
            for (int right = i + 1; right < arr.length; right++) {
                sumRight += arr[right];
            }
            ans =Math.max(ans,Math.min(sumLeft,sumRight));
        }
        return ans;
    }

    /**
     * 最优解
     */
    public static int split2(int[] arr) {
        if (arr == null || arr.length < 2) return 0;
        int sumAll = 0;
        for (int num : arr) {
            sumAll += num;
        }
        int ans = 0;
        int sumLeft = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            sumLeft += arr[i];
            int sumRight = sumAll - sumLeft;
            ans = Math.max(ans,Math.min(sumLeft,sumRight));
        }
        return ans;
    }
    public static int[] randomArray(int len, int max) {
        int[] ans = new int[len];
        for (int i = 0; i < len; i++) {
            ans[i] = (int) (Math.random() * max);
        }
        return ans;
    }


    public static void main(String[] args) {
        int N = 20;
        int max = 30;
        int testTime = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * N);
            int[] arr = randomArray(len, max);
            int ans1 = spilt1(arr);
            int ans2 = split2(arr);
            if (ans1 != ans2) {
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("Oops!");
            }
        }
        System.out.println("测试结束");
    }
}
