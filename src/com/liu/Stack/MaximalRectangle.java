package com.liu.Stack;

import java.util.Stack;

/**
 * 给定一个仅包含 0 和 1 、大小为 rows x cols 的二维二进制矩阵，
 * 找出只包含 1 的最大矩形，并返回其面积。
 * 用到单调栈 + 压缩数组的技巧
 * 用压缩数组的技巧把矩阵中'1'转换成直方图数组问题
 * 问题就转换成求直方图数组中矩形的最大面积
 */
public class MaximalRectangle {

    public static int maximalRectangle(char[][] matrix) {
        if (matrix == null || matrix.length == 0) return 0;
        int maxArea = 0;
        int[] height = new int[matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                height[j] = matrix[i][j] == '0' ? 0 : height[j] + 1;
            }
            maxArea = Math.max(maxArea,maxFromBottom(height));
        }
        return maxArea;
    }

    private static int maxFromBottom(int[] height) {
        if (height == null || height.length == 0) return 0;
        int maxArea = 0;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < height.length; i++) {
            while (!stack.isEmpty() && height[i] <= height[stack.peek()]) {
                int j = stack.pop();
                int leftIndex = stack.isEmpty() ? -1 : stack.peek();
                //(i - leftIndex - 1) * height[j] = 5 - (-1) - 1 = 5
                int curArea = (i - leftIndex - 1) * height[j];
                maxArea = Math.max(maxArea, curArea);
            }
            stack.push(i);
        }
        while (!stack.isEmpty()) {
            int j = stack.pop();
            int leftIndex = stack.isEmpty() ? -1 : stack.peek();
            int curArea = (height.length - leftIndex - 1) * height[j];
            maxArea = Math.max(maxArea, curArea);
        }
        return maxArea;
    }
}
