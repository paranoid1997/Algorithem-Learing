package com.liu.DP;

/**假设有排成一行的N个位置，记为1~N，N 一定大于或等于 2
 开始时机器人在其中的M位置上(M 一定是 1~N 中的一个)
 如果机器人来到1位置，那么下一步只能往右来到2位置；
 如果机器人来到N位置，那么下一步只能往左来到 N-1 位置；
 如果机器人来到中间位置，那么下一步可以往左走或者往右走；
 规定机器人必须走 K 步，最终能来到P位置(P也是1~N中的一个)的方法有多少种
 给定四个参数 N、M、K、P，返回方法数。
 */
public class RobotWalk {

    public static int robotWalk1(int n,int start,int aim,int k) {
        if (n < 2 || start < 1 || start > n || aim < 1 || aim > n || k < 1 ) {
            return -1;
        }
        return process1(start,k,aim,n);
    }

    private static int process1(int cur, int res, int aim, int n) {
        if (res == 0) {
            //已经不需要走了
            return cur == aim ? 1 : 0;
        }
        if (cur == 1) {
            return process1(2,res - 1,aim,n);
        }
        if (cur == n) {
            return process1(n - 1,res - 1,aim,n);
        }
        return process1(cur - 1,res - 1,aim,n) + process1(cur + 1,res - 1,aim,n);
    }


    public static int robotWalk2(int N, int start, int aim, int K) {
        if (N < 2 || start < 1 || start > N || aim < 1 || aim > N || K < 1) {
            return -1;
        }
        int[][] dp = new int[N + 1][K + 1];
        for (int i = 0; i <= N; i++) {
            for (int j = 0; j <= K; j++) {
                dp[i][j] = -1;
            }
        }
        // dp就是缓存表
        // dp[cur][rest] == -1 -> process1(cur, rest)之前没算过！
        // dp[cur][rest] != -1 -> process1(cur, rest)之前算过！返回值，dp[cur][rest]
        // N+1 * K+1
        return process2(start, K, aim, N, dp);
    }

    // cur 范: 1 ~ N
    // rest 范：0 ~ K
    public static int process2(int cur, int rest, int aim, int N, int[][] dp) {
        if (dp[cur][rest] != -1) {
            //缓存命中
            return dp[cur][rest];
        }
        // 之前没算过！
        int ans = 0;
        if (rest == 0) {
            ans = cur == aim ? 1 : 0;
        } else if (cur == 1) {
            ans = process2(2, rest - 1, aim, N, dp);
        } else if (cur == N) {
            ans = process2(N - 1, rest - 1, aim, N, dp);
        } else {
            ans = process2(cur - 1, rest - 1, aim, N, dp) + process2(cur + 1, rest - 1, aim, N, dp);
        }
        dp[cur][rest] = ans;
        return ans;
    }

    public static int robotWalkWithDp(int N,int start,int aim,int K) {
        if (N < 2 || start < 1 || start > N || aim < 1 || aim > N || K < 1) {
            return -1;
        }
        //生成dp依赖表
        //dp的行代表的是cur,列代表的是rest
        int[][] dp = new int[N + 1][K + 1];
        dp[aim][0] = 1;
        for (int rest = 1; rest <= K; rest++) {
            dp[1][rest] = dp[2][rest - 1];
            for (int cur = 2;cur < N;cur++) {
                dp[cur][rest] = dp[cur - 1][rest - 1] + dp[cur + 1][rest - 1];
            }
            dp[N][rest] = dp[N - 1][rest - 1];
        }
        return dp[start][K];
    }

    public static void main(String[] args) {
        System.out.println(robotWalk1(5, 2, 4, 6));
        System.out.println(robotWalk2(5, 2, 4, 6));
        System.out.println(robotWalkWithDp(5, 2, 4, 6));
    }
}
