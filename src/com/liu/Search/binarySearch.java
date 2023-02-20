package com.liu.Search;

import java.util.Arrays;

/**
 * 二分不一定必须要有序的，不是只有有序才能二分，
 *一种策略，如果有一边肯定有或者有一边肯定没有或者有一边可能有但另一边一定没有，则可以去进行二分操作
 */
public class binarySearch {

    /**
     * 功能：在arr上找出满足 >=value的最左的位置
     * @param arr
     * @param value
     * @return
     */
    public static int nearestIeftIndex(int[] arr,int value) {
        int Left = 0;
        int Right = arr.length - 1;
        int index = -1;//记录最左的数的index
        while (Left <= Right) {
            int mid = Left + ((Right - Left) >> 1);//防止溢出
            if (arr[mid] >= value) {
                index = mid;
                Right = mid - 1;
            }else {
                Left = mid + 1;
            }
        }
        return index;
    }

    /**
     * 功能：找满足<=value的最右位置
     * @param arr
     * @param value
     * @return
     */
    public static int nearestIndex(int[] arr, int value) {
        int Left = 0;
        int Right = arr.length - 1;
        int index = -1; // 记录最右的对号
        while (Left <= Right) {
            int mid = Left + ((Right - Left) >> 1);
            if (arr[mid] <= value) {
                index = mid;
                Left = mid + 1;
            } else {
                Right = mid - 1;
            }
        }
        return index;
    }

    /**
     * 在一个无序数组中, 值有可能正, 负, 或者零, 数组中任由两个相邻的数一定不相等.
     * 定义局部最小:
     * 1.长度为1，arr[0]就是局部最小；
     * 2.数组的开头，如果arr[0] < arr[1] ，arr[0]被定义为局部最小。
     * 3.数组的结尾，如果arr[N-1] < arr[N-2] ，arr[N-1]被定义为局部最小。
     * 任何一个中间位置i, 即数组下标1~N-2之间, 必须满足arr[i-1] < arr[i] <arr[i+1] ,叫找到一个局部最小。
     * 请找到任意一个局部最小并返回。
     * @param arr
     * @return
     */
    public static int getLessIndex(int[] arr) {
        if (arr == null || arr.length ==0) return -1;
        if (arr.length == 1 || arr[0] < arr[1]) return 0;
        if (arr[arr.length - 1] < arr[arr.length - 2]) return arr.length - 1;
        int Left = 1;
        int Right = arr.length - 2;
        while (Left <= Right) {
            int mid = Left + ((Right - Left) >> 1);
            if (arr[mid] > arr[mid - 1]) {
                Right = mid - 1;
            }else if (arr[mid] > arr[mid + 1]) {
                Left = mid + 1;
            }else {
                return mid;
            }
        }
        return -1;//查找失败
    }
    /**
     * 功能：相当于equal用于比较大小的
     * @param arr
     * @param value
     * @return
     */
    public static int test(int[] arr, int value) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] >= value) return i;
        }
        return -1;
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
            int[] arr = generateRandomArray(maxSize,maxValue);
            Arrays.sort(arr);
            int value = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
            if (test(arr,value) != nearestIeftIndex(arr,value)) {
                printArray(arr);
                System.out.println(value);
                System.out.println(test(arr,value));
                System.out.println(nearestIeftIndex(arr,value));
                success = false;
                break;
            }
        }
        System.out.println(success ? "测试代码正确" :"fuck you!! 错误");
    }
}
