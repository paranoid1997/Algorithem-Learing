package com.liu.Math;

public class FibonacciProblem {
    /**
     *暴力方法
     */
    public static int Fibonacci(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }
        return Fibonacci(n - 1) + Fibonacci(n - 2);
    }

    /**
     * 只要是有递推数列的式子
     * 不涉及转移方程
     * 都可以用矩阵快速幂解决
     * 时间复杂度O(LogN)
     */
    public static int Fibonacci2(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }
        // [ 1 ,1 ]
        // [ 1, 0 ]
        //base是我们需要提前计算的答案
        //跟线性代数中的行列式有关
        int[][] base = {
                {1,1},
                {1,0}
        };
        int[][] res = matrixPower(base, n - 2);
        return res[0][0] + res[1][0];
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
        System.out.println(Fibonacci(n));
        System.out.println(Fibonacci2(n));

    }
}
