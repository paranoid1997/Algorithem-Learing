package com.liu.Array;

public class SubMatrixSum {

    /**
     * 返回子矩阵最大值是多少
     */
    public static int getMaxMatrix1(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        int max = Integer.MIN_VALUE;
        int cur = 0;
        for (int i = 0; i < matrix.length; i++) {
            int[] sums = new int[matrix[0].length];
            for (int j = i; j < matrix.length; j++) {
                cur = 0;
                for (int k = 0; k < matrix[0].length; k++) {
                    sums[k] += matrix[j][k];
                    cur += sums[k];
                    max = Math.max(max,cur);
                    cur = cur < 0 ? 0 : cur;
                }
            }
        }
        return max;
    }


    /**
     * 返回最大和的子矩阵的左上角顶点和右下角顶点的值
     */
    public static int[] getMaxMatrix(int[][] matrix) {
        int max = Integer.MIN_VALUE;
        int cur = 0;
        int a = 0;
        int b = 0;
        int c = 0;
        int d = 0;
        for (int i = 0; i < matrix.length; i++) {
            int[] sums = new int[matrix[0].length];
            for (int j = i; j < matrix.length; j++) {
                cur = 0;
                int b1 = 0;
                for (int k = 0; k < matrix[0].length; k++) {
                    sums[k] += matrix[j][k];
                    cur += sums[k];
                    if (max < cur) {
                        max = cur;
                        a = i;
                        b = b1;
                        c = j;
                        d = k;
                    }
                    if (cur < 0) {
                        cur = 0;
                        b1 = k + 1;
                    }
                }
            }
        }
        return new int[]{a,b,c,d};

    }
}
