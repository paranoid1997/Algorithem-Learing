package com.liu.DP;

/**
 * 你有无限的1 x 2的砖块，
 * 要铺满M x N的区域，不同的铺法有多少种?
 */
public class PavingTile {

    public static int ways1(int n, int m) {
        if (n < 1 || m < 1 || ((n * m) & 1) != 0) {
            return 0;
        }
        if (n == 1 || m == 1) {
            return 1;
        }
        int[] pre = new int[m]; // pre代表-1行的状况
        for (int i = 0; i < pre.length; i++) {
            pre[i] = 1;
        }
        return process(pre, 0, n);
    }
    // pre 表示level-1行的状态
    // level表示，正在level行做决定
    // N 表示一共有多少行 固定的
    // level-2行及其之上所有行，都摆满砖了
    // level做决定，让所有区域都满，方法数返回
    public static int process(int[] pre, int level, int n) {
        if (level == n) { // base case
            for (int i = 0; i < pre.length; i++) {
                if (pre[i] == 0) {
                    //如果上层瓷砖有一个不满
                    //则返回0种方法
                    return 0;
                }
            }
            return 1;
        }

        // 没到终止行，可以选择在当前的level行摆瓷砖
        int[] op = getOp(pre);//根据上一行的状态，决定此时的操作
        return dfs(op, 0, level, n);
    }
    // op[i] == 0 可以考虑摆砖
    // op[i] == 1 只能竖着向上
    public static int dfs(int[] op, int col, int level, int N) {
        // 在列上自由发挥，玩深度优先遍历，当col来到终止列，i行的决定做完了
        // 轮到i+1行，做决定
        if (col == op.length) {
            //子过程调用父过程
            //既用下一行来判断上一行来作决定
            return process(op, level + 1, N);
        }
        int ans = 0;
        // col位置不横摆
        ans += dfs(op, col + 1, level, N); // col位置上不摆横转
        // col位置横摆, 向右
        if (col + 1 < op.length && op[col] == 0 && op[col + 1] == 0) {
            //如果发现这一列和下一列均有位置可以摆横转
            op[col] = 1;//摆砖
            op[col + 1] = 1;//摆砖
            ans += dfs(op, col + 2, level, N);
            op[col] = 0;//恢复现场
            op[col + 1] = 0;//恢复现场
        }
        return ans;
    }
    public static int[] getOp(int[] pre) {
        int[] cur = new int[pre.length];
        for (int i = 0; i < pre.length; i++) {
            //上行为0的，这行都为1
            //上行为1的，这行全为0
            cur[i] = pre[i] ^ 1;
        }
        return cur;
    }
}
