package com.liu.DP;

public class IsInterleave {
    public boolean isInterleave(String s1, String s2, String s3) {
        if (s1 == null || s2 == null || s3 == null) {
            return false;
        }
        if (s3.length() != s1.length() + s2.length()) {
            return false;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        char[] str3 = s3.toCharArray();
        boolean[][] dp = new boolean[str1.length + 1][str2.length + 1];
        dp[0][0] = true;

        for (int i = 1; i <= s1.length(); i++) {
            if (str1[i - 1] != str3[i - 1]) {
                break;
            }
            dp[i][0] = true;
        }

        for (int j = 1; j <= s2.length(); j++) {
            if (str2[j - 1] != str3[j - 1]) {
                break;
            }
            dp[0][j] = true;
        }

        for (int i = 1; i <= str1.length; i++) {
            for (int j = 1; j <= str2.length; j++) {
                if (str1[i - 1] == str3[i+j-1] && dp[i - 1][j] || str2[j - 1] == str3[i+j-1] && dp[i][j - 1]) {
                    //假设str1和str3的最后一个字符是相等的
                    //str1.length 除最后一个字符外前面所有字符和str2全部的字符都可以
                    //交错成str3除最后的字符前面所有的字符 = dp[i - 1][j]表示的含义
                    dp[i][j] = true;
                }
            }
        }
        return dp[str1.length][str2.length];
    }
}
