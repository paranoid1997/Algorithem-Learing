package com.liu.Sort;

import java.util.Stack;

/**
 * 荷兰国旗问题:1.给定一个数x,<=x的数的整体放在左边，>=x的数的整体放在右边
 * 2.给定一个数x,<x的数的整体放在左边，=x的整体放在中间，>=x的数的整体放在右边
 * 不要用辅助数组，时间复杂度为O(N)完成上面的调整
 * 荷兰国旗本质上就是partition选取的问题
 * 是为随机快速排序做铺垫用的！！！
 */
public class QuickSort {
    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
    public static int partition(int[] arr, int left, int right) {
        if (left > right) return -1;
        if (left == right) return left;
        int lessEqual = left - 1;
        int index = left;
        while (index < right) {
            //1.当前数小于目标数,当前数和小于等于区下一个数交换
            //2.index向下移动一个
            //3.当前数大于目标数，index直接跳到下一个
            //4.然后当前数和小于等于区交换,小于等于区向外扩
            if (arr[index] <= arr[right]) {
                swap(arr,index,++lessEqual);
            }
            index++;
        }
        swap(arr,++lessEqual,right);//整个lessEqual全部覆盖数组
        return lessEqual;
    }

    /**
     *荷兰国旗问题,中间枢纽的值可以是n个相等的数
     * arr[left...right],以arr[R]做划分值
     */
    public static int[] netherLandFlag(int[] arr,int left,int right) {
        if (left > right) return new int[]{-1,-1};//数组里面的值随便取
        if (left == right) return new int[]{left,right};
        int less = left - 1;//左边界
        int more = right;//右边界
        int index = left;
        while (index < more) {//左右边界向中心靠拢，不能撞上
            if (arr[index] == arr[right]) {
                index++;
            }else if (arr[index] < arr[right]) {
                swap(arr,index++,++less);
            }else{
                swap(arr,index,--more);
            }
        }
        swap(arr,more,right);//让最后一个摁在最右边的元素归位
        return new int[] {less + 1,more};
    }

    /**
     *快速排序1.0版本
     * patition只能取一个数进行快排
     */
    public static void quickSort1(int[] arr) {
        if (arr == null || arr.length ==0) return;
        process1(arr,0,arr.length -1);
    }
    public static void process1(int[] arr,int left,int right) {
        if (left >= right) return;
        int mid = partition(arr,left,right);
        process1(arr,left,mid -1);
        process1(arr,mid + 1,right);
    }

    /**
     * 快速排序2.0版本
     * patition为相同数的一个数组进行快排
     * patition以数组最右边的一个数进行快排
     */
    public static void quickSort2(int[] arr) {
        if (arr == null || arr.length ==0) return;
        process2(arr,0,arr.length -1);
    }

    public static void process2(int[] arr,int left,int right) {
        if (left >= right) return;
        int[] mid = netherLandFlag(arr, left, right);
        process2(arr,left,mid[0] -1);
        process2(arr,mid[1] + 1,right);
    }

    /**
     * patiton为随机的一组相同的数
     *快速排序3.0版本之随机快速排序
     * 性能最好！！！！
     */
    public static void quickSort3(int[] arr) {
        if (arr == null || arr.length ==0) return;
        process3(arr,0,arr.length -1);
    }

    public static void process3(int[] arr,int left,int right) {
        if (left >= right) return;
        swap(arr, (int) (left+(Math.random()*(right - left + 1))),right);
        int[] mid = netherLandFlag(arr, left, right);
        process3(arr,left,mid[0] -1);
        process3(arr,mid[1] + 1,right);
    }

    /**
     * 随机快速排序3.0非递归版本
     */
    public static class Op {
        // 要处理的是什么范围上的排序
        public int left;
        public int right;
        public Op(int left, int right) {
            this.left = left;
            this.right = right;
        }
    }
    public static void quickSort4(int[] arr) {
        if (arr == null || arr.length < 2) return;
        int N = arr.length;
        swap(arr, (int) (Math.random() * N),N -1);
        int[] mid = netherLandFlag(arr,0,N - 1);
        int el= mid[0];
        int er = mid[1];
        Stack<Op> stack = new Stack<>();
        stack.push(new Op(0,el -1));//把[0,el -1]压入栈中
        stack.push(new Op(er + 1,N - 1));//把[er + 1,N -1]压入栈中
        while (!stack.isEmpty()) {
            Op op = stack.pop();
            //大区域化小区域，小区域化成更小的区域，套娃下去
            if (op.left < op.right) {
                swap(arr, (int) (op.left + (Math.random() * (op.right - op.left + 1))),op.right);
                mid = netherLandFlag(arr,op.left,op.right);
                el = mid[0];
                er = mid[1];
                stack.push(new Op(op.left,el -1));//把[0,el -1]压入栈中
                stack.push(new Op(er + 1,op.right));//把[er + 1,N -1]压入栈中
            }
        }
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
        int maxValue = 100;
        boolean succeed = true;
        System.out.println("quickSort测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            int[] arr3 = copyArray(arr1);
            int[] arr4 = copyArray(arr1);
            quickSort1(arr1);
            quickSort2(arr2);
            quickSort3(arr3);
            quickSort4(arr4);
            if (!isEqual(arr1, arr2) || !isEqual(arr2, arr3) || !isEqual(arr2, arr4)) {
                succeed = false;
                break;
            }
        }
        System.out.println(succeed ? "quickSort测试正确啦！！" : "fuking code!");
    }
}
