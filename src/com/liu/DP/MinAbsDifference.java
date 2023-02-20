package com.liu.DP;

import java.util.Arrays;

public class MinAbsDifference {
    int point = 0;
    public int minAbsDifference(int[] nums, int goal) {
        if (nums == null || nums.length == 0) return goal;
        int mid = nums.length >> 1;
        point = 0;
        int ans = Integer.MAX_VALUE;
        int[] leftArr = new int[1 << mid];//存放左边所有组合的数组
        int[] rightArr = new int[1 << (nums.length - mid)];//存放右边所有组合的数组
        process(nums,0,mid - 1,0,leftArr);
        point = 0;
        process(nums,mid,nums.length - 1,0,rightArr);
        //开始整合左半边和右半边的结果
        Arrays.sort(leftArr);
        Arrays.sort(rightArr);
        int left = 0;
        int right = rightArr.length - 1;
        while (left < leftArr.length && right >= 0) {
            int temp = leftArr[left] + rightArr[right];
            ans = Math.min(ans,Math.abs(goal - temp));
            if (temp > goal) {
                right--;
            }else {
                left++;
            }
        }

        return ans;
    }

    private void process(int[] nums, int start, int end, int sum, int[] arr) {
        arr[point++] = sum;
        for (int i = start; i <= end; i++) {
            process(nums,i +1 ,end,sum + nums[i],arr);
        }
    }
}
