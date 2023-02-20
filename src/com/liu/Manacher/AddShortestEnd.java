package com.liu.Manacher;

/**
 * 给定一个字符串, 让它整体变回文字符串,
 * 只能在后面添加字符, 最短加多少个
 * 返回添加的是哪几个字符串？
 */
public class AddShortestEnd {

    public static String shortestEnd(String s) {
        if (s == null || s.length() == 0) return null;
        char[] str = manacherString(s);
        int[] pArr = new int[str.length];
        int C = -1;
        int R = -1;
        int maxContainsEnd = -1;
        for (int i = 0; i < str.length; i++) {
            pArr[i] = i < R ? Math.min(pArr[2 * C - i],R - i) : 1;
            while (i + pArr[i] < str.length && i - pArr[i] > -1) {
                if (str[i + pArr[i]] == str[i - pArr[i]]) {
                    pArr[i]++;
                }else {
                    break;
                }
            }
            if (i + pArr[i] > R) {
                R = i + pArr[i];
                C = i;
            }
            if (R == str.length) {
                maxContainsEnd = pArr[i];
                break;
            }
        }
        char[] res = new char[s.length() - maxContainsEnd + 1];
        int n = s.length() - maxContainsEnd + 6;
        int index = 0;
        for (int i = n - 1; i >= 0 ; i--) {
            //0123456789
            //abcd123321
            if (str[i] == '#') continue;
            res[index++] = str[i];
        }
        return String.valueOf(res);
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

    public static void main(String[] args) {
        String str1 = "abcd123321";
        System.out.println(shortestEnd(str1));
    }
}
