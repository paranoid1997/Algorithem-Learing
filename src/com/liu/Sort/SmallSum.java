package com.liu.Sort;

/**
 * 给定一个数组，求第i个元素左边的所有比arr[i]元素小的数的累加和
 * 思路：从最左边找比x大的数
 * SmallSum = 左边产生小和的量 + 右边产生小和的量 + merge过程中产生小和的量
 * 从左到右
 */
public class SmallSum {

    public static int smallSum(int[] arr) {
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
        int i = 0;
        int p1 = left;
        int p2 = mid + 1;
        int res = 0;
        while (p1 <= mid && p2 <= right) {
            res += arr[p1] < arr[p2] ? (right - p2 +1) * arr[p1] : 0;//归并排序唯一要修改的地方
            //这里有个小bug,如果arr[p1] <= arr[p2]结果就是错的
            //这又是个卡边界的题，当左组和右组数字相等的时候
            //一定要让p2指针向右走,简而言之:就是当左右组相等时候，选择右组的数
            //因为你考虑左边的时候，你不知道右边到底有多少个数大于左边的值
            //故你只有找到右边第一个大于左边的值，你才能确切的知道有(right - p2 +1)个值大于左边的值！！！
            helper[i++] = arr[p1] < arr[p2] ? arr[p1++] :arr[p2++];
        }
        while (p1 <= mid)  helper[i++] = arr[p1++];
        while (p2 <= right)  helper[i++] = arr[p2++];
        //把排序好的helper数组复制到原数组arr中
        for (int j = 0; j < helper.length; j++) {
            arr[left + j] = helper[j];
        }
        return res;
    }

    /**
     * 对数器
     * @param arr
     * @return
     */
    public static int conparator(int[] arr) {
        if (arr == null || arr.length < 2) return 0;
        int res = 0;
        for (int i = 1; i < arr.length; i++) {
            for (int j = 0; j < i; j++) {
                res += arr[j] < arr[i] ? arr[j] : 0;
            }
        }
        return res;
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
        for (int i = 0; i <= arr1.length; i++) {
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
        int testTime = 10000000;
        int maxSize = 100;
        int maxValue = 100;
        boolean succeed = true;
        System.out.println("SmallSum开始测试");
        for (int i = 0; i < testTime; i++) {
           // int[] arr1 = generateRandomArray(maxSize, maxValue);
            //printArray(arr1);
            //int[] arr2 = copyArray(arr1);
            int[] arr1 = {-27,24,24,56};//看到有两个24，就要怀疑是不是加了个“=”号！！
            int[] arr2 = {-27,24,24,56};
            if (smallSum(arr1) != conparator(arr2)) {
                System.out.println("res1:" + smallSum(arr1));
                System.out.println("res2:" + conparator(arr2));
                succeed = false;
                printArray(arr1);
                printArray(arr2);

                break;
            }
        }
        System.out.println(succeed ? "SmallSum测试正确" : "fucked 错了！！");
    }
}
