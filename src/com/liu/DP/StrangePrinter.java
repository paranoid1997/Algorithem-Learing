package com.liu.DP;

/**
 * 有台奇怪的打印机有以下两个特殊要求：
 * 打印机每次只能打印由 同一个字符 组成的序列。
 * 每次可以在从起始到结束的任意位置打印新字符，并且会覆盖掉原来已有的字符。
 * 给你一个字符串 s ，你的任务是计算这个打印机打印它需要的最少打印次数。 
 * 示例 1：
 *
 * 输入：s = "aaabbb"
 * 输出：2
 * 解释：首先打印 "aaa" 然后打印 "bbb"。
 * 示例 2：
 *
 * 输入：s = "aba"
 * 输出：2
 * 解释：首先打印 "aaa" 然后在第二个位置打印 "b" 覆盖掉原来的字符 'a'。

 */
public class StrangePrinter {
    /**
     * 暴力递归
     */
    public static int strangePrinter1(String s) {
        if (s == null || s.length() == 0) return 0;
        char[] str = s.toCharArray();
        return process1(str,0,str.length -1);
    }

    private static int process1(char[] str, int left, int right) {
        if (left == right) return 1;
        int ans = right -left + 1;
        for (int i = left + 1; i <= right; i++) {
            //在L..i - 1和i...right这个区间进行递归划分来寻找最小转数
            //如果左边第一个字母等于右边区间第一个字母，则可以合并成一次去打印
            //所以要减去1
            ans = Math.min(ans,process1(str,left,i - 1) +
                    process1(str,i,right) - (str[left] == str[i] ? 1 : 0));
        }
        return ans;
    }

    /**
     * 暴力递归改动态规划
     */
    public static int strangePrinter2(String s) {
        if (s == null || s.length() == 0) return 0;
        char[] str = s.toCharArray();
        int n = str.length;
        int [][] dp= new int[n][n];
        dp[n - 1][n - 1] = 1;
        for (int i = 0; i < n - 1; i++) {
            dp[i][i] = 1;
        }
        for (int left = n - 1; left >= 0; left--) {
            for (int right = left + 1; right < n; right++) {
                dp[left][right] = right - left + 1;
                for (int i = left + 1; i <= right; i++) {
                    dp[left][right] = Math.min(dp[left][right],dp[left][i - 1] + dp[i][right] - (str[left] == str[i] ? 1 : 0));
                }
            }
        }
        return dp[0][n -1];

    }
}
