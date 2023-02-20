package com.liu.Array;

/**
 * a b c d
 * e f g h
 * i j k L
 *
 * 打印顺序：a b c d h L k j I e f g
 */
public class PrintMatrixSpiralOrder {

    public static void spiralOrderPrint(int[][] matrix) {
        int a = 0;
        int b = 0;
        int c = matrix.length - 1;
        int d = matrix[0].length - 1;
        while (a <= c && b <= d) {
            printEdge(matrix, a++, b++, c--, d--);
        }
    }

    public static void printEdge(int[][] matrix, int a, int b, int c, int d) {
        if (a == c) {
            for (int i = b; i <= d; i++) {
                System.out.print(matrix[a][i] + " ");
            }
        } else if (b == d) {
            for (int i = a; i <= c; i++) {
                System.out.print(matrix[i][b] + " ");
            }
        } else {
            int curC = b;
            int curR = a;
            while (curC != d) {
                System.out.print(matrix[a][curC] + " ");
                curC++;
            }
            while (curR != c) {
                System.out.print(matrix[curR][d] + " ");
                curR++;
            }
            while (curC != b) {
                System.out.print(matrix[c][curC] + " ");
                curC--;
            }
            while (curR != a) {
                System.out.print(matrix[curR][b] + " ");
                curR--;
            }
        }
    }

    public static void main(String[] args) {
        int[][] matrix = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 },
                { 13, 14, 15, 16 } };
        spiralOrderPrint(matrix);

    }

}
