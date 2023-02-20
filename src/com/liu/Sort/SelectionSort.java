package com.liu.Sort;

import java.util.Arrays;

public class SelectionSort {
    public static void selectionSort(int arr[]) {
        if (arr == null || arr.length < 2) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            int minIndex = i;
            for (int j = i+1; j < arr.length; j++) {
                minIndex = arr[j] < arr[minIndex] ? j : minIndex;
            }
            int temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
        }
    }

    /**
     * @comparator ：对数器1
     * 功能：相当于自己编写单元测试用例
     */
    public static void comparator(int[] arr) {
        Arrays.sort(arr);
    }

    /**
     *  功能 ： 生成随机数组
     * @param maxSize 随机数组的最大长度
     * @param maxValue 值：-100 ～100
     */
    public static int[] generateRandomArray(int maxSize,int maxValue) {
        // Math.random()   [0,1)
        // Math.random() * N  [0,N)
        // (int)(Math.random() * N)  [0, N-1]
        int[] arr = new int[(int) (Math.random()*(maxSize+1))];//数组长度随机[0，N]
        for (int i = 0; i < arr.length; i++) {
            //两个随机数做差 得到的数组的值有正有负
            arr[i] = (int) (Math.random()*(maxValue+1)) - (int) (Math.random()*(maxValue));
        }
        return arr;
    }

    /**
     * 功能: 复制数组元素
     * @param arr
     * @return
     */
    public static int[] copyArray(int[] arr) {
        if (arr == null) return null;
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i];
        }
        return res;
    }

    /**
     * 功能: 判断两数组是否相等
     * @param arr1
     * @param arr2
     * @return
     */
    public static boolean isEqual(int[] arr1,int[] arr2) {
        if (arr1 == null && arr2 == null) return true;
        if ((arr1 == null &&arr2 != null) || (arr1 != null && arr2 ==null)) return false;
        if (arr1.length != arr2.length) return false;
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) return false;
        }
        return true;
    }

    /**
     * 功能: 打印数组
     * @param arr
     */
    public static void printArray(int[] arr) {
        if (arr == null) return;
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
    public static void main(String[] args) {
        int testTime = 5;//测试的次数
        int maxSize = 6;//随机数组的长度
        int maxValue = 100;//随机数组的随机值
        boolean success = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize,maxValue);
            int[] arr2 = copyArray(arr1);
            selectionSort(arr1);
            comparator(arr2);
            if (!isEqual(arr1,arr2)) {
                success = false;
                printArray(arr1);
                printArray(arr2);
            }
        }
        System.out.println(success ? "测试代码正确" :"fuck you!! 错误");
        int[] arr = generateRandomArray(maxSize,maxValue);
        printArray(arr);
        selectionSort(arr);
        printArray(arr);
    }
}
