package com.liu.DP;

/**
 * 给定5个参数，N，M，row，col，k
 * 表示在N*M的区域上，醉汉Bob初始在(row,col)位置
 * Bob一共要迈出k步，且每步都会等概率向上下左右四个方向走一个单位
 * 任何时候Bob只要离开N*M的区域，就直接死亡
 * 返回k步之后，Bob还在N*M的区域的概率
 */
public class BobDie {

    public static double livePosibility(int row, int col, int k, int n, int m) {
        return (double) process(row,col,k,n,m) / Math.pow(4,k);
    }

    private static long process(int row, int col, int rest, int n, int m) {
        if (row < 0 || row == n || col < 0 || col == m) return 0;
        if (rest == 0) return 1;//如果Bob还在棋盘中
        long up = process(row - 1, col, rest - 1, n, m);
        long down = process(row + 1, col, rest - 1,  n, m);
        long left = process(row, col - 1, rest - 1, n, m);
        long right = process(row, col + 1, rest - 1,  n, m);
        return up + down + left + right;
    }

    public static double livePosibilityWithDp(int row, int col, int k, int n, int m) {
        long[][][] dp = new long[n][m][k + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                dp[i][j][0] = 1;
            }
        }
        for (int rest = 1; rest <= k; rest++) {
            for (int r = 0; r < n; r++) {
                for (int c = 0; c < m; c++) {
                    dp[r][c][rest] = pick(dp, n, m, r - 1, c, rest - 1);
                    dp[r][c][rest] += pick(dp, n, m, r + 1, c, rest - 1);
                    dp[r][c][rest] += pick(dp, n, m, r, c - 1, rest - 1);
                    dp[r][c][rest] += pick(dp, n, m, r, c + 1, rest - 1);
                }
            }
        }
        return (double) dp[row][col][k] / Math.pow(4, k);
    }

    public static long pick(long[][][] dp, int N, int M, int r, int c, int rest) {
        if (r < 0 || r == N || c < 0 || c == M) {
            return 0;
        }
        return dp[r][c][rest];

    }

    public static void main(String[] args) {
        System.out.println(livePosibility(6, 6, 10, 50, 50));
        System.out.println(livePosibilityWithDp(6, 6, 10, 50, 50));
    }
}
