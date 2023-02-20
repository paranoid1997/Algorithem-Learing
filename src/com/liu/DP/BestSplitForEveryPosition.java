package com.liu.DP;

/**
 * 非负数组切分成左右两部分累加和的最大值数组
 */
public class BestSplitForEveryPosition {

    /**
     * 暴力解
     * 时间复杂度O(n^3)
     */
    public static int[] split1(int[] arr) {
        if (arr == null || arr.length == 0) return new int[0];
        int[] ans = new int[arr.length];
        ans[0] = 0;
        for (int range = 1; range < arr.length; range++) {
            for (int i = 0; i < range; i++) {
                int sumLeft = 0;
                for (int left = 0; left <= i; left++) {
                    sumLeft += arr[left];
                }
                int sumRight = 0;
                for (int right = i + 1; right <= range; right++) {
                    sumRight += arr[right];
                }
                ans[range] = Math.max(ans[range],Math.min(sumLeft,sumRight));
            }
        }
        return ans;
    }
    public static int sum(int[] sum,int left,int right) {
        //算出arr中i-j的累加和
        return sum[right + 1] - sum[left];
    }

    /**
     *利用sum[right + 1] - sum[left]累加和技巧优化
     * 时间复杂度O(n^2)
     */
    public static int[] split2(int[] arr) {
        if (arr == null || arr.length == 0) return new int[0];
        int[] ans = new int[arr.length];
        ans[0] = 0;
        int[] sum = new int[arr.length + 1];
        for (int i = 0; i < arr.length; i++) {
            sum[i + 1] = sum[i] + arr[i];
        }
        for (int range = 1; range < arr.length; range++) {
            for (int i = 0; i < range; i++) {
                int sumLeft = sum(sum,0,i);
                int sumRight = sum(sum,i + 1,range);
                ans[range] = Math.max(ans[range],Math.min(sumLeft,sumRight));
            }
        }
        return ans;
    }

    /**
     * 终极技巧:不回退的技巧
     * 满足以下公式存在不回退的方法
     * ans = 最差{最优{左指标，右指标}}；
     * 时间复杂度O(n)
     */
    public static int[] split3(int[] arr) {
        if (arr == null || arr.length == 0) return new int[0];
        int[] ans = new int[arr.length];
        ans[0] = 0;
        int[] sum = new int[arr.length + 1];
        for (int i = 0; i < arr.length; i++) {
            sum[i + 1] = sum[i] + arr[i];
        }
        int best = 0;
        for (int range = 1; range < arr.length; range++) {
            while (best + 1 < range) {
                //如果best = range,则不好再切分了
                int before = Math.min(sum(sum,0,best),sum(sum,best + 1,range));
                int after = Math.min(sum(sum,0,best + 1),sum(sum,best + 2,range));//满足单调性
                if (after >= before) {
                    //如果满足单调性，最优切分点向右滑动
                    //不回退
                    best++;
                }else {
                    break;
                }
            }
            ans[range] = Math.min(sum(sum,0,best),sum(sum,best + 1,range));
        }
        return ans;
    }
    public static int[] randomArray(int len, int max) {
        int[] ans = new int[len];
        for (int i = 0; i < len; i++) {
            ans[i] = (int) (Math.random() * max);
        }
        return ans;
    }

    public static boolean isSameArray(int[] arr1, int[] arr2) {
        if (arr1.length != arr2.length) {
            return false;
        }
        int N = arr1.length;
        for (int i = 0; i < N; i++) {
                if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }
    public static void main(String[] args) {
        int N = 20;
        int max = 30;
        int testTime = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * N);
            int[] arr = randomArray(len, max);
            int[] ans1 = split1(arr);
            int[] ans2 = split2(arr);
            int[] ans3 = split3(arr);
            if (!isSameArray(ans1, ans2) || !isSameArray(ans1, ans3)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("测试结束");
    }
}
