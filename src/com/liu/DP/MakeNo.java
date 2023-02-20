package com.liu.DP;

/**
 * 生成长度为size的达标数组，什么叫达标？
 * 达标：对于任意的i< k < j,满足[i]+[j]!=[k]*2
 * 给定一个正数size,返回长度为size的达标数组
 */
public class MakeNo {

    public static int[] makeNo(int size) {
        if (size == 1) {
            return new int[]{1};
        }
        int halfSize = (size + 1) /2;
        int[] base = makeNo(halfSize);
        int index = 0;
        int[] ans = new int[size];
        for (; index < halfSize; index++) {
            ans[index] = base[index] * 2;
        }
        for (int i = 0; index < size;index++,i++) {
            ans[index] = base[i] * 2 + 1;
        }
        return ans;
    }


    public static boolean isValid(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            for (int k = i + 1; k < n; k++) {
                for (int j = k + 1; j < n; j++) {
                    if (arr[i] + arr[j] == arr[k] * 2) {
                        return false;
                    }
                }
            }
        }
        return true;
    }


    public static void main(String[] args) {
        System.out.println("test begin");
        for (int N = 1; N < 1000; N++) {
            int[] arr = makeNo(N);
            if (!isValid(arr)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test end");
    }
}
