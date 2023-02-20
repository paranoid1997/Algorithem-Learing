package com.liu.Stack;

/**
 * 给你一个只包含 0 和 1 的 rows * columns 矩阵 mat ，
 * 请你返回有多少个 子矩形 的元素全部都是 1 。
 */
public class CountSubmatrices {

    public int numSubmat(int[][] mat) {
        if (mat == null || mat.length == 0 || mat[0].length == 0) return 0;
        int nums = 0;
        int[] height = new int[mat[0].length];
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                height[j] = mat[i][j] == 0 ? 0 : height[j] + 1;
            }
            nums += countFromBottom(height);
        }
        return nums;
    }

    private int countFromBottom(int[] height) {
        if (height == null || height.length == 0) return 0;
        int nums = 0;
        int n = height.length;
        int[] stack = new int[n];
        int si = -1;
        for (int i = 0; i < height.length; i++) {
            while (si != -1 && height[i] <= height[stack[si]]) {
                int cur = stack[si--];
                if (height[cur] > height[i]) {
                int leftIndex = si == -1 ? -1 : stack[si];
                int L = i - leftIndex - 1;
                //(x - max{y,z}) *  (n * (1 + n)) / 2
                    int down = Math.max(leftIndex == -1 ? 0 : height[leftIndex],height[i]);
                    nums += (height[cur] - down) * num(L);
                }
            }
            stack[++si] = i;
        }
        while (si != - 1) {
            int cur = stack[si--];
            int leftIndex = si == -1 ? -1 : stack[si];
            int L = height.length - leftIndex - 1;
            int down = leftIndex == -1 ? 0 : height[leftIndex];
            nums += (height[cur] - down) * num(L);
        }
        return nums;
    }
    public static int num(int n) {
        return ((n * (1 + n)) >> 1);
    }
}
