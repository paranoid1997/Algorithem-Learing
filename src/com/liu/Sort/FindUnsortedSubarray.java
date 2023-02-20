package com.liu.Sort;

import java.util.Arrays;

/**
 * 给你一个整数数组 nums ，你需要找出一个连续子数组，
 * 如果对这个子数组进行升序排序，那么整个数组都会变为升序排序。
 * 请你找出符合题意的 最短子数组，并输出它的长度。
 *
 */
public class FindUnsortedSubarray {

    /**
     * 暴力解
     */
    public static int findUnsortedSubarray1(int[] nums) {
        if (nums == null || nums.length == 1) return 0;
        int count = 0;
        int left = 0;
        int right = 0;
        int[] copyNums = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            copyNums[i] = nums[i];
        }
        Arrays.sort(nums);

        //预防测试用例1234
        for (int i = 0; i < copyNums.length; i++) {
            if (copyNums[i] == nums[i]) {
                count++;
            }
            if (count == nums.length) return 0;
        }
        for (int i = 0; i < copyNums.length; i++) {
            if (copyNums[i] != nums[i]) {
                left = i;
                break;
            }
        }
        for (int i = copyNums.length - 1;i > 0; i-- ) {
            if (copyNums[i] != nums[i]) {
                right = i;
                break;
            }
        }

        return right - left + 1;
    }

    /**
     * 最优解
     * 时间复杂度O(N)
     */

    public static int findUnsortedSubarray2(int[] nums) {
        if (nums == null || nums.length == 1) return 0;
        int left = nums.length;
        int right = -1;
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] < max) {
                right = i;
            }
            max = Math.max(max,nums[i]);
        }
        for (int i = nums.length - 1; i >=0; i--) {
            if (nums[i] > min) {
                left = i;
            }
            min = Math.min(min,nums[i]);
        }
        return Math.max(0,right -left + 1);
    }
}
