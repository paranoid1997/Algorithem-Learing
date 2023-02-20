package com.liu.DP;

import java.util.HashMap;

/**
 * 给定一个数组arr，你可以在每个数字之前决定+或者-,
 * 但是必须所有数字都参与, 再给定一个数target，
 * 请问最后算出target的方法数是多少？
 *
 * 输入：nums = [1,1,1,1,1], target = 3
 * 输出：5
 * 解释：一共有 5 种方法让最终目标和为 3 。
 * -1 + 1 + 1 + 1 + 1 = 3
 * +1 - 1 + 1 + 1 + 1 = 3
 * +1 + 1 - 1 + 1 + 1 = 3
 * +1 + 1 + 1 - 1 + 1 = 3
 * +1 + 1 + 1 + 1 - 1 = 3
 */
public class TargetSum {

    /**
     * 暴力递归
     */
    public static int targetSum1(int[] nums,int target) {
        if (nums.length == 0 || nums == null) return 0;
        return recursion(nums,0,target);
    }

    private static int recursion(int[] nums, int index, int rest) {
        if (nums.length == index) {
           return rest == 0 ? 1 : 0;
        }
        //数组还有数的时候
        return recursion(nums,index + 1,rest - nums[index])
                + recursion(nums,index + 1,rest + nums[index]);
    }



    /**
     * 记忆化搜索
     */
    public static int targetSum2(int[] nums, int target) {
        if (nums.length == 0 || nums == null) return 0;
        return process2(nums, 0, target, new HashMap<>());
    }

    public static int process2(int[] arr, int index, int rest, HashMap<Integer, HashMap<Integer, Integer>> dp) {
        //index在hashmap表示key,rest的结果集作为value
        if (dp.containsKey(index) && dp.get(index).containsKey(rest)) {
            return dp.get(index).get(rest);
        }
        // 否则，没命中！
        int ans = 0;
        if (index == arr.length) {
            ans = rest == 0 ? 1 : 0;
        } else {
            ans = process2(arr, index + 1, rest - arr[index], dp) +
                    process2(arr, index + 1, rest + arr[index], dp);
        }
        if (!dp.containsKey(index)) {
            dp.put(index, new HashMap<>());
        }
        dp.get(index).put(rest, ans);
        return ans;
    }



    /**
     * 暴力递归改动态规划
     * 待完成！！
     */
    public static int targetSumWithDp(int[] nums,int target) {
        if (nums.length == 0 || nums == null) return 0;
        int n = nums.length;
        int sum = 0;
        for (int num: nums) {
            sum += num;
        }
        int[][] dp = new int[n + 1][sum +1];
        dp[n][0] = 1;
        for (int index = n - 1; index >= 0; index--) {
            for (int rest = 0; rest <= target; rest++) {
                dp[index][rest] = (rest - nums[index] >= 0 ? dp[index + 1][rest - nums[index]] : 0)
                +dp[index + 1][rest + nums[index]];
            }
        }
        return dp[0][target];
    }

}
