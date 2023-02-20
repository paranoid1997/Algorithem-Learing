package com.liu.DP;

/**
 * 字节原题
 * 给定一个正数数组arr，请把arr中所有的数分成两个集合
 * 如果arr长度为偶数，两个集合包含数的个数要一样多
 * 如果arr长度为奇数，两个集合包含数的个数必须只差一个
 * 请尽量让两个集合的累加和接近
 * 返回:
 * 最接近的情况下，较小集合的累加和
 * （较大集合的累加和一定是所有数累加和减去较小集合的累加和
 */
public class SplitSumClosedSizeHalf {

    public static int splitSumClosedSizeHalf(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        if (arr.length % 2 == 0) {
            return process(arr,0,arr.length / 2,sum / 2);
        }else {
            return Math.max(process(arr,0,arr.length / 2,sum / 2),
                        process(arr,0,arr.length / 2 + 1,sum / 2));
        }
    }
    // arr[i....]自由选择，挑选的个数一定要是picks个，累加和<=rest, 离rest最近的返回
    private static int process(int[] arr, int index, int picks, int rest) {
        if (index == arr.length) {
            return picks == 0 ? 0 : -1;
        }else {
            int p1 = process(arr,index + 1,picks,rest);
            int p2 = -1;
            int next = -1;//要了以后是无效解
            if (arr[index] <= rest) {
                next = process(arr,index + 1,picks - 1,rest - arr[index]);
            }
            if (next != -1) {
                p2 = arr[index] + next;
            }
            return Math.max(p1,p2);
        }
    }

    public static int splitSumClosedSizeHalfDp(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        sum /= 2;
        int n = arr.length;
        int m = (n + 1) / 2;
        //                      index  picks  sum
        int[][][] dp = new int[n + 1][m + 1][sum + 1];
        for (int rest = 0; rest <= sum; rest++) {
            for (int picks = 1; picks <= m; picks++) {
                dp[n][0][rest] = 0;
                dp[n][picks][rest] = -1;
            }
        }
        for (int index = n - 1; index >= 0 ; index--) {
            for (int picks = 0; picks <= m; picks++) {
                for (int rest = 0; rest <= sum; rest++) {
                    int p1 =  dp[index + 1][picks][rest];
                    int p2 = -1;
                    int next = -1;//要了以后是无效解
                    if (picks - 1 >= 0 && arr[index] <= rest ) {
                        next = dp[index + 1][picks -1][rest - arr[index]];
                    }
                    if (next != -1) {
                        p2 = arr[index] + next;
                    }
                    dp[index][picks][rest] = Math.max(p1,p2);
                }
            }
        }
        if (arr.length % 2 == 0) {
            return dp[0][arr.length / 2][sum];
        }else {
            return Math.max(dp[0][arr.length / 2][sum], dp[0][(arr.length / 2) + 1][sum]);
        }
    }

    // for test
    public static int[] randomArray(int len, int value) {
        int[] arr = new int[len];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * value);
        }
        return arr;
    }

    // for test
    public static void printArray(int[] arr) {
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int maxLen = 10;
        int maxValue = 50;
        int testTime = 100000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * maxLen);
            int[] arr = randomArray(len, maxValue);
            int ans1 = splitSumClosedSizeHalf(arr);
            int ans2 = splitSumClosedSizeHalfDp(arr);
            if (ans1 != ans2) {
                printArray(arr);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("测试结束");
    }
}
