package com.liu.Pointer;

import java.util.Arrays;

/**
 * 给定一个正数数组arr,代表若干人的体重
 * 再给定一个正数imit,表示所有船共同拥有的载重量
 * 每艘船最多坐两人，且不能超过载重
 * 想让所有的人同时过河，并且用最好的分配方法让船尽量少
 * 返回最少的船数
 */
public class BoatsToSavePeople {

    public int numRescueBoats(int[] arr, int limit) {
        if (arr.length == 0 || arr == null || limit < 1) return 0;
        if (arr.length == 1 && arr[0] <= limit) return 1;
        Arrays.sort(arr);
        int n = arr.length;
        if (arr[n - 1] > limit) {
            return -1;
        }
        int lessR = -1;
        for (int i = n - 1; i >= 0; i--) {
            if (arr[i] <= limit / 2) {
                lessR = i;
                break;
            }
        }
        int left = lessR;
        int right = lessR + 1;
        int unSolved = 0;
        while (left >= 0) {
            int solved = 0;
            while (right < n && arr[left] + arr[right] <= limit) {
                right++;
                solved++; //打勾
            }
            if (solved == 0) {
                unSolved++;//打叉
                left--;
            }else {
                left = Math.max(-1,left - solved);
            }
        }
        int leftAll = lessR + 1;
        int used = leftAll - unSolved;
        int moreUsed = n - leftAll - used;
        return used + ((unSolved + 1)) / 2 + moreUsed;
    }
}
