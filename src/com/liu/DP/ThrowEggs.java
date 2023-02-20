package com.liu.DP;

/**
 * 给你 k 枚相同的鸡蛋，并可以使用一栋从第 1 层到第 n 层共有 n 层楼的建筑。
 * 已知存在楼层 f ，满足 0 <= f <= n ，任何从 高于 f 的楼层落下的鸡蛋都会碎，
 * 从 f 楼层或比它低的楼层落下的鸡蛋都不会破。
 * 每次操作，你可以取一枚没有碎的鸡蛋并把它从任一楼层 x 扔下（满足 1 <= x <= n）。
 * 如果鸡蛋碎了，你就不能再次使用它。如果某枚鸡蛋扔下后没有摔碎，则可以在之后的操作中 重复使用 这枚鸡蛋。
 * 请你计算并返回要确定 f 确切的值 的 最小操作次数 是多少？
 */
public class ThrowEggs {

    /**
     * 暴力递归
     */
    public static int superEggDrop1(int kEggs, int nLevel) {
        if (nLevel < 1 || kEggs < 1) {
            return 0;
        }
        return process1(nLevel, kEggs);
    }
    // rest还剩多少层楼需要去验证
    // k还有多少颗鸡蛋能够使用
    // 一定要验证出最高的不会碎的楼层！但是每次都是坏运气。
    // 返回至少需要扔几次？
    private static int process1(int restLevel, int kEggs) {
        if (restLevel == 0) {
            return 0;
        }
        if (kEggs == 1) {
            return restLevel;
        }
        int min = Integer.MAX_VALUE;
        for (int i = 1; i <= restLevel; i++) {
            min = Math.min(min,Math.max(process1(i - 1,kEggs - 1),
                    process1(restLevel - i,kEggs)));
        }
        //碎的一次 + 没碎的一次 + 扔的一次
        return min + 1;
    }

    /**
     * 动态规划之有枚举
     */
    public static int superEggDrop2(int kEggs, int nLevel) {
        if (nLevel < 1 || kEggs < 1) {
            return 0;
        }
        if (kEggs == 1) {
            return nLevel;
        }
        int[][] dp = new int[nLevel + 1][kEggs + 1];
        for (int i = 1; i < dp.length; i++) {
            dp[i][1] = i;
        }
        for (int i = 1; i <= nLevel; i++) {
            for (int j = 2; j    <= kEggs ; j++) {
                int min = Integer.MAX_VALUE;
                for (int k = 1; k <= i; k++) {
                    min = Math.min(min,Math.max(dp[k - 1][j - 1],dp[i - k][j]));
                }
                dp[i][j] = min + 1;
            }
        }
        return dp[nLevel][kEggs];
    }
    /**
     * 动态规划之四边形不等式
     * 上 + 右
     */
    public static int superEggDrop3(int kEggs, int nLevel) {
        if (nLevel < 1 || kEggs < 1) {
            return 0;
        }
        if (kEggs == 1) {
            return nLevel;
        }
        int[][] dp = new int[nLevel + 1][kEggs + 1];
        for (int i = 1; i != dp.length; i++) {
            dp[i][1] = i;
        }
        int[][] best = new int[nLevel + 1][kEggs + 1];
        for (int i = 1; i != dp[0].length; i++) {
            dp[1][i] = 1;
            best[1][i] = 1;
        }
        for (int i = 2; i <= nLevel; i++) {
            for (int j = kEggs; j > 1 ; j--) {
                int ans = Integer.MAX_VALUE;
                int bestChoose = -1;
                int down = best[i - 1][j];//上
                int up = j == kEggs ? i : best[i][j + 1];//右
                for (int k = down; k <= up; k++) {
                    int cur = Math.max(dp[k - 1][j - 1],dp[i - k][j]);
                    if (cur <= ans) {
                        ans = cur;
                        bestChoose = k;
                    }
                }
                dp[i][j] = ans + 1;
                best[i][j] = bestChoose;
            }
        }
        return dp[nLevel][kEggs];
    }

    public static int superEggDrop4(int kChess, int nLevel) {
        if (nLevel < 1 || kChess < 1) {
            return 0;
        }
        int[] dp = new int[kChess];
        int res = 0;
        while (true) {
            res++;
            int previous = 0;
            for (int i = 0; i < dp.length; i++) {
                int tmp = dp[i];
                dp[i] = dp[i] + previous + 1;
                previous = tmp;
                if (dp[i] >= nLevel) {
                    return res;
                }
            }
        }
    }

    public static int superEggDrop5(int kChess, int nLevel) {
        if (nLevel < 1 || kChess < 1) {
            return 0;
        }
        int bsTimes = log2N(nLevel) + 1;
        if (kChess >= bsTimes) {
            return bsTimes;
        }
        int[] dp = new int[kChess];
        int res = 0;
        while (true) {
            res++;
            int previous = 0;
            for (int i = 0; i < dp.length; i++) {
                int tmp = dp[i];
                dp[i] = dp[i] + previous + 1;
                previous = tmp;
                if (dp[i] >= nLevel) {
                    return res;
                }
            }
        }
    }
    public static int log2N(int n) {
        int res = -1;
        while (n != 0) {
            res++;
            n >>>= 1;
        }
        return res;
    }

    public static void main(String[] args) {
        int maxN = 500;
        int maxK = 30;
        int testTime = 10000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int N = (int) (Math.random() * maxN) + 1;
            int K = (int) (Math.random() * maxK) + 1;
            int ans2 = superEggDrop2(K, N);
            int ans3 = superEggDrop3(K, N);
            int ans4 = superEggDrop4(K, N);
            int ans5 = superEggDrop5(K, N);
            if (ans2 != ans3 || ans4 != ans5 || ans2 != ans4) {
                System.out.println("出错了!");
            }
        }
        System.out.println("测试结束");
    }

}
