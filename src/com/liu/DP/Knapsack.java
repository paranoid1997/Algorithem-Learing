package com.liu.DP;

/**
 * 给定两个长度都为N的数组weights和values，
 * weights[i]和values[i]分别代表 i号物品的重量和价值。
 * 给定一个正数bag，表示一个载重bag的袋子，
 * 你装的物品不能超过这个重量。
 * 返回你能装下最多的价值是多少?
 */
public class Knapsack {

    public static int maxValue(int[] weight,int[] value,int bag) {
        if (weight == null || value == null ||
                weight.length != value.length ||
                weight.length == 0 || value.length == 0) {
            return  0;
        }
        return process(weight,value,0,bag);
    }

    private static int process(int[] weight, int[] value, int index, int rest) {

        if (rest < 0) return -1;
        if (index == weight.length) return 0;//已经没有货了，返回价值为0
        //不选了这个最大的背包
        int p1 = process(weight,value,index + 1, rest);
        //选这个背包
        int p2 = 0;
        int next = process(weight,value,index + 1,rest - weight[index]);
        if (next != -1) {
            p2 = value[index] + next;
        }
        return Math.max(p1,p2);
   }
   public static int maxValueWithDp(int[] weight,int[] value,int bag) {
       if (weight == null || value == null ||
               weight.length != value.length ||
               weight.length == 0 || value.length == 0) {
           return  0;
       }
       int n = weight.length;
       int[][] dp = new int[n + 1][bag + 1];
       //行是index,列是rest
       for (int index = n - 1; index >= 0; index--) {
           for (int rest = 0; rest <= bag; rest++) {
               int p1 = dp[index + 1][rest];
               int p2 = 0;
               int next = rest - weight[index] < 0 ? -1 : dp[index + 1][rest - weight[index]];
            if (next != -1) {
                p2 = value[index] + next;
            }
            dp[index][rest] = Math.max(p1,p2);
           }
       }
       return dp[0][bag];
   }

    public static void main(String[] args) {
        int[] weights = { 3, 2, 4, 7, 3, 1, 7 };
        int[] values = { 5, 6, 3, 19, 12, 4, 2 };
        int bag = 15;
        System.out.println(maxValue(weights, values, bag));
        System.out.println(maxValueWithDp(weights, values, bag));
    }
}
