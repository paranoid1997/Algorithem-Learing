package com.liu.KMP;

/**
 * 给定两个字符串, A 和 B。
 * A 的旋转操作就是将 A 最左边的字符移动到最右边。
 * 例如, 若 A = 'abcde'，在移动一次之后结果就是'bcdea'。
 * 如果在若干次旋转操作之后，A 能变成B，那么返回True。
 */
public class isRotation {

    public static boolean isRotation1(String a, String b) {
        if (a == null || b == null || a.length() != b.length()) {
            return false;
        }
        String a2 = a + a;
        return kmp2(a2,b) != -1;
    }

    private static int kmp2(String s, String m) {
        if (s.length() < m.length()) return -1;
        char[] str1 = s.toCharArray();
        char[] str2 = m.toCharArray();
        int x = 0;
        int y = 0;
        int[] next = getnext(str2);
        while (x < str1.length && y < str2.length) {
            if (str1[x] == str2[y]) {
                x++;
                y++;
            }else if (next[y] == -1) {
                x++;
            }else {
                y = next[y];
            }
        }
        return y == str2.length ? x - y : -1;
    }

    private static int[] getnext(char[] str2) {
        if (str2.length == 1) return new int[]{-1};
        int[] next = new int[str2.length];
        next[0] = -1;
        next[1] = 0;
        int cn = 0;
        int i = 2;
        while (i < next.length) {
            if (str2[i - 1] == str2[cn]) {
                next[i++] = ++cn;
            }else if (cn > 0) {
                cn = next[cn];
            }else {
                next[i++] = 0;
            }
        }
        return next;
    }

    public static void main(String[] args) {
        String str1 = "biaosenliu";
        String str2 = "liusenbiao";
        System.out.println(isRotation1(str1, str2));
    }
}
