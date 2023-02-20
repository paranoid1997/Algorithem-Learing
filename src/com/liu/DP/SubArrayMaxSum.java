package com.liu.DP;

public class SubArrayMaxSum {

    /**
     * 暴力解
     */
    public int maxSubArray(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int max = Integer.MIN_VALUE;
        for(int left = 0; left < nums.length;left++) {
            int temp = 0;
            for(int right = left; right < nums.length;right++) {
                temp += nums[right];
                max = Math.max(max,temp);
            }
        }
        return max;
    }

    /**
     * dp滚动数组
     */

    public int maxSubArray1(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int max = nums[0];
        int pre = nums[0];
        for (int i = 1; i < nums.length; i++) {
            int p1 = nums[i];
            int p2 = nums[i] + pre;
            int cur = Math.max(p1,p2);
            max = Math.max(max,cur);
            pre = cur;
        }
        return max;
    }
}
