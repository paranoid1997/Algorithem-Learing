package com.liu.DP;

/**
 * 四边形不等式
 * 摆放着n堆石子。现要将石子有次序地合并成一堆
 * 规定每次只能选相邻的2堆石子合并成新的一堆,
 * 并将新的一堆石子数记为该次合并的得分
 * 求出将n堆石子合并成一堆的最小得分（或最大得分）合并方案
 */
public class StoneMerge {

    /**
     * 暴力递归
     */
    public static int min1(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int n = arr.length;
        int[] prefixSum = sum(arr);
        return process1(0,n - 1,prefixSum);
    }

    private static int process1(int left, int right, int[] prefixSum) {
        if (left == right) {
            //区间0-0划分 代价为0
            return 0;
        }
        int next = Integer.MAX_VALUE;
        for (int leftEnd = left; leftEnd < right; leftEnd++) {
            next = Math.min(next,process1(left,leftEnd,prefixSum) + process1(leftEnd + 1,right,prefixSum));
        }
        return next + w(prefixSum,left,right);
    }

    /**
     * 暴力递归改动态规划
     */
    public static int min2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int n = arr.length;
        int[] prefixSum = sum(arr);
        int[][] dp = new int[n][n];
        for (int left = n - 2; left >= 0; left--) {
            //为啥left = n -2？
            //因为因为n - 1行均是left > end
            for (int right = left + 1; right < n; right++) {
                int next = Integer.MAX_VALUE;
                for (int leftEnd = left; leftEnd < right; leftEnd++) {
                    next = Math.min(next,dp[left][leftEnd] + dp[leftEnd + 1][right]);
                }
                dp[left][right] = next + w(prefixSum,left,right);
            }
        }
        return dp[0][n - 1];
    }

    /**
     * 四边形不等式
     * 复杂度降一阶
     */
    public static int min3(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int n = arr.length;
        int[] prefixSum = sum(arr);
        int[][] dp = new int[n][n];
        int[][] best = new int[n][n];//最优划分点
        for (int i = 0; i < n - 1; i++) {
            //dp[2][3]表示左部分2..2,右部分是2..3
            //故最优划分点是2
            best[i][i + 1] = i;
            dp[i][i + 1] = w(prefixSum,i,i + 1);
        }
        for (int left = n - 3; left >= 0; left--) {
            for (int right = left + 2; right < n; right++) {
                int next = Integer.MAX_VALUE;
                int choose = -1;
                for (int leftEnd = best[left][right - 1]; leftEnd <= best[left + 1][right]; leftEnd++) {
                    int cur = dp[left][leftEnd] + dp[leftEnd + 1][right];
                    if (cur <= next) {
                        next = cur;
                        choose = leftEnd;
                    }
                }
                best[left][right] = choose;
                dp[left][right] = next + w(prefixSum,left,right);
            }
        }
        return dp[0][n - 1];
    }

    public static int[] sum(int[] arr) {
        int N = arr.length;
        int[] s = new int[N + 1];
        s[0] = 0;
        for (int i = 0; i < N; i++) {
            s[i + 1] = s[i] + arr[i];
        }
        return s;
    }
    public static int w(int[] sum,int left,int right) {
        //算出arr中i-j的累加和
        return sum[right + 1] - sum[left];
    }

    public static int[] randomArray(int len, int maxValue) {
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * maxValue);
        }
        return arr;
    }


    public static void main(String[] args) {
        int N = 15;
        int maxValue = 100;
        int testTime = 100000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * N);
            int[] arr = randomArray(len, maxValue);
            int ans1 = min1(arr);
            int ans2 = min2(arr);
            int ans3 = min3(arr);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("Oops!");
            }
        }
        System.out.println("测试结束");
    }
}
