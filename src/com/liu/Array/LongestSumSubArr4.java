package com.liu.Array;


import java.util.TreeMap;

/**
 * 给定一个整型数组arr，和一个整数K，
 * 求平均值小于等于K的所有子数组中，最大长度是多少
 * 这个数组不都是整数，可正可负可零。
 * 利用数组三连问题，第三连
 * 都减去K之后，求数组累加和<=0的最大子数组
 */
public class LongestSumSubArr4 {

    /**
     * 解法一 : 暴力解
     * 时间复杂度O(N^3)
     */
    public static int ways1(int[] arr,int v) {
        int ans = 0;
        for (int left = 0; left < arr.length; left++) {
        for (int right = left; right < arr.length; right++) {
            int sum = 0;
            int k = right - left + 1;
            for (int i = left; i <= right; i++) {
                sum += arr[i];
            }
            double avg = (double)sum / (double)k;
            if (avg <= v) {
                ans = Math.max(k,ans);
            }
        }
    }
        return ans;
}

    /**
     * 解法二:继承于数组第三连的思想
     * 都减去K之后，求数组累加和<=0的最大子数组
     * 时间复杂度O(N)
     */
    public static int ways2(int[] arr,int v) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        for (int i = 0; i < arr.length; i++) {
            arr[i] -= v;
        }
        return maxLength(arr,0);
    }

    private static int maxLength(int[] arr, int k) {
        int[] minSums = new int[arr.length];
        int[] minSumEnds = new int[arr.length];
        minSums[arr.length - 1] = arr[arr.length - 1];
        minSumEnds[arr.length - 1] = arr.length - 1;
        for (int i = arr.length - 2; i >= 0; i--) {
            if (minSums[i + 1] < 0) {
                minSums[i] = arr[i] + minSums[i + 1];
                minSumEnds[i] = minSumEnds[i + 1];
            }else {
                minSums[i] = arr[i];
                minSumEnds[i] = i;
            }
        }
        int end = 0;
        int sum = 0;
        int ans = 0;
        for (int i = 0; i < arr.length; i++) {
            while (end < arr.length && sum + minSums[end] <= k) {
                sum += minSums[end];
                end = minSumEnds[end] + 1;
            }
            ans = Math.max(end - i,ans);
            if (end > i) {
                sum -= arr[i];
            }else {
                end = i + 1;
            }
        }
        return ans;
    }

    /**
     * 想实现的解法3，时间复杂度O(N*logN)
     */
    public static int ways3(int[] arr, int v) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        TreeMap<Integer, Integer> origins = new TreeMap<>();
        int ans = 0;
        int modify = 0;
        for (int i = 0; i < arr.length; i++) {
            int p1 = arr[i] <= v ? 1 : 0;
            int p2 = 0;
            int querry = -arr[i] - modify;
            if (origins.floorKey(querry) != null) {
                p2 = i - origins.get(origins.floorKey(querry)) + 1;
            }
            ans = Math.max(ans, Math.max(p1, p2));
            int curOrigin = -modify - v;
            if (origins.floorKey(curOrigin) == null) {
                origins.put(curOrigin, i);
            }
            modify += arr[i] - v;
        }
        return ans;
    }
    // 用于测试
    public static int[] randomArray(int maxLen, int maxValue) {
        int len = (int) (Math.random() * maxLen) + 1;
        int[] ans = new int[len];
        for (int i = 0; i < len; i++) {
            ans[i] = (int) (Math.random() * maxValue);
        }
        return ans;
    }
    // 用于测试
    public static int[] copyArray(int[] arr) {
        int[] ans = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            ans[i] = arr[i];
        }
        return ans;
    }

    // 用于测试
    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        System.out.println("测试开始");
        int maxLen = 20;
        int maxValue = 100;
        int testTime = 500000;
        for (int i = 0; i < testTime; i++) {
            int[] arr = randomArray(maxLen, maxValue);
            int value = (int) (Math.random() * maxValue);
            int[] arr1 = copyArray(arr);
            int[] arr2 = copyArray(arr);
            int[] arr3 = copyArray(arr);
            int ans1 = ways1(arr1, value);
            int ans2 = ways2(arr2, value);
            int ans3 = ways3(arr3, value);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("测试出错！");
                System.out.print("测试数组：");
                printArray(arr);
                System.out.println("子数组平均值不小于 ：" + value);
                System.out.println("方法1得到的最大长度：" + ans1);
                System.out.println("方法2得到的最大长度：" + ans2);
                System.out.println("方法3得到的最大长度：" + ans3);
                System.out.println("=========================");
            }
        }
        System.out.println("测试结束");
    }
}
