package com.liu.DP;

public class EditCost {

    public int minDistance(String word1, String word2) {
        return mincost(word1,word2,1,1,1);
    }

    private int mincost(String s1, String s2, int add, int del, int replace) {
        if (s1 == null || s2 == null) {
            return 0;
        }
        char[] str1 = s1.toCharArray();//行
        char[] str2 = s2.toCharArray();//列
        int n = str1.length + 1;
        int m = str2.length + 1;
        int[][] dp = new int[n][m];
        for (int i = 1; i < n; i++) {
            dp[i][0] = i * del;
        }
        for (int i = 1; i < m; i++) {
            dp[0][i] = i * add;
        }

        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                if (str1[i - 1] == str2[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1];
                }else {
                    dp[i][j] = dp[i - 1][j - 1] + replace;
                }
                dp[i][j] = Math.min(dp[i][j],dp[i - 1][j] + del);
                dp[i][j] = Math.min(dp[i][j],dp[i][j - 1] + add);
            }
        }

        return dp[n - 1][m - 1];
    }
}
