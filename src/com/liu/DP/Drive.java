package com.liu.DP;

import java.util.Arrays;

/**
 * 现有司机N * 2人，调度中心会将所有司机平分给A、B两个区域
 * 第 i 个司机去A可得收入为income[i][0]，
 * 第 i 个司机去B可得收入为income[i][1]，
 * 返回所有调度方案中能使所有司机总收入最高的方案，是多少钱?
 */
public class Drive {

    /**
     * 暴力递归
     */
    public static int maxMoney1(int[][] income) {
        if (income == null || income.length == 1 || income.length % 2 != 0) {
            return 0;
        }
        int M = income.length /2;//往A区域调度
        return recursion(income,0,M);
    }

    private static int recursion(int[][] income, int index, int rest) {
        if (index == income.length) {
            return 0;
        }

        //说明B区域已经名额用满了，剩下的只能往A区域调度
        if (income.length - index == rest) {
            return income[index][0] + recursion(income,index + 1,rest - 1);
        }
        //A区域名额用光了，往B区域调度
        if (rest == 0) {
            return income[index][1] + recursion(income, index + 1,rest);
        }
        //A,B区域均有名额可以调度
        int p1 = income[index][0] + recursion(income,index + 1,rest - 1);
        int p2 = income[index][1] + recursion(income, index + 1,rest);
        return Math.max(p1,p2);
    }


    /**
     * 暴力递归改动态规划
     */
    public static int maxMoney2(int[][] income) {
        if (income == null || income.length == 1 || income.length % 2 != 0) {
            return 0;
        }
        int M = income.length /2;
        int N = income.length;
        int[][] dp = new int[N + 1][M + 1];
        for (int index = N - 1; index >= 0 ; index--) {
            for (int rest = 0; rest <= M; rest++) {
                if (N - index == rest) {
                    //往A上调度
                    dp[index][rest] = income[index][0] + dp[index + 1][rest - 1];
                }else if(rest == 0) {
                    dp[index][rest] = income[index][1] + dp[index + 1][rest];
                }else {
                    int p1 = income[index][0] + dp[index + 1][rest - 1];
                    int p2 = income[index][1] + dp[index + 1][rest];
                    dp[index][rest] = Math.max(p1,p2);
                }
            }
        }
        return dp[0][M];
    }


    // 返回随机len*2大小的正数矩阵
    // 值在0~value-1之间
    public static int[][] randomMatrix(int len, int value) {
        int[][] ans = new int[len << 1][2];
        for (int i = 0; i < ans.length; i++) {
            ans[i][0] = (int) (Math.random() * value);
            ans[i][1] = (int) (Math.random() * value);
        }
        return ans;
    }

    // 这题有贪心策略 :
    // 假设一共有10个司机，思路是先让所有司机去A，得到一个总收益
    // 然后看看哪5个司机改换门庭(去B)，可以获得最大的额外收益
    // 这道题有贪心策略，打了我的脸
    // 但是我课上提到的技巧请大家重视
    // 根据数据量猜解法可以省去大量的多余分析，节省时间
    public static int maxMoney3(int[][] income) {
        int N = income.length;
        int[] arr = new int[N];
        int sum = 0;
        for (int i = 0; i < N; i++) {
            arr[i] = income[i][1] - income[i][0];
            sum += income[i][0];
        }
        Arrays.sort(arr);
        int M = N >> 1;
        for (int i = N - 1; i >= M; i--) {
            sum += arr[i];
        }
        return sum;
    }

    public static void main(String[] args) {
        int N = 10;
        int value = 100;
        int testTime = 50000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * N) + 1;
            int[][] matrix = randomMatrix(len, value);
            int ans1 = maxMoney1(matrix);
            int ans2 = maxMoney2(matrix);
            int ans3 = maxMoney3(matrix);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                System.out.println("Oops!");
            }
        }
        System.out.println("测试结束");
    }
}
