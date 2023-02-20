package com.liu.Greedy;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 题意:一些要占用一个会议室宣讲，会议室不能同时容纳两个项目宣讲
 * 给你每一个项目的开始时间和结束时间
 * 你来安排宣讲的日程。要求会议室进行的宣讲的次数最多
 * 返回次数最多的宣讲场次
 */
public class BestArrange {

    public static class Program {
        public int start;//会议开始的时间
        public int end;//会议结束的时间

        public Program(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

       /**
     *贪心策略:把结束时间进行从小到大的排序
     * 在进行贪心
     */
    public static class ProgramComparator implements Comparator<Program> {

        @Override
        public int compare(Program o1, Program o2) {
            return o1.end - o2.end;
        }
    }
    public static int bestArrange(Program[] programs) {
        Arrays.sort(programs,new ProgramComparator());
        int timeLine = 0;
        int result = 0;
        for (int i = 0; i < programs.length; i++) {
            if (timeLine <= programs[i].start) {
                //如果下一个会议的的开始时间
                // 在现在这个会议结束之后
                //则可以安排
                result++;
                timeLine = programs[i].end;
            }
        }
        return result;
    }

    /**
     *暴力方法
     */
    public static int bestArrange1(Program[] programs) {
        if (programs == null || programs.length == 0) {
            return 0;
        }
        return process(programs,0,0);
    }

    private static int process(Program[] programs, int done, int timeLine) {
        if (programs.length == 0) {
            //如果没有会议了，
            // 返回之前安排了多少的会议
            return done;
        }
        int max = done;
        for (int i = 0; i < programs.length; i++) {
            if (programs[i].start >= timeLine) {
                Program[] next = copyButExcept(programs,i);
                max = Math.max(max,process(next,done + 1,programs[i].end ));
            }
        }
        return max;
    }
    public static Program[] copyButExcept(Program[] programs,int i) {
        Program[] ans = new Program[programs.length - 1];
        int index = 0;
        for (int k = 0; k < programs.length; k++) {
            if (k != i) {
                ans[index++] = programs[k];
            }
        }
        return ans;
    }
    // for test
    public static Program[] generatePrograms(int programSize, int timeMax) {
        Program[] ans = new Program[(int) (Math.random() * (programSize + 1))];
        for (int i = 0; i < ans.length; i++) {
            int start = (int) (Math.random() * (timeMax + 1));
            int end = (int) (Math.random() * (timeMax + 1));
            if (start == end) {
                ans[i] = new Program(start, start + 1);
            } else {
                ans[i] = new Program(Math.min(start, end), Math.max(start, end));
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int programSize = 12;
        int timeMax = 20;
        int timeTimes = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < timeTimes; i++) {
            Program[] programs = generatePrograms(programSize, timeMax);
            if (bestArrange1(programs) != bestArrange(programs)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("测试结束!");
    }
}
