package com.liu.Math;

/**
 * 给定一个数N，想象只由0和1两种字符，组成的所有长度为N的字符串
 * 如果某个字符串,任何0字符的左边都有1紧挨着,认为这个字符串达标
 * 返回有多少达标的字符串
 */
public class ZeroLeftNumber {

    /**
     *暴力递归
     */
    public static int getNum(int n) {
        if (n < 1) return 0;
        return process(1,n);
    }

    private static int process(int i, int n) {
        if (i == n - 1) return 2;
        if (i == n) return 1;
        //                 选择1               选择0
        return process(i + 1,n) + process(i + 2, n);
    }

    /**
     *矩阵快速幂
     */
    public static int getNum2(int n) {
        if (n < 1) return 0;
        if (n == 1 || n == 2) return n;
        //f(n) = f(n + 1) + f(n + 2);
        int[][] base = { { 1, 1 }, { 1, 0 } };
        int[][] res = matrixPower(base, n - 2);
        return 2 * res[0][0] + res[1][0];
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
        System.out.println(getNum(n));
        System.out.println(getNum2(n));
    }

}
