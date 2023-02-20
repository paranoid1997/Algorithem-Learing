package com.liu.DP;

/**
 * 给定一个正数1，裂开的方法有一种，(1) 给定一个正数2，
 * 裂开的方法有两种，(1和1)、(2) 给定一个正数3，裂开的方法有三种，
 * (1、1、1)、(1、2)、(3) 给定一个正数4，裂开的方法有五种，
 * (1、1、1、1)、(1、1、2)、(1、3)、(2、2)、 (4)
 * 要求后面的数要大于等于前面的数
 * 给定一个正数n，求裂开的方法数。
 */
public class SplitNumber {

    public static int splitWays(int n) {
        if (n < 0) return 0;
        if (n == 1) return 1;
        return process(1,n);
    }

    private static int process(int pre, int rest) {
        if (rest == 0) return 1;
        if (pre == rest) return 1;
        if (pre > rest) return 0;
        int ways = 0;
        //pre < rest时候
        for (int first = pre; first <= rest; first++) {
            ways += process(first,rest - first);
        }
        return ways;
    }

    public static int splitWaysWithDp1(int n){
        if (n < 0) return 0;
        if (n == 1) return 1;
        int[][] dp = new int[n + 1][n + 1];
        for (int pre = 1; pre <= n; pre++) {
            dp[pre][0] = 1;
            dp[pre][pre] = 1;
        }
        for (int pre = n - 1; pre >= 1; pre--) {
            //pre和rest的关系满足:pre < rest
            for (int rest = pre + 1; rest <= n; rest++) {
                int ways = 0;
                for (int first = pre; first <= rest; first++) {
                    ways += dp[first][rest - first];
                }
                dp[pre][rest] = ways;
            }
        }
        return dp[1][n];
    }
    public static int splitWaysWithDp2(int n) {
        if (n < 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        int[][] dp = new int[n + 1][n + 1];
        for (int pre = 1; pre <= n; pre++) {
            dp[pre][0] = 1;
            dp[pre][pre] = 1;
        }
        for (int pre = n - 1; pre >= 1; pre--) {
            for (int rest = pre + 1; rest <= n; rest++) {
                dp[pre][rest] = dp[pre + 1][rest];
                dp[pre][rest] += dp[pre][rest - pre];
            }
        }
        return dp[1][n];
    }
    public static void main(String[] args) {
        int test = 39;
        System.out.println(splitWays(test));
        System.out.println(splitWaysWithDp1(test));
        System.out.println(splitWaysWithDp2(test));
    }
}
