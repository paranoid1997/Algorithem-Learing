package com.liu.Sort;

/**
 * 一个数组中从从左到右构成降序的两个数，叫做逆序对
 * 求一个数组中一共有多少逆序对
 * 思路:从右往左开始进行归并排序，因为给定一个X想要求它的逆序对
 * 如果它在归并排序的右组，就只需要考虑左边有多少个逆序对
 * 如果两个数相等，先考虑右边的，指针从右往左移动
 * 为啥两个数相等只需要考虑右边的数呢？
 * 因为你这时候你已经归并排序好了，如果你考虑左边的，
 * 你不知道右边有多少个数比左边这个数要小
 * 不允许暴力！！！！
 * 从右到左
 */
public class ReversePair {

    public static int reversePair(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        return process(arr, 0, arr.length - 1);
    }

    private static int process(int[] arr, int left, int right) {
        if (left == right) return 0;
        int mid = left + ((right - left) >> 1);
        return process(arr,left,mid)//左边产生小和的量
                + process(arr,mid + 1,right)//右边产生小和的量
                + merge(arr,left,mid,right);//merge过程中产生小和的量
    }
    public static int merge(int[] arr,int left, int mid, int right) {
        int[] helper = new int[right - left + 1];
        int i = helper.length - 1;
        int p1 = mid;
        int p2 = right;
        int res = 0;
        while (p1 >=left && p2 > mid) {
            res += arr[p1] > arr[p2] ? (p2 - mid): 0;//归并排序唯一要修改的地方
            helper[i--] = arr[p1] > arr[p2] ? arr[p1--] :arr[p2--];
        }
        while (p1 >= left)  helper[i--] = arr[p1--];
        while (p2 > mid)  helper[i--] = arr[p2--];//边界，保证了如果两个数相等的情况选右边的！！
        //把排序好的helper数组复制到原数组arr中
        for (int j = 0; j < helper.length; j++) {
            arr[left + j] = helper[j];
        }
        return res;
    }

    /**
     * 对数器:暴力for循环
     * @param args
     */
    public static int comparator(int[] arr) {
        if (arr == null || arr.length < 2) return 0;
        int ans = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > arr[j]) ans++;
            }
        }
        return ans;
    }
    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
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
        int testTime = 100000;
        int maxSize = 100;
        int maxValue = 100;
        boolean succeed = true;
        System.out.println("RversePairNunber开始测试");
        for (int i = 0; i < testTime; i++) {
             int[] arr1 = generateRandomArray(maxSize, maxValue);
            //printArray(arr1);
            int[] arr2 = copyArray(arr1);
            if (reversePair(arr1) != comparator(arr2)) {
                succeed = false;
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println(succeed ? "RversePairNunber测试正确" : "fucked 错了！！");
    }
}
