package com.liu.DP;

/**
 * 给出一些不同颜色的盒子 boxes ，盒子的颜色由不同的正数表示。
 * 你将经过若干轮操作去去掉盒子，直到所有的盒子都去掉为止。
 * 每一轮你可以移除具有相同颜色的连续 k 个盒子（k >= 1），这样一轮之后你将得到 k * k 个积分。
 * 返回 你能获得的最大积分和。
 * 示例 1：
 * 输入：boxes = [1,3,2,2,2,3,4,3,1]
 * 输出：23
 * 解释：
 * [1, 3, 2, 2, 2, 3, 4, 3, 1]
 * ----> [1, 3, 3, 4, 3, 1] (3*3=9 分)
 * ----> [1, 3, 3, 3, 1] (1*1=1 分)
 * ----> [1, 1] (3*3=9 分)
 * ----> [] (2*2=4 分)
 */
public class RemoveBoxes {
    /**
     * 纯暴力版本
     */
    public static int removeBox0(int[] arr,int left,int right,int k) {
        if (left > right) return 0;
        //前面k个数和当前这个数一起消掉
        int ans = removeBox0(arr,left + 1,right,0) + (k + 1) * (k + 1);
        //前面有k个数和这个数暂时不消去
        //等待后面还有k这个数的时候在消去
        for (int i = left + 1; i <= right; i++) {
            if (arr[i] == arr[left]) {
                //先消去中间的，在消去前面k个数
                ans = Math.max(ans,removeBox0(arr,left + 1,i - 1,0) + removeBox0(arr,i,right,k + 1));
            }
        }
        return ans;
    }

    /**
     * 暴力递归改动态规划
     * 主要是加了一个记忆化缓存 就能leecode上AC了
     */
    public static int removeBox1(int[] boxes) {
        int n = boxes.length;
        int[][][] dp = new int[n + 1][n + 1][n + 1];
        int ans = process1(boxes,0,n - 1,0,dp);
        return ans;
    }

    private static int process1(int[] boxes, int left, int right, int k, int[][][] dp) {
        if (left > right) return 0;
        if (dp[left][right][k] > 0) return dp[left][right][k];//记忆化缓存
        int ans = process1(boxes,left + 1,right,0,dp) + (k + 1) * (k + 1);
        //前面有k个数和这个数暂时不消去
        //等待后面还有k这个数的时候在消去
        for (int i = left + 1; i <= right; i++) {
            if (boxes[i] == boxes[left]) {
                //先消去中间的，在消去前面k个数
                ans = Math.max(ans,process1(boxes,left + 1,i - 1,0,dp) + process1(boxes,i,right,k + 1,dp));
            }
        }
        dp[left][right][k] = ans;
        return ans;
    }
}
