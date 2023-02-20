package com.liu.DP;

import java.util.Map;
import java.util.TreeMap;

/**
 * 背包容量为w
 * 一共有n袋零食, 第i袋零食体积为v[i]
 * 总体积不超过背包容量的情况下，
 * 一共有多少种零食放法？(总体积为0也算一种放法)。
 */
public class SnacksWays {
    /**
     * 暴力递归
     */
    public static int ways1(int[] arr,int w) {
        return process(arr,0,w);
    }

    //从左往右模型
    private static int process(int[] arr, int index, int rest) {
        if (rest < 0) return -1;//-1表示无方案
        if (index == arr.length) return 1;//无零食可选
        int p1 = process(arr,index + 1,rest);
        int p2 = process(arr,index + 1,rest - arr[index]);
        return p1 + (p2 == -1 ? 0 : p2);
    }

    /**
     * 从暴力递归改动态规划
     */
    public static int way2(int[] arr,int w) {
        int n = arr.length;
        int[][] dp = new int[n + 1][w + 1];
        for (int j = 0; j <= w; j++) {
            dp[n][j] = 1;
        }
        for (int index = n - 1; index >= 0; index--) {
            for (int rest = 0; rest <= w; rest++) {
                dp[index][rest] = dp[index + 1][rest] + (rest - arr[index] >= 0 ? dp[index + 1][rest - arr[index]] : 0);
            }
        }
        return dp[0][w];
    }

    /**
     * 分治思想
     */
    public static long way3(int[] arr,int bag) {
        if (arr.length == 0 || arr == null) return 0;
        if (arr.length == 1) {
            //arr[0] <= bag 可以选择放进去，也可以选择不放进去
            //故一共两种方法
            return arr[0] <= bag ? 2 : 1;
        }
        int mid = (arr.length - 1) / 2;
        TreeMap<Long,Long> lmap = new TreeMap<>();
        //左边零食一共的方法数
        long ways = process1(arr,0,0,mid,bag,lmap);
        TreeMap<Long,Long> rmap = new TreeMap<>();
        //右边零食一共的方法数
        ways += process1(arr,mid + 1,0,arr.length - 1,bag,rmap);
        TreeMap<Long, Long> rpre = new TreeMap<>();
        long pre = 0;
        //中间零食一共的方法数
        for (Map.Entry<Long,Long> entry : rmap.entrySet()) {
            //entrySet()表示一对键值对
            pre += entry.getValue();
            //类似于整合逻辑
            //包含1，2的数一共有多少个
            rpre.put(entry.getKey(),pre);
        }
        for (Map.Entry<Long,Long> entry : lmap.entrySet()) {
            Long lweight = entry.getKey();
            Long lways = entry.getValue();
            Long floorKey = rpre.floorKey(bag - lweight);
            if (floorKey != null) {
                Long rways = rpre.get(floorKey);
                ways += lways * rways;
            }
        }
        //左侧一个都不拿，右侧一个都不拿的情况
        return ways + 1;
    }

    private static long process1(int[] arr, int index, long sum, int end, long bag, TreeMap<Long, Long> map) {
        if (sum > bag) {
            return 0;
        }
        if (index > end) {
            //所有商品都自由选择完了
            if (sum != 0) {
                if (!map.containsKey(sum)) {
                    map.put(sum,1L);
                }else {
                    map.put(sum,map.get(sum) + 1);
                }
                return 1;//返回有效方法1
            }else {
                //sum = 0时，一个货物都没选择
                return 0;
            }
        }
        long ways = process1(arr,index + 1,sum,end,bag,map);
        ways += process1(arr,index + 1,sum + arr[index],end,bag,map);
        return ways;
    }

    public static int[] generateRandomArray(int len, int value) {
        int[] ans = new int[(int) (Math.random() * len) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (int) (Math.random() * value);
        }
        return ans;
    }
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
    public static void main(String[] args) {
        int len = 10;
        int value = 100;
        int w = (int) (Math.random() * value);
        int testTime = 500000;
        System.out.println("开始测试");
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(len, value);
            int[] arr2 = copyArray(arr1);
            int[] arr3 = copyArray(arr2);
            int ans1 = ways1(arr1, w);
            int ans2 = way2(arr2, w);
            long ans3 = way3(arr3,w);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("Oops!");
            }
        }
        System.out.println("结束测试");
    }
}