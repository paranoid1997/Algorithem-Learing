package com.liu.Greedy;


/**
 * 一个数组中只有两种字符'G'和'B’，
 * 可以让所有的G都放在左侧，所有的B都放在右侧,
 * 或者可以让所有的G都放在右侧，所有的B都放在左侧,
 * 但是只能在相邻字符之间进行交换操作，返回至少需要交换几次?
 */
public class MinSwapStep {

    /**
     * 贪心策略
     * 使用两个变量，一个left,一个minstep,
     * 遇到第一个G点，把他逻辑上移到数组的第一个元素中，用现在的i左边减去数组第一个位置的左边
     * 这里用i - left表示,然后left向右移动一格，表示逻辑上0位置元素已经被G归位，以此类推
     * 累加算的最少需要交换几次，需要考虑极端情况全是GGG和全是BBB的情况
     */
    public static int minSwapStep1(String s) {
        if (s.length() == 0 || s == null) return 0;
        char[] str = s.toCharArray();
        int left1 = 0;//用来记录G点占的坑位
        int minStep1 = 0;
        for (int i = 0; i < s.length(); i++) {
            if (str[i] == 'G') {
                minStep1 += i - left1;
                left1++;
            }
        }

        //这个是用来防止全是BBB这类情况的
        int left2 = 0;
        int minStep2 = 0;
        for (int i = 0; i < s.length(); i++) {
            if (str[i] == 'B') {
                minStep2 += i -left2;
                left2++;
            }
        }
       return Math.min(minStep1,minStep2);
        //return minStepCount1;
    }

    // 可以让G在左，或者在右
    public static int minSteps2(String s) {
        if (s == null || s.equals("")) {
            return 0;
        }
        char[] str = s.toCharArray();
        int step1 = 0;
        int step2 = 0;
        int gi = 0;
        int bi = 0;
        for (int i = 0; i < str.length; i++) {
            if (str[i] == 'G') { // 当前的G，去左边   方案1
                step1 += i - (gi++);
            } else {// 当前的B，去左边   方案2
                step2 += i - (bi++);
            }
        }
        return Math.min(step1, step2);
    }

    // 为了测试
    public static String randomString(int maxLen) {
        char[] str = new char[(int) (Math.random() * maxLen)];
        for (int i = 0; i < str.length; i++) {
            str[i] = Math.random() < 0.5 ? 'G' : 'B';
        }
        return String.valueOf(str);
    }
    public static void main(String[] args) {
        int maxLen = 50;
        int testTime = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            String str = randomString(maxLen);
            int ans1 = minSwapStep1(str);
            int ans2 = minSteps2(str);
            if (ans1 != ans2) {
                System.out.println("Oops!");
            }
        }
        System.out.println("测试结束");
    }
}
