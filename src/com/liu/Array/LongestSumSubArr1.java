package com.liu.Array;

/**
 * 给定一个正整数组成的无序数组arr，给定一个正整数值K
 * 找到arr的所有子数组里，哪个子数组的累加和等于K，并且是长度最大的
 * 返回其长度
 * 看到子数组有单调性 => 应想到滑动窗口
 */
public class LongestSumSubArr1 {

    /**
     *解法一:滑动窗口法
     */
    public static int getMaxLength(int[] arr,int k) {
        if (arr == null || arr.length == 0 || k <= 0) return 0;
        int left = 0;
        int right = 0;
        int sum = arr[0];
        int len = 0;
        while (right < arr.length) {
            if (sum == k) {
                len = Math.max(len,right - left + 1);
                sum -= arr[left++];
            }else if (sum < k) {
                right++;
                if (right == arr.length) {
                    break;
                }
                sum += arr[right];
            }else {
                //sum > k
                sum -= arr[left++];
            }
        }
        return len;
    }

    /**
     *解法二:暴力解
     */
    public static int right(int[] arr, int k) {
        int max = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                if (valid(arr,i,j,k)) {
                    max = Math.max(max,j - i + 1);
                }
            }
        }
        return max;
    }

    private static boolean valid(int[] arr, int left, int right, int k) {
        int sum = 0;
        for (int i = left; i <= right; i++) {
            sum += arr[i];
        }
        return sum == k;
    }
    public static int[] generatePositiveArray(int size, int value) {
        int[] ans = new int[size];
        for (int i = 0; i != size; i++) {
            ans[i] = (int) (Math.random() * value) + 1;
        }
        return ans;
    }

    // for test
    public static void printArray(int[] arr) {
        for (int i = 0; i != arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }


    public static void main(String[] args) {
        int len = 50;
        int value = 100;
        int testTime = 500000;
        System.out.println("开始测试");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generatePositiveArray(len, value);
            int K = (int) (Math.random() * value) + 1;
            int ans1 = getMaxLength(arr, K);
            int ans2 = right(arr, K);
            if (ans1 != ans2) {
                System.out.println("Oops!");
                printArray(arr);
                System.out.println("K : " + K);
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("结束测试");
    }
}
