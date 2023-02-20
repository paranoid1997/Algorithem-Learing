package com.liu.Window;

import java.util.LinkedList;

/**
 * N个加油站组成一个环形，给定两个长度都是N的非负数组 gas和cost(N>1)，
 * gas[i]代表 第i个加油站存的油可以跑多少千米，
 * cost[i]代表第i个加油站到环中下一个加油站相隔 多少千米。
 * 假设你有一辆油箱足够大的车，初始时车里没有油。
 * 如果车从第i个加油站出发，最终 可以回到这个加油站，那么第i个加油站就算良好出发点，否则就不算。
 * 请返回长度为N的boolean型数组res，res[i]代表第 i 个加油站是不是良好出发点。
 */
public class GasStation {

    // 这个方法的时间复杂度O(N)，额外空间复杂度O(N)
    public static int canCompleteCircuit(int[] gas, int[] cost) {
        boolean[] good = goodArray(gas, cost);
        for (int i = 0; i < gas.length; i++) {
            if (good[i]) {
                return i;
            }
        }
        return -1;
    }

    public static boolean[] goodArray(int[] g, int[] c) {
        int N = g.length;
        int M = N << 1;
        int[] arr = new int[M];
        for (int i = 0; i < N; i++) {
            arr[i] = g[i] - c[i];
            arr[i + N] = g[i] - c[i];
        }
        for (int i = 1; i < M; i++) {
            arr[i] += arr[i - 1];
        }
        LinkedList<Integer> w = new LinkedList<>();
        for (int i = 0; i < N; i++) {
            while (!w.isEmpty() && arr[w.peekLast()] >= arr[i]) {
                w.pollLast();
            }
            w.addLast(i);
        }
        boolean[] ans = new boolean[N];
        for (int offset = 0, i = 0, j = N; j < M; offset = arr[i++], j++) {
            if (arr[w.peekFirst()] - offset >= 0) {
                ans[i] = true;
            }
            if (w.peekFirst() == i) {
                w.pollFirst();
            }
            while (!w.isEmpty() && arr[w.peekLast()] >= arr[j]) {
                w.pollLast();
            }
            w.addLast(j);
        }
        return ans;
    }
}
