package com.liu.Math;

/**
 * 第一年农场有1只成熟的母牛A，往后的每年：
 * 1）每一只成熟的母牛都会生一只母牛
 * 2）每一只新出生的母牛都在出生的第三年成熟
 * 3）每一只母牛永远不会死
 * 返回N年后牛的数量
 */
public class CowNumber {

    /**
     *暴力解
     */
    public static int cowNumber(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2 || n == 3) {
            return n;
        }
        int res = 3;
        int pre = 2;
        int prepre = 1;
        int tmp1 = 0;
        int tmp2 = 0;
        for (int i = 4; i <= n; i++) {
            tmp1 = res;
            tmp2 = pre;
            res = res + prepre;
            pre = tmp1;
            prepre = tmp2;
        }
        return res;
    }

    /**
     *矩阵快速幂
     */
    private static int cowNumber2(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2 || n == 3) {
            return n;
        }
        int[][] base = {
                { 1, 1, 0 },
                { 0, 0, 1 },
                { 1, 0, 0 } };
        int[][] res = matrixPower(base, n - 3);
        return 3 * res[0][0] + 2 * res[1][0] + res[2][0];
    }

    private static int[][] matrixPower(int[][] base, int p) {
        int[][] res = new int[base.length][base[0].length];
        for (int i = 0; i < res.length; i++) {
            //这是一个单位矩阵
            res[i][i] = 1;
        }
        // res = 矩阵中的1
        int[][] t = base;//矩阵的一次方
        for (;p != 0; p >>= 1) {
            //矩阵的幂次方看作一个二进制数
            //整体右移一位
            //如果有1，矩阵就自己和自己乘
            if ((p & 1) != 0) {
                //如果最末尾有1
                res = mulMatrix(res,t);
            }
            //如果末尾没有1
            //平方变4次方
            t = mulMatrix(t,t);
        }
        return res;
    }

    private static int[][] mulMatrix(int[][] m1, int[][] m2) {
        int[][] res = new int[m1.length][m2[0].length];
        for (int i = 0; i < m1.length; i++) {
            for (int j = 0; j < m2[0].length; j++) {
                for (int k = 0; k < m2.length; k++) {
                    res[i][j] += m1[i][k] * m2[k][j];
                }
            }
        }
        return res;
    }
    public static void main(String[] args) {
        int n = 19;
        System.out.println(cowNumber(n));
        System.out.println(cowNumber2(n));
    }
}
