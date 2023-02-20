package com.liu.Greedy;

public class CandyProblem {

    public int candy(int[] arr) {
        /**
         * 思路:准备两个数组left和right
         * left数组规则:如果arr[i] > arr[i - 1],left[i] = left[i - 1] + 1
         * right数组规则:如果right[i] > arr[i + 1],right[i] = right[i + 1] + 1
         * 求left和right的max
         * 最后累加出最终答案
         */
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int ans = 0;
        int n = arr.length;
        int[] left = new int[n];
        int[] right = new int[n];
        for (int i = 0; i < n; i++) {
            left[i] = right[i] = 1;
        }
        for (int i = 1; i < n; i++) {
            if (arr[i] > arr[i - 1]) {
                left[i] = left[i - 1] + 1;
            }
        }
        for (int i = n - 2; i >= 0; i--) {
            if (arr[i] > arr[i + 1]) {
                right[i] = right[i + 1] + 1;
            }
        }

        for (int i = 0; i < n; i++) {
            ans += Math.max(left[i],right[i]);
        }
        return ans;
    }
}
