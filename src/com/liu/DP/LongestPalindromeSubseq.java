package com.liu.DP;

/**
 * 给你一个字符串 s ，找出其中最长的回文子序列，并返回该序列的长度。
 * 子序列定义为：不改变剩余字符顺序的情况下，删除某些字符或者不删除任何字符形成的一个序列。
 */
public class LongestPalindromeSubseq {

    public static int longestPalindromeSubseq(String s) {
        if (s == null || s.length() == 0) return 0;
        char[] str = s.toCharArray();
        return process(str,0,str.length - 1);
    }

    private static int process(char[] str, int left, int right) {
        if (left == right) return 1;
        if (left == right - 1) {
            return str[left] == str[right] ? 2 : 1;
        }
        int p1 = process(str,left + 1, right);
        int p2 = process(str,left, right - 1);
        int p3 = process(str,left + 1, right - 1);
        int p4 = str[left] != str[right] ? 0 : (2 + process(str,left + 1,right - 1));
        return Math.max(Math.max(p1,p2),Math.max(p3,p4));
    }

    public static int longestPalindromeSubseq2(String s) {
        if (s == null || s.length() == 0) return 0;
        char[] str = s.toCharArray();
        int n = str.length;
        int [][] dp = new int[n + 1][n + 1];
        dp[n - 1][n - 1] = 1;//防止i + 1越界
        for (int i = 0; i < n - 1; i++) {
            dp[i][i] = 1;
            dp[i][i + 1] = str[i] == str[i + 1] ? 2 : 1;
        }
        for (int i = n - 3; i >= 0; i--) {//看依赖关系
            for (int j = i + 2; j < n; j++) {
                dp[i][j] = Math.max(dp[i + 1][j],dp[i][j -1]);
                if (str[i] == str[j]) {
                    dp[i][j] = Math.max(dp[i][j],2 + dp[i + 1][j - 1]);
                }
            }
        }
        return dp[0][n - 1];
     }
}
