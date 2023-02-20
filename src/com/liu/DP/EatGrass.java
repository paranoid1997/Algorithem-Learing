package com.liu.DP;

/**
 * 给定一个正整数N，表示有N份青草统一堆放在仓库里有一只牛和一只羊，牛先吃，羊后吃，
 * 它俩轮流吃草不管是牛还是羊，每一轮能吃的草量必须是：
 * 1，4，16，64…(4的某次方)
 * 谁最先把草吃完，谁获胜假设牛和羊都绝顶聪明，都想赢，
 * 都会做出理性的决定根据唯一的参数N，返回谁会赢
 */
public class EatGrass {

    public static String whoWin(int n) {
        if (n < 5) {
            return n == 0 || n == 2 ? "后手" : "先手";
        }
        int want = 1;
        while (want <= n) {
            if (whoWin(n - want).equals("后手")) {
                return "先手";
            }
            if (want <= (n / 4)) {
                //防止溢出
                want *= 4;
            }else {
                break;
            }
        }
        return "后手";
    }

    /**
     * 打表找规律
     */
    public static String winner2(int n) {
        if (n % 5 == 0 || n % 5 == 2) {
            return "后手";
        } else {
            return "先手";
        }
    }

    public static void main(String[] args) {

        for (int i = 0; i <= 50; i++) {
            System.out.println(i + " : " + whoWin(i));
        }
    }
}
