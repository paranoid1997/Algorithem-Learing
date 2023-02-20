package com.liu.DP;

/**
 * 给定一个二维数组matrix，可以从任何位置出发，每一步可以走向上、下、左、右，四个方向。返回最大递增链的长度。
 * 例子：
 * matrix =
 * 5 4 3
 * 3 1 2
 * 2 1 3
 * 从最中心的1出发，是可以走出1 2 3 4 5的链的，而且这是最长的递增链。所以返回长度5
 */
public class LongestIncreasingPath {

    /**
     * 暴力递归、
     * 在leecode上会超出时间限制
     */
    public static int longestIncreasingPath1(int[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;
        int ans = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                ans = Math.max(ans,process1(matrix,i,j));
            }
        }
        return ans;
    }

    private static int process1(int[][] matrix, int i, int j) {
        if (i < 0 || i == matrix.length || j < 0 || j == matrix[0].length) {
            return 0;
        }
        int up = i > 0 && matrix[i][j] < matrix[i - 1][j] ? process1(matrix,i - 1,j) : 0;
        int down = i < (matrix.length - 1) && matrix[i][j] < matrix[i + 1][j] ? process1(matrix,i + 1,j) : 0;
        int left = j > 0 && matrix[i][j] < matrix[i][j - 1] ? process1(matrix,i,j - 1) : 0;
        int right = j < (matrix[0].length - 1 ) && matrix[i][j] < matrix[i][j + 1] ? process1(matrix,i,j + 1) : 0;
        return Math.max(Math.max(up,down),Math.max(left,right)) + 1;//后续加上自身的1个
    }


    /**
     * 记忆化搜索
     */
    public static int longestIncreasingPath2(int[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;
        int ans = 0;
        int[][] dp = new int[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                ans = Math.max(ans,process2(matrix,i,j,dp));
            }
        }
        return ans;
    }

    private static int process2(int[][] matrix, int i, int j,int[][] dp) {
        if (i < 0 || i == matrix.length || j < 0 || j == matrix[0].length) {
            return 0;
        }
        if (dp[i][j] != 0) return dp[i][j];
        int up = i > 0 && matrix[i][j] < matrix[i - 1][j] ? process2(matrix,i - 1,j,dp) : 0;
        int down = i < (matrix.length - 1) && matrix[i][j] < matrix[i + 1][j] ? process2(matrix,i + 1,j,dp) : 0;
        int left = j > 0 && matrix[i][j] < matrix[i][j - 1] ? process2(matrix,i,j - 1,dp) : 0;
        int right = j < (matrix[0].length - 1 ) && matrix[i][j] < matrix[i][j + 1] ? process2(matrix,i,j + 1,dp) : 0;
        int ans = Math.max(Math.max(up,down),Math.max(left,right)) + 1;//后续加上自身的1个

        //加记忆化缓存
        dp[i][j] = ans;
        return ans;
    }
}
