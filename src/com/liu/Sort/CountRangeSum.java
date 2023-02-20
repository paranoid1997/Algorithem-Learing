package com.liu.Sort;

import java.util.HashSet;

/**
 * 给你一个整数数组 nums 以及两个整数 lower 和 upper 。求数组中，
 * 值位于范围 [lower, upper] （包含 lower 和 upper）之内的 区间和的个数 。
 * 输入：nums = [-2,5,-1], lower = -2, upper = 2
 * 输出：3
 * 解释：存在三个区间：[0,0]、[2,2] 和 [0,2] ，对应的区间和分别是：-2 、-1 、2 。
 * 链接：https://leetcode-cn.com/problems/count-of-range-sum
 */
public class CountRangeSum {
    public static int countRangeSum(int[] nums,int lower, int upper ) {
        if (nums == null || nums.length == 0) return 0;
        long[] preSum = new long[nums.length];//前缀和数组
        preSum[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            preSum[i] = preSum[i - 1] +nums[i];//求出了一个前缀和数组
        }
        return process(preSum,0,preSum.length -1,lower,upper);
    }

    /**
     *找有多少个前缀和落在[lower,upeper]上可以转化为
     * 以i下标为结尾的数，i-1有多少个数的前缀和落在[i -1-upper,i-1-lower]上！！
     * 既可以转化为归并排序类模型:求presum数组中出现的每一个数x,它之前
     * 有多少个数落在[x-upper,x-lower]上，标准的归并排序的模型！！！
     */
    public static int process(long[] preSum,int left,int right,int lower, int upper) {
        if (left == right) return preSum[left] >=lower && preSum[left] <= upper ? 1 : 0; //0-x上有多少个数达标
        int mid = left + ((right - left) >> 1);
        return process(preSum,left,mid,lower,upper)
                +process(preSum,mid +1,right,lower,upper)
                +merge(preSum,left,mid,right,lower,upper);
    }
    public static int merge(long[] arr, int left, int mid,int right,int lower,int upper) {
        int res = 0;
        int windowL = left;//滑动窗口left
        int windowR = left;//滑动窗口right
        //[windowL,wondowR)
        for (int i = mid + 1; i <= right; i++) {
            long min = arr[i] - upper;
            long max = arr[i] - lower;
            while (windowL <= mid && arr[windowL] < min) windowL++;
            while (windowR <= mid && arr[windowR] <= max) windowR++;
            res += windowR - windowL;
        }
        long[] helper = new long[right - left + 1];
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
        return res;

    }
    /**
     *dp解法
     */
    public static int compartor(int[] nums, int lower, int upper) {
        int len = nums.length;
        int ans = 0;
        // 长整型 是为了避免 整型相加 溢出
        long[][] dp = new long[len][len];
        for (int row = 0; row < len; row++) {
            for (int col = row; col < len; col++) {
                if (col == row) {
                    dp[row][col] = nums[row];
                } else {
                    dp[row][col] = dp[row][col - 1] + nums[col];
                }
                if (dp[row][col] >= lower && dp[row][col] <= upper) ans++;
            }
        }
        return ans;
    }

