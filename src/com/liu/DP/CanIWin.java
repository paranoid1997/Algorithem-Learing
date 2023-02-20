package com.liu.DP;

/**
 * 在 "100 game" 这个游戏中，两名玩家轮流选择从 1 到 10 的任意整数，累计整数和，
 * 先使得累计整数和 达到或超过100 的玩家，即为胜者。
 * 如果我们将游戏规则改为 “玩家 不能 重复使用整数” 呢？
 * 例如，两个玩家可以轮流从公共整数池中抽取从 1 到 15 的整数（不放回），直到累计整数和 >= 100。
 * 给定两个整数 maxChoosableInteger （整数池中可选择的最大数）和 desiredTotal（累计和），
 * 若先出手的玩家是否能稳赢则返回 true，否则返回 false 。假设两位玩家游戏时都表现 最佳 。
 */
public class CanIWin {

    // 1~choose 拥有的数字
    // total 一开始的剩余
    // 返回先手会不会赢

    /**
     * 暴力递归
     * 时间复杂度O(N!)
     */
    public static boolean canIWin1(int choose,int total) {
        if (total == 0) {
            return true;//题目规定
        }
        if (choose * (choose + 1) / 2 < total) {
            return false;//题目规定
        }
        int[] arr = new int[choose];
        for (int i = 0; i < choose; i++) {
            arr[i] = i + 1;//1~choose
        }
        return process(arr,total);
    }
    // 当前轮到先手拿，
    // 先手只能选择在arr中还存在的数字，
    // 还剩rest这么值，
    // 返回先手会不会赢
    private static boolean process(int[] arr, int rest) {
        if (rest <= 0) {
            //先手输了
            return false;
        }
        //先手尝试所有的情况
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != -1) {
                //-1代表已经拿过了这个数字
                int cur = arr[i];
                arr[i] = -1;
                boolean next = process(arr,rest - cur);
                arr[i] = cur;//递归完成之后再状态还原
                if (!next) {
                    //表示递归子过程后手输了
                    //那么下一次必定就是先手赢了
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * 暴力尝试改动态规划
     */
    public static boolean canIWin2(int choose, int total) {
        if (total == 0) {
            return true;
        }
        if ((choose * (choose + 1) >> 1) < total) {
            return false;
        }
        //0 ~ 11111
        int[] dp = new int[1 << (choose + 1)];
        // dp[status] == 1  true
        // dp[status] == -1  false
        // dp[status] == 0  process(status) 没算过！去算！
        return process2(choose, 0, total, dp);
    }
    // 为什么明明status和rest是两个可变参数，却只用status来代表状态(也就是dp)
    // 因为选了一批数字之后，得到的和一定是一样的，所以rest是由status决定的，所以rest不需要参与记忆化搜索
    private static boolean process2(int choose, int status, int rest, int[] dp) {
        if (dp[status] != 0) {
            return dp[status] == 1 ? true : false;
        }
        boolean ans = false;
        if (rest > 0) {
            for (int i = 1; i <= choose; i++) {
                if (((1 << i) & status) == 0) {
                    //如果第i为表示0，则可以去拿
                    if (!process2(choose,(status|(1 << i)),rest - i,dp)) {
                        //status|(1 << i)表示拿完这个数，这个数就不好再拿了
                        //然后把这位设置为1
                        //子过程后手输了，先手才能赢
                        ans = true;
                        break;
                    }
                }
            }
        }
        dp[status] = ans ? 1 : -1;//傻缓存
        return ans;
    }
}