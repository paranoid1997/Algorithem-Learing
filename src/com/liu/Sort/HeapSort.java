package com.liu.Sort;

import java.util.Arrays;

public class HeapSort {
    /**
     * 堆排序
     * @param arr
     */
    public static void heapSort(int[] arr) {
        if (arr == null || arr.length == 0 ) return;
        //从上到下进行建堆，O(nlogn)
//        for (int i = 0; i < arr.length; i++) {
//            heapInsert(arr,i);
//        }
        //从下往上建堆，O(n)
        for (int i = arr.length - 1; i >= 0 ; i--) {
            heapify(arr,i,arr.length);
        }
        //进行堆排序
        //把数组元素的第一个和数组元素最后进行交换，
        // 然后调整堆结构
        int heapSize = arr.length;
        swap(arr,0,--heapSize);
        while (heapSize > 0) {
            heapify(arr,0,heapSize);
            swap(arr,0,--heapSize);
        }
    }
    /**
     * 插入堆的结构里，向上移动变成大根堆
     */
    public static void heapInsert(int[] arr, int index) {
        while (arr[index] > arr[(index - 1)/2]) {//如果当前节点比父亲节点大
            swap(arr, index, (index - 1) / 2);//当前节点和父亲节点交换
            index = (index - 1) / 2;//当前节点来到父亲节点
        }
    }

    /**
     * 从index位置，往下看，不断的向下沉
     * 停：较大的孩子都不再比index位置的数大；已经没孩子了
     * 通常用于删除堆上一个元素，然后再调整堆结构
     */
    public static void heapify(int[] arr, int index, int heapSize) {
        int left = index * 2 + 1;//left指向左孩子节点
        while (left < heapSize) {//如果有左孩子，可能有右孩子也可能没有
            int largest = left + 1 < heapSize && arr[left + 1] > arr[left] ? left + 1 : left;//选出较大孩子
            //考虑父节点和左右节点谁的值更大
            // 然后把值最大的下标赋值给largest
            largest = arr[largest] > arr[index] ? largest : index;
            if (largest == index) {
                break;//如果父节点不需要调整，则直接跳出整个循环
            }
            //index和较大值的孩子，需要互换
            swap(arr, largest, index);
            index = largest;//父节点index到下一个孩子节点上
            left = index * 2 + 1;//再继续往下比较需不需要往下沉
        }
    }

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    // for test
    public static void comparator(int[] arr) {
        Arrays.sort(arr);
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
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            heapSort(arr1);
            comparator(arr2);
            if (!isEqual(arr1, arr2)) {
                succeed = false;
                break;
            }
        }
        System.out.println(succeed ? "HeapSort测试正确!" : "fucking code!");
    }
}