    /**
     * SB树的解法
     */
    public static int countRangeSum2(int[] nums,int lower,int upper) {
        SizeBalancedTreeSet treeSet = new SizeBalancedTreeSet();
        long sum = 0;
        int ans = 0;
        // 一个数都没有的时候，就已经有一个前缀和累加和为0
        treeSet.add(0);
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            long a = treeSet.lessKeySize(sum - lower + 1);
            long b = treeSet.lessKeySize(sum - upper);
            ans += a - b;
            treeSet.add(sum);
        }
        return ans;
    }
    public static class SBTNode {
        public long key;
        public SBTNode left;
        public SBTNode right;
        public long size;//平衡因子
        public long all;//总的size

        public SBTNode(long key) {
            this.key = key;
            size = 1;
            all = 1;
        }
    }
    public static class SizeBalancedTreeSet {
        private SBTNode root;
        private HashSet<Long> set = new HashSet<>();

        private SBTNode rightRotate(SBTNode cur) {
            //same表示重复的元素
            long same = cur.all - (cur.left != null ? cur.left.all : 0) -  (cur.right != null ? cur.right.all : 0);
            SBTNode leftNode = cur.left;
            cur.left = leftNode.right;
            leftNode.right = cur;
            leftNode.size = cur.size;
            cur.size = (cur.left != null ? cur.left.size : 0) + (cur.right != null ? cur.right.size : 0) + 1;
            leftNode.all = cur.all;
            cur.all = (cur.left != null ? cur.left.all : 0) + (cur.right != null ? cur.right.all : 0) + same;
            return leftNode;
        }
        private SBTNode leftRotate(SBTNode cur) {
            long same = cur.all - (cur.left != null ? cur.left.all : 0) - (cur.right != null ? cur.right.all : 0);
            SBTNode rightNode = cur.right;
            cur.right = rightNode.left;
            rightNode.left = cur;
            rightNode.size = cur.size;
            cur.size = (cur.left != null ? cur.left.size : 0) + (cur.right != null ? cur.right.size : 0) + 1;
            // all modify
            rightNode.all = cur.all;
            cur.all = (cur.left != null ? cur.left.all : 0) + (cur.right != null ? cur.right.all : 0) + same;
            return rightNode;
        }
        private SBTNode maintain(SBTNode cur) {
            if (cur == null) return null;
            long leftSize = cur.left != null ? cur.left.size : 0;
            long leftLeftSize = cur.left != null && cur.left.left != null ? cur.left.left.size : 0;
            long leftRightSize = cur.left != null && cur.left.right != null ? cur.left.right.size : 0;
            long rightSize = cur.right != null ? cur.right.size : 0;
            long rightLeftSize = cur.right != null && cur.right.left != null ? cur.right.left.size : 0;
            long rightRightSize = cur.right != null && cur.right.right != null ? cur.right.right.size : 0;
            if (leftLeftSize > rightSize) {
                cur = rightRotate(cur);
                cur.right = maintain(cur.right);
                cur = maintain(cur);
            }else if (leftRightSize > rightSize) {
                cur.left = leftRotate(cur.left);
                cur = rightRotate(cur);
                cur.left = maintain(cur.left);
                cur.right = maintain(cur.right);
                cur = maintain(cur);
            }else if (rightRightSize > leftSize) {
                cur = leftRotate(cur);
                cur.left = maintain(cur.left);
                cur = maintain(cur);
            }else if (rightLeftSize > leftSize) {
                cur.right = rightRotate(cur.right);
                cur = leftRotate(cur);
                cur.left = maintain(cur.left);
                cur.right = maintain(cur.right);
                cur = maintain(cur);
            }
            return cur;
        }
        private SBTNode add(SBTNode cur,long key,boolean contains) {
            if (cur == null) {
                return new SBTNode(key);
            }else {
                cur.all++;
                if (key == cur.key) {
                    return cur;
                }else {
                    if (!contains) {
                        cur.size++;
                    }
                    if (key < cur.key) {
                        cur.left = add(cur.left,key,contains);
                    }else {
                        cur.right = add(cur.right,key,contains);
                    }
                    return maintain(cur);
                }
            }
        }
        public void add(long sum) {
            boolean contains = set.contains(sum);
            root = add(root,sum,contains);
            set.add(sum);
        }

        public long lessKeySize(long key) {
            SBTNode cur = root;
            long ans = 0;
            while (cur != null) {
                if (key == cur.key) {
                    return ans + (cur.left != null ? cur.left.all : 0);
                }else if (key < cur.key) {
                    cur = cur.left;
                }else {
                    ans += cur.all - (cur.right != null ? cur.right.all : 0);
                    cur = cur.right;
                }
            }
            return ans;
        }
        //>7 取反就是                >7 -> 8 9 10
        public long moreKeySize(long key) {
            return root != null ? (root.all - lessKeySize(key + 1)) : 0;
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
        int lower = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        int upper = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        boolean succeed = true;
        System.out.println("countRangeSum开始测试");
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            //printArray(arr1);
            int[] arr2 = copyArray(arr1);
            int[] arr3 = copyArray(arr1);
            if (countRangeSum(arr1,lower,upper) != compartor(arr2,lower,upper) &&
            compartor(arr2,lower,upper) != countRangeSum2(arr3,lower,upper)) {
                succeed = false;
                printArray(arr1);
                printArray(arr2);
                printArray(arr3);
                break;
            }
        }
        System.out.println(succeed ? "countRangeSum测试正确" : "fucked 错了！！");
    }
}
