package com.liu.Sort;

import java.util.Arrays;

public class RadixSort {
    /**
     * 桶排序
     * 只适用于非负数
     * 时间复杂度O(N*Log10 max)
     * @param arr
     */
    public static void radixSort(int[] arr) {
        if (arr == null || arr.length == 0) return;
        radixSort(arr,0,arr.length - 1,maxBits(arr));
    }

    /**
     *功能:获得一个一个最大数一共有几位
     * 比如一个最大的数为102，一共有3位
     */
    public static int maxBits(int[] arr) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            max = Math.max(max,arr[i]);
        }
        int res = 0;
        while (max != 0) {
            res++;
            max /= 10;
        }
        return res;
    }
    /**
     * 桶排序重载
     * @param digit:最大值的十进制位数digit
     */
    public static void radixSort(int[] arr,int left,int right,int digit) {
        final int radix = 10;
        int i = 0, j = 0;
        int[] help = new int[right - left + 1];
        for (int d = 1; d <= digit; d++) {//有多少位就进出几次
            int[] count = new int[radix];
            for ( i = left; i <= right; i++) {
                 j = getDigit(arr[i],d);//提取每一位上的数字放到count上
                count[j]++;
            }
            for ( i = 1; i < radix;i++) {//计算前缀累加和
                count[i] = count[i] + count[i - 1];
            }
            for (i = right; i >= left; i--) {
                //从右往左开始骚操作
                j = getDigit(arr[i],d);//提取最右边数组的个位
                //比如小于个位2的数有6个,数组最右边的数应该放在help数组
                //第6-1 = 5的位置上，其余的也是以此类推
                help[count[j] - 1] = arr[i];
                count[j]--;//各位数j这个位数上的前缀和-1
            }
            for (i = left,j =0;i <= right; i++,j++) {
                arr[i] = help[j];
            }
        }
    }
    /**
     *提取这个数每一位上面的数字
     */
    public static int getDigit(int x,int d) {
        return ((x / ((int) Math.pow(10, d - 1))) % 10);
    }
    // for test
    public static void comparator(int[] arr) {
        Arrays.sort(arr);
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random());
        }
        return arr;
    }

    // for test
    public static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i];
        }
        return res;
    }

    // for test
    public static boolean isEqual(int[] arr1, int[] arr2) {
        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
            return false;
        }
        if (arr1 == null && arr2 == null) {
            return true;
        }
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    // for test
    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100000;
        boolean succeed = true;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            radixSort(arr1);
            comparator(arr2);
            if (!isEqual(arr1, arr2)) {
                succeed = false;
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println(succeed ? "测试结束" : "桶排序错误!!!");

    }
}
