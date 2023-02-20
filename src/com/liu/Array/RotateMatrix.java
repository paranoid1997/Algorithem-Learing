package com.liu.Array;

/**
 * 给定一个正方形矩阵matrix，原地调整成顺时针90度转动的样子
 * a  b  c            g  d  a
 * d  e  f            h  e  b
 * g  h  i            i  f  c
 */
public class RotateMatrix {
    /**
     * 这类旋转问题的核心就是
     * 找到最左上方的那个点，然后找到最右下角的那个点
     * 然后像剥洋葱那样一层一层的拨
     * 哲学感悟:跳出局部，总览全局
     */
    public static void rotate(int[][] matrix) {
        int a = 0;
        int b = 0;
        int c = matrix.length - 1;
        int d = matrix[0].length - 1;
        while (a < c) {
            rotateEdge(matrix,a++,b++,c--,d--);
        }
    }

    private static void rotateEdge(int[][] matrix, int a, int b, int c, int d) {
        int temp = 0;
        for (int i = 0; i < d - b; i++) {
            temp = matrix[a][b + i];
            matrix[a][b + i] = matrix[c - i][b];
            matrix[c - i][b] = matrix[c][d - i];
            matrix[c][d - i] = matrix[a + i][d];
            matrix[a + i][d] = temp;
        }
    }

    public static void printMatrix(int[][] matrix) {
        for (int i = 0; i != matrix.length; i++) {
            for (int j = 0; j != matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int[][] matrix = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 }, { 13, 14, 15, 16 } };
        printMatrix(matrix);
        rotate(matrix);
        System.out.println("=========");
        printMatrix(matrix);
    }
}
