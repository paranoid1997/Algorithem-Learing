package com.liu.Sort;

public class MergeSort {
    /**
     * 递归实现归并排序
     *
     */
    public static void mergeSort1(int[] arr) {
        if (arr == null || arr.length < 2) return;
        process(arr,0,arr.length - 1);
    }
    public static void process(int[] arr, int left, int right) {
        //边界条件
        if (left == right) return;
        int mid = left + ((right - left) >> 1);
        process(arr,left,mid);
        process(arr,mid + 1,right);
        merge(arr,left,mid,right);
    }
    public static void merge(int[] arr,int left, int mid, int right) {
     int[] helper = new int[right - left + 1];
     int i = 0;
     int p1 = left;
     int p2 = mid + 1;
     while (p1 <= mid && p2 <= right) {
         helper[i++] = arr[p1] <= arr[p2] ? arr[p1++] :arr[p2++];
     }
     while (p1 <= mid)  helper[i++] = arr[p1++];
     while (p2 <= right)  helper[i++] = arr[p2++];
     //把排序好的helper数组复制到原数组arr中
        for (int j = 0; j < helper.length; j++) {
            arr[left + j] = helper[j];
        }
    }

    /**
     *非递归实现归并排序
     */
    public static void mergeSort2(int[] arr) {
        if (arr == null || arr.length < 2) return;
        int N = arr.length;
        int mergeSize = 1;//步长为1
        while (mergeSize < N) {
            int leftFirst = 0;//当前左组的第一个位置
            while (leftFirst < N) {
                int mid = leftFirst + mergeSize -1;//当前左组的最后一个位置
                if (mid >= N) break;//如果左组都不够，越界跳出
                int right = Math.min(mid + mergeSize,N -1);//右组+mergesize跳出越界，取数组最后最大长度
                merge(arr,leftFirst,mid,right);
                leftFirst = right + 1;//下一轮
            }
            if (mergeSize > N/2) break;//防止int32位能表示的最大的数据溢出,mergeSize * 2 > N/2 * 2 = N
            mergeSize <<= 1;//步长乘2
        }
    }

    /**
     * 生成随机数组
     * @param maxSize
     * @param maxValue
     * @return
     */
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        return arr;
    }

    /**
     * 复制数组
     * @param arr
     * @return
     */
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

    /**
     * 判断两个数组是否相等
     * @param arr1
     * @param arr2
     * @return
     */
    public static boolean isEqual(int[] arr1,int[] arr2) {
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
        int testTime = 1000000;
        int maxSize = 100;
        int maxValue = 100;;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize,maxValue);
            int[] arr2 = copyArray(arr1);
//            int[] arr1 = {44,-5};
//            int[] arr2 = {44,-5};
//            printArray(arr1);//测bug
            mergeSort1(arr1);//[-5,44]
            mergeSort2(arr2);//[44,5]
            if (!isEqual(arr1,arr2)) {
                System.out.println("归并排序代码错误了！！！");
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println("测试结束");
    }
}
