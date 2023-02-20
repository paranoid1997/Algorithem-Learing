package com.liu.DP;

/**
 * 给定3个参数，N，M，K
 * 怪兽有N滴血，等着英雄来砍自己
 * 英雄每一次打击，都会让怪兽流失[0-M]的血量
 * 到底流失多少?每一次在[0~M]上等概率的获得一个值
 * 求K次打击之后，英雄把怪兽砍死的概率
 */
public class KillMonster {

    public static double killMonster(int n, int m, int k) {
        if (n < 1 || m < 1 || k < 1) return 0;
        long all = (long) Math.pow(m + 1,k);
        long kill = process(k,m,n);
        return (double) ((double) kill / (double) all);
    }

    private static long process(int times, int m, int hp) {
        if (times == 0) {
            return hp <= 0 ? 1 : 0;
        }
        if (hp <= 0) {
            return (long) Math.pow(m + 1,times);
        }
        long ways = 0;
        for (int i = 0; i <= m; i++) {
            ways += process(times - 1,m,hp - i);
        }
        return ways;
    }

    /**
     *暴力递归改动态规划
     */
    public static double killmonsterWithDp1(int n,int m, int k) {
        if (n < 1 || m < 1 || k < 1) return 0;
        long all = (long) Math.pow(m + 1,k);
        long [][] dp = new long[k + 1][n + 1];
        dp[0][0] = 1;
        for (int times = 1; times <= k; times++) {
            dp[times][0] = (long) Math.pow(m + 1,times);
            for (int hp = 0; hp <= n; hp++) {
                long ways = 0;
                for (int i = 0; i <= m; i++) {
                    if (hp - i >= 0) {
                        ways += dp[times - 1][hp - i];
                    }else {
                        ways += Math.pow(m + 1,times - 1);
                    }
                }
                dp[times][hp] = ways;
            }
        }
        long kill = dp[k][n];
        return (double) ((double) kill / (double) all);
    }

    /**
     *动态规划之枚举优化
     */
    public static double killmonsterWithDp2(int N, int M, int K) {
        if (N < 1 || M < 1 || K < 1) {
            return 0;
        }
        long all = (long) Math.pow(M + 1, K);
        long[][] dp = new long[K + 1][N + 1];
        dp[0][0] = 1;
        for (int times = 1; times <= K; times++) {
            dp[times][0] = (long) Math.pow(M + 1, times);
            for (int hp = 1; hp <= N; hp++) {
                dp[times][hp] = dp[times][hp - 1] + dp[times - 1][hp];
                if (hp - 1 - M >= 0) {
                    dp[times][hp] -= dp[times - 1][hp - 1 - M];
                } else {//这是dp表里面的的hp值已经小于0的情况
                    //用公式减去
                    dp[times][hp] -= Math.pow(M + 1, times - 1);
                }
            }
        }
        long kill = dp[K][N];
        return (double) ((double) kill / (double) all);
    }

    public static void main(String[] args) {
        int NMax = 10;
        int MMax = 10;
        int KMax = 10;
        int testTime = 200000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int N = (int) (Math.random() * NMax);
            int M = (int) (Math.random() * MMax);
            int K = (int) (Math.random() * KMax);
            double ans1 = killMonster(N, M, K);
            double ans2 = killmonsterWithDp1(N, M, K);
            double ans3 = killmonsterWithDp2(N, M, K);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("测试结束");
    }
}
