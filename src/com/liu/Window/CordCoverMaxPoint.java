package com.liu.Window;

import java.util.Arrays;

/**
 * 给定一个有序数组arr,代表坐落在X轴上的点
 * 给定一个正数K，代表绳子的长度
 * 返回绳子最多压中几个点？
 * 即使绳子的边缘处盖住点也算盖住
 */
public class CordCoverMaxPoint {
    /**
     * 贪心+二分法
     * 时间复杂度O(N*logN)
     * 从1这个点往左做多能覆盖几个点，从1这个点往左做多能覆盖几个点
     * 以此类推...贪心策略
     * 然后利用二分法以NlogN的速度找到离arr[i] - K这个值最近的坐标
     * 最后right - left + 1算出最多覆盖的最多的点
     */
    public static int maxPoint1(int[] arr, int K) {
        int res = 1;
        for (int i = 0; i < arr.length; i++) {
            int nearest = nearestIndex(arr, i, arr[i] - K);
            res = Math.max(res, i - nearest + 1);
        }
        return res;
    }

    public static int nearestIndex(int[] arr, int right, int value) {
      int left = 0;
      int index = right;
      while (left <= right) {
          int mid = left + ((right - left) >> 2);
          //int mid = (right - left) /2;
          if (arr[mid] >= value) {
              index = mid;
              right = mid - 1;
          }else {
              left = mid + 1;
          }
      }
      return index;
    }

    /**
     * 滑动窗口
     * 时间复杂度O(N)
     */
    public static int maxPoint2(int[] arr,int K) {
        int left = 0;
        int right = 0;
        int max = 0;
        while (left < arr.length) {
            while (right < arr.length && arr[right] - arr[left] <= K) {
                //这个right的值是到达边界的值 + 1
                //所以下标从0开始不需要right - left + 1
                right++;
            }
            max = Math.max(max,right -left);
            left++;
        }
        return max;
    }

    // for test
    public static int test(int[] arr, int L) {
        int max = 0;
        for (int i = 0; i < arr.length; i++) {
            int pre = i - 1;
            while (pre >= 0 && arr[i] - arr[pre] <= L) {
                pre--;
            }
            max = Math.max(max, i - pre);
        }
        return max;
    }

    // for test
    public static int[] generateArray(int len, int max) {
        int[] ans = new int[(int) (Math.random() * len) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (int) (Math.random() * max);
        }
        Arrays.sort(ans);
        return ans;
    }

    public static void main(String[] args) {
        int len = 500;
        int max = 100;
        int testTime = 100000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int L = (int) (Math.random() * max);
            int[] arr = generateArray(len, max);
            int ans1 = maxPoint1(arr, L);
            int ans2 = maxPoint2(arr, L);
            int ans3 = test(arr, L);
            if (ans1 != ans2 || ans2 != ans3) {
                System.out.println("oops!");
                break;
            }
        }
        System.out.println("测试结束");
    }
}
