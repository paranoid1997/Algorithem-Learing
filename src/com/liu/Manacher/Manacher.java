package com.liu.Manacher;

/**
 * Mancher算法是专门解决最长回文子串的问题
 * 子串要求连续
 * 具体流程
 * 1.如果i在R的外侧，暴力扩散
 * 2.如果i在R的内测，分为三种情况
 * 1).如果回文子串在R内，i撇的回文半径
 * 2).如果回文子串在R外，R - i.
 * 3).如果回文子串在R的压线，min {i撇的回文半径,R - i}.
 * 时间复杂度O(N)
 */
public class Manacher {

    public static int manacher(String s) {
        if (s == null || s.length() == 0) return 0;
        // "12132" -> "#1#2#1#3#2#"
        char[] str = manacherString(s);
        int[] pArr = new int[str.length];//回文半径的大小
        int C = -1;//中心点
        int R = -1;//扩散失败的右边界
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < str.length; i++) {
            //i在R的内测的三种情况
            //2 * C - i就是i撇的回文半径
            pArr[i] = i < R ? Math.min(pArr[2 * C - i],R - i) : 1;
            //这句代码的意思就是以i为中心，像左右两边扩散
            while (i + pArr[i] < str.length && i - pArr[i] > - 1) {
                if (str[i + pArr[i]] == str[i - pArr[i]]) {
                    //是回文串,回文半径++
                    pArr[i]++;
                }else {
                    break;
                }
            }
            //如果它把R推的更右，更新R,更新C
            if (i + pArr[i] > R) {
                R = i + pArr[i];
                C = i;
            }
            max = Math.max(max,pArr[i]);
        }
        //半径数组中的最大值减1就是最大回文子串的长度
        return max - 1;

    }

    /**
     *暴力解
     */
    public static int right(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = manacherString(s);
        int max = 0;
        for (int i = 0; i < str.length; i++) {
            int L = i - 1;
            int R = i + 1;
            while (L >= 0 && R < str.length && str[L] == str[R]) {
                L--;
                R++;
            }
            max = Math.max(max, R - L - 1);
        }
        return max / 2;
    }
    public static char[] manacherString(String str) {
        char[] charArr = str.toCharArray();
        char[] res = new char[str.length() * 2 + 1];
        int index = 0;
        for (int i = 0; i != res.length; i++) {
            res[i] = (i & 1) == 0 ? '#' : charArr[index++];
        }
        return res;
    }

    // for test
    public static String getRandomString(int possibilities, int size) {
        char[] ans = new char[(int) (Math.random() * size) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (char) ((int) (Math.random() * possibilities) + 'a');
        }
        return String.valueOf(ans);
    }

    public static void main(String[] args) {
        int possibilities = 5;
        int strSize = 20;
        int testTimes = 5000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTimes; i++) {
            String str = getRandomString(possibilities, strSize);
            if (manacher(str) != right(str)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("测试结束");
    }
}
