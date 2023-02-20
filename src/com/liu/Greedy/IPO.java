package com.liu.Greedy;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 输入: 正数数组costs、正数数组profits、正数K、正数M
 * costs[i]表示i号项目的花费
 * profits[i]表示i号项目在扣除花费之后还能挣到的钱(利润)
 * K表示你只能串行的最多做k个项目
 * M表示你初始的资金
 * 说明: 每做完一个项目，马上获得的收益，可以支持你去做下一个项目。不能并行的做项目。
 * 输出：你最后获得的最大钱数。
 */
public class IPO {

    public static class Program {
        public int profit;
        public int cost;

        public Program(int profit, int cost) {
            this.profit = profit;
            this.cost = cost;
        }
    }
    public static class MinCostComparator implements Comparator<Program> {

        @Override
        public int compare(Program o1, Program o2) {
            return o1.cost - o2.cost;
        }
    }
    public static class MaxProfitComparator implements Comparator<Program> {

        @Override
        public int compare(Program o1, Program o2) {
            return o2.profit - o1.profit;
        }
    }
    /**
     * 思路:1.建两个堆，一个是小根堆，一个是大根堆
     *      2.把这个项目的花费进小根堆，这个项目的的利润进大根堆
     *      3.先进小根堆，从堆顶找一个花费最小的跟你的初始资金比
     *      4.如果初始资金足够，则出堆进大根堆，然后堆顶的最大利润 + 最初的资金
     *      5.周而复始，算出最大的钱数
     */
    public static int findMaximizedCapital(int k,int w,int[] profit,int[] capital) {
        PriorityQueue<Program> minCost= new PriorityQueue<>(new MinCostComparator());
        PriorityQueue<Program> maxProfit = new PriorityQueue<>(new MaxProfitComparator());
        for (int i = 0; i < profit.length; i++) {
            minCost.add(new Program(profit[i],capital[i]));
        }
        for (int i = 0; i < k; i++) {//最多k个项目
            while (!minCost.isEmpty() && minCost.peek().cost <= w) {
                maxProfit.add(minCost.poll());
            }
            if (maxProfit.isEmpty()) {
                //如果你的钱连一个项目都买不起
                //则直接返回你的初始资金
                return w;
            }
            w += maxProfit.poll().profit;
        }
        return w;
    }
}
