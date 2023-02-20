package com.liu.Sort;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 给定很多线段，每个线段都有两个数组[start, end]，
 * 表示线段开始位置和结束位置，左右都是闭区间
 * 规定：
 * 1）线段的开始和结束位置一定都是整数值
 * 2）线段重合区域的长度必须>=1
 * 返回线段最多重合区域中，包含了几条线段
 */
public class CoverMax {
    /**
     *暴力法
     * 因为每个线段都必须是整数值
     * 所以每次加0.5，重合的线段必大于线段的开始位置的最小值
     * 也必定小于结束位置的最大值
     */
    public static int maxCover1(int[][] lines) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < lines.length; i++) {
            min = Math.min(min,lines[i][0]);//所有线段的开始位置求最小值
            max = Math.max(max,lines[i][1]);//所有线段的结束位置求最大值
        }
        int cover = 0;
        for (double p = min + 0.5; p < max; p += 1) {
            int cur = 0;
            for (int i = 0; i < lines.length; i++) {
                if (lines[i][0] < p && lines[i][1] > p) {
                    cur++;
                }
            }
            cover = Math.max(cover,cur);
        }
        return cover;
    }

    /**
     *用堆排序来做
     * 1.先把线段的开头进行从大到小的排序
     * 2.如果堆是空的，依次加入线段中的end元素
     * 3.如果线段树非空，比较堆顶end元素是否小于等于线段的start
     * 若小于等于，则两个线段必定不重合，堆顶元素弹出
     * 4.堆里有多少元素就是最大的重复线段个数
     */
    public static int maxCover2(int[][] m) {
        Line[] lines = new Line[m.length];
        for (int i = 0; i < m.length; i++) {
            lines[i] = new Line(m[i][0], m[i][1]);
        }
        Arrays.sort(lines,new StartComparator());
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        int max = 0;
        for (int i = 0; i < lines.length; i++) {
            //把<=cur.start 东西都弹出
            while (!heap.isEmpty() && heap.peek() <= lines[i].start) {
                //因为堆的顶端是一个线段end,end <= start必然不会有覆盖的线段
                heap.poll();
            }
            heap.add(lines[i].end);//把这个线段的end加进去
            max = Math.max(max,heap.size());
        }
        return max;
    }
    public static class StartComparator implements Comparator<Line> {
        @Override
        public int compare(Line o1, Line o2) {
            return o1.start - o2.start;
        }
    }
    public static class Line {
        public int start;
        public int end;

        public Line(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }
    /**
     * 生成随机的线段数组
     */
    public static int[][] generateLines(int N,int start,int end) {
        int size = (int) ((Math.random() * N) + 1);
        int[][] ans = new int[size][2];
        for (int i = 0; i < size; i++) {
            int a = start + (int) (Math.random() * (end - start + 1));
            int b = start + (int) (Math.random() * (end - start + 1));
            if (a == b) {
                b = a + 1;
            }
            ans[i][0] = Math.min(a, b);
            ans[i][1] = Math.max(a, b);
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println("测试开始");
        int N = 100;
        int start = 0;
        int end = 200;
        int testTimes = 100000;
        for (int i = 0; i < testTimes; i++) {
            int[][] lines = generateLines(N, start, end);
            int ans1 = maxCover1(lines);
            int ans2 = maxCover2(lines);
            if (ans1 != ans2) {
                System.out.println("最大线段重合的个数出错!");
            }
        }
        System.out.println("测试结束");
    }
}
