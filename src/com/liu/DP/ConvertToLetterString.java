package com.liu.DP;

/**
 * 规定1和A对应、2和B对应、3和C对应...
 * 那么一个数字字符串比如"111”就可以转化为:
 * "AAA"、"KA"和"AK"
 * 给定一个只有数字字符组成的字符串str，返回有多少种转化结果
 */
public class ConvertToLetterString {

    public static int convertNumber(String str) {
        if (str == null || str.length() == 0 ) return 0;
        return process(str.toCharArray(),0);
    }

    private static int process(char[] str, int i) {
        if (i == str.length) {
            //之前做的决定，共同构成了这一个决定
            return 1;
        }
        if (str[i] == '0') return 0;
        //可能性一:i单转
        int ways = process(str,i + 1);
        //可能性二:i和i+1双转
        if (i + 1 < str.length && (str[i] - '0') * 10 + (str[i+1] - '0') < 27) {
            ways += process(str,i + 2);
        }
        return ways;
    }
    public static int convertNumberWithDp(String s) {
        if (s == null || s.length() == 0 ) return 0;
        char[] str = s.toCharArray();
        int n = str.length;
        int[] dp = new int[n+1];
        dp[n] = 1;
        for (int i = n - 1; i >= 0; i--) {
            if (str[i] != '0') {
                int ways = dp[i + 1];
                if (i + 1 < str.length && (str[i] - '0') * 10 + (str[i+1] - '0') < 27) {
                    ways += dp[i + 2];
                }
            dp[i] = ways;
            }
        }
        return dp[0];
    }
    // for test
    public static String randomString(int len) {
        char[] str = new char[len];
        for (int i = 0; i < len; i++) {
            str[i] = (char) ((int) (Math.random() * 10) + '0');
        }
        return String.valueOf(str);
    }

    public static void main(String[] args) {
        int N = 30;
        int testTime = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * N);
            String s = randomString(len);
            int ans1 = convertNumber(s);
            int ans2 = convertNumberWithDp(s);
            if (ans1 != ans2) {
                System.out.println(s);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("测试结束");
    }
}
