package com.liu.Stack;

import java.util.Stack;

/**
 * https://leetcode-cn.com/problems/largest-rectangle-in-histogram/
 * 给定 n 个非负整数，用来表示柱状图中各个柱子的高度。每个柱子彼此相邻，且宽度为 1 。
 * 求在该柱状图中，能够勾勒出来的矩形的最大面积。
 */
public class LargestRectangleArea {
    /**
     *用系统实现的单调栈
     */
    public static int largestArea(int[] height) {
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

    /**
     *自己用数组实现的单调栈
     */
    public static int largestArea2(int[] height) {
        if (height == null || height.length == 0) return 0;
        int maxArea = 0;
        int n = height.length;
        int[] stack = new int[n];
        int si = -1;
        for (int i = 0; i < height.length; i++) {
            while (si != -1 && height[i] <= height[stack[si]]) {
                int j = stack[si--];
                int leftIndex = si == -1 ? -1 : stack[si];
                int curArea = (i - leftIndex - 1 ) * height[j];
                maxArea = Math.max(maxArea,curArea);
            }
            stack[++si] = i;
        }
        while (si != - 1) {
            int j = stack[si--];
            int leftIndex = si == -1 ? -1 : stack[si];
            int curArea = (n - leftIndex - 1 ) * height[j];
            maxArea = Math.max(maxArea,curArea);
        }
        return maxArea;
    }
}
