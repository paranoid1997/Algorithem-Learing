package com.liu.Array;

/**
 * 给定一个整数组成的无序数组arr，值可能正、可能负、可能0
 * 给定一个整数值K
 * 找到arr的所有子数组里，哪个子数组的累加和<=K，并且是长度最大的
 * 返回其长度
 */
public class LongestSumSubArr3 {

    /**
     * 解法一: 可能性舍弃 + 窗口不回退
     * 时间复杂度O(N)
     */
    public static int maxLength(int[] arr,int k) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        //minSums代表以i开头取得的最小的累加和
        //minSumEnds代表以i开头取得的最小的累加和的右边界
        int[] minSums = new int[arr.length];
        int[] minSumEnds = new int[arr.length];
        minSums[arr.length - 1] = arr[arr.length - 1];
        minSumEnds[arr.length - 1] = arr.length - 1;
        for (int i = arr.length - 2; i >= 0; i--) {
            if (minSums[i + 1] < 0) {
                //如果累加和变小
                minSums[i] = arr[i] + minSums[i + 1];
                minSumEnds[i] = minSumEnds[i + 1];
            }else {
                //如果累加和不能变小
                //保持不变
                minSums[i] = arr[i];
                minSumEnds[i] = i;
            }
        }
        // 迟迟扩不进来那一块儿的开头位置
        int end = 0;
        int sum = 0;
        int ans = 0;
        for (int i = 0; i < arr.length; i++) {
            // while循环结束之后：
            // 1) 如果以i开头的情况下，累加和<=k的最长子数组是arr[i..end-1]，看看这个子数组长度能不能更新res；
            // 2) 如果以i开头的情况下，累加和<=k的最长子数组比arr[i..end-1]短，更新还是不更新res都不会影响最终结果；
            while (end < arr.length && sum + minSums[end] <= k) {
                sum += minSums[end];
                end = minSumEnds[end] + 1;//这是进不来的第一个位置
            }
            ans = Math.max(ans,end - i);
            if (end > i) {
                // 还有窗口，哪怕窗口没有数字 [i~end) [4,4)
                sum -= arr[i];
            }else {
                // i == end,  即将 i++, i > end, 此时窗口概念维持不住了，所以end跟着i一起走
                //就是如果数组第一个就扩不动了
                end = i + 1;
            }
        }
        return ans;
    }
    public static int maxLength2(int[] arr, int k) {
        int[] h = new int[arr.length + 1];
        int sum = 0;
        h[0] = sum;
        for (int i = 0; i != arr.length; i++) {
            sum += arr[i];
            h[i + 1] = Math.max(sum, h[i]);
        }
        sum = 0;
        int res = 0;
        int pre = 0;
        int len = 0;
        for (int i = 0; i != arr.length; i++) {
            sum += arr[i];
            pre = getLessIndex(h, sum - k);
            len = pre == -1 ? 0 : i - pre + 1;
            res = Math.max(res, len);
        }
        return res;
    }

    public static int getLessIndex(int[] arr, int num) {
        int low = 0;
        int high = arr.length - 1;
        int mid = 0;
        int res = -1;
        while (low <= high) {
            mid = (low + high) / 2;
            if (arr[mid] >= num) {
                res = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return res;
    }

    // for test
    public static int[] generateRandomArray(int len, int maxValue) {
        int[] res = new int[len];
        for (int i = 0; i != res.length; i++) {
            res[i] = (int) (Math.random() * maxValue) - (maxValue / 3);
        }
        return res;
    }
    public static void main(String[] args) {
        System.out.println("开始测试");
        for (int i = 0; i < 10000000; i++) {
            int[] arr = generateRandomArray(10, 20);
            int k = (int) (Math.random() * 20) - 5;
            if (maxLength2(arr, k) != maxLength(arr, k)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("结束测试");
    }
}
