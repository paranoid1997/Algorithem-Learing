package com.liu.DP;

public class LargestBorderedSquare {

    public static int largestBorderedSquare(int[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;
        int[][] right = new int[row][col];
        int[][] down = new int[row][col];
        setBorderMap(matrix,right,down);
        for (int size = Math.min(matrix.length, matrix[0].length); size != 0; size--) {
            if (hasSizeOfBorder(size, right, down)) {
                return size * size;
            }
        }
        return 0;
    }

    private static boolean hasSizeOfBorder(int border, int[][] right, int[][] down) {
        for (int i = 0; i != right.length - border + 1; i++) {
            for (int j = 0; j != right[0].length - border + 1; j++) {
                if (right[i][j] >= border && down[i][j] >= border &&
                        right[i + border - 1][j] >= border && down[i][j + border - 1] >= border) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void setBorderMap(int[][] matrix, int[][] right, int[][] down) {
        int row = matrix.length;
        int col = matrix[0].length;
        if (matrix[row - 1][col - 1] == 1) {
            right[row - 1][col - 1] = 1;
            down[row - 1][col - 1] = 1;
        }
        //确定最后一列
        for (int i = row - 2; i != -1 ; i--) {
            if (matrix[i][col - 1] == 1) {
                down[i][col - 1] = down[i + 1][col - 1] + 1;
                right[i][col - 1] = 1;
            }
        }
        //确定最后一行
        for (int i = col - 2; i != -1; i--) {
            if (matrix[row - 1][i] == 1) {
                right[row - 1][i] = right[row - 1][i + 1] + 1;
                down[row - 1][i] = 1;
            }
        }

        //确定非最后一行，非最后一列
        for (int i = row - 2; i != -1 ; i--) {
            for (int j = col - 2; j != -1; j--) {
                if (matrix[i][j] == 1) {
                    right[i][j] = right[i][j + 1] + 1;
                    down[i][j] = down[i + 1][j] + 1;
                }
            }
        }
    }
}
