package com.liu.DP;

/**
 * 给定一个数组 arr，代表一排有分数的气球。
 * 每打爆一个气球都能获得分数，假设打爆气球的分数为 X，
 * 获得分数的规则如下:
 * 1)如果被打爆气球的左边有没被打爆的气球，找到离被打爆气球最近的气球，假设分数为 L;
 * 如果被打爆气球的右边有没被打爆的气球，找到离被打爆气球最近的气球，假设分数为 R。
 * 获得分数为 L*X*R。
 * 2)如果被打爆气球的左边有没被打爆的气球，找到离被打爆气球最近的气球，假设分数为 L;
 * 如果被打爆气球的右边所有气球都已经被打爆。获得分数为 L*X。
 * 3)如果被打爆气球的左边所有的气球都已经被打爆;如果被打爆气球的右边有没被打爆的气球，找到离被打爆气球最近的气球，假设分数为 R;
 * 如果被打爆气球的右边所有气球都 已经 被打爆。获得分数为 X*R。
 * 4)如果被打爆气球的左边和右边所有的气球都已经被打爆。获得分数为 X。
 * 目标是打爆所有气球，获得每次打爆的分数。通过选择打爆气球的顺序，可以得到不同的总分，请返回能获得的最大分数。
 */
public class BurstBalloons {
    /**
     * 暴力递归
     * 核心思想:设第i位气球最后被打爆
     */
    public static int maxCoins1(int[] arr) {
            if (arr.length == 0 || arr == null) return 0;
            int n = arr.length;
            int[] helper = new int[n + 2];
            helper[0] = 1;
            helper[n + 1] = 1;
            for (int i = 1; i <= n; i++) {
                helper[i] = arr[i - 1];
            }
            return func(helper, 1, n);
    }

    // L-1位置，和R+1位置，永远不越界，并且，[L-1] 和 [R+1] 一定没爆呢！
    // 返回，arr[L...R]打爆所有气球，最大得分是什么
    private static int func(int[] helper, int left, int right) {
        if (left == right) {
            return helper[left - 1] * helper[left] * helper[right + 1];
        }
        // 尝试每一种情况，最后打爆的气球，
        // 是什么位置
        // L位置的气球，最后打爆
        int max = func(helper,left + 1,right) + helper[left - 1] * helper[left] * helper[right + 1];
        //R的位置最后打爆
        max = Math.max(max,func(helper,left,right - 1) + helper[left - 1] * helper[right] * helper[right + 1]);
        //尝试[left + 1, right - 1]的位置
        for (int i = left + 1; i < right; i++) {
            //i位置的气球最后被打爆
            int lefti = func(helper,left,i - 1);
            int righti = func(helper,i + 1,right);
            //打爆i
            int last = helper[left - 1] * helper[i] * helper[right + 1];
            int cur = lefti + righti + last;
            max = Math.max(max,cur);
        }
        return max;
    }

    /**
     * 解法二 :简单的暴力递归改动态规划
     */
    public static int maxCoins2(int[] arr) {
        if (arr.length == 0 || arr == null) return 0;
        if (arr.length == 1) return arr[0];
        int n = arr.length;
        int[] helper = new int[n + 2];
        helper[0] = 1;
        helper[n + 1] = 1;
        for (int i = 1; i <= n; i++) {
            helper[i] = arr[i - 1];
        }
        int[][] dp = new int[n + 2][n + 2];
        for (int i = 1; i <= n; i++) {
            dp[i][i] = helper[i - 1] * helper[i] * helper[i + 1];
        }
        for (int left = n; left >= 1; left--) {
            for (int right = left + 1; right <= n; right++) {
                int max = dp[left + 1][right] + helper[left - 1] * helper[left] * helper[right + 1];
                max = Math.max(max,dp[left][right - 1] + helper[left - 1] * helper[right] * helper[right + 1]);
                for (int i = left + 1; i < right; i++) {
                    //i最后打爆
                    max = Math.max(max,dp[left][i - 1] + dp[i + 1][right] + helper[left - 1] * helper[i] * helper[right + 1]);
                }
                dp[left][right] = max;
            }
        }
        return dp[1][n];
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        return arr;
    }
    public static void main(String[] args) {
        int N = 20;
        int max = 30;
        int testTime = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * N);
            int[] arr = generateRandomArray(len, max);
            int ans1 = maxCoins1(arr);
            int ans2 = maxCoins2(arr);
            if (ans1 != ans2) {
                System.out.println("Oops!");
            }
        }
        System.out.println("测试结束");
    }
}
