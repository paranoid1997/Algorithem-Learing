package com.liu.KMP;

public class KMP {

    public static int kmp(String s1,String s2) {
        if (s1 == null || s2 == null || s2.length() < 1 || s1.length() < s2.length()) {
            return -1;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        int x = 0;
        int y = 0;
        int[] next = getNext(str2);
        while (x < str1.length && y < s2.length()) {
            if (str1[x] == str2[y]) {
                x++;
                y++;
            }else if (next[y] == -1) {
                x++;
            }else {
                y = next[y];
            }
        }
        //如果子串全部走完，则证明匹配成功
        return y == str2.length ? x - y : -1;
    }

    private static int[] getNext(char[] str2) {
        if (str2.length == 1) return new int[]{-1};
        int[] next = new int[str2.length];
        next[0] = -1;//人为规定;
        next[1] = 0;//人为规定
        int i = 2;//在2的位置上求next数组的值
        int cn = 0;//当前是哪个位置和i-1的位置字符做比较
        while (i < next.length) {
            if (str2[i - 1] == str2[cn]) {
                //配对成功的时候
                //i的位置是cn在next数组里面的值加1
                //i位置依赖i-1的位置
                next[i++] = ++cn;
            }else if (cn > 0) {
                //配对不成功的时候
                //但next数组还没有到0位置的时候
                cn = next[cn];
            }else  {
                //配对失败的时候
                //next数组到了0位置的时候
                next[i++] = 0;
            }
        }
        return next;
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
        int matchSize = 5;
        int testTimes = 5000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTimes; i++) {
            String str = getRandomString(possibilities, strSize);
            String match = getRandomString(possibilities, matchSize);
            if (kmp(str, match) != str.indexOf(match)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("测试结束");
    }
}
