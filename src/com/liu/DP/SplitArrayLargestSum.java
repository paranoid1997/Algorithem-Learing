package com.liu.DP;
/**
 * 给定一个整型数组 arr，数组中的每个值都为正数，表示完成一幅画作需要的时间，
 * 再给定一个整数 k，表示画匠的数量，每个画匠只能画连在一起的画作。
 * 所有的画家并行工作，请 返回完成所有的画作需要的最少时间。
 * 【举例】
 * arr=[3,1,4]，num=2。
 * 最好的分配方式为第一个画匠画 3 和 1，所需时间为 4。
 * 第二个画匠画 4，所需时间 为 4。
 * 因为并行工作，所以最少时间为 4。如果分配方式为第一个画匠画 3，所需时 间为 3。
 * 第二个画 匠画 1 和 4，所需的时间为 5。那么最少时间为 5，显然没有第一 种分配方式好。所以返回 4。
 * arr=[1,1,1,4,3]，num=3。
 * 最好的分配方式为第一个画匠画前三个 1，所需时间为 3。第二个画匠画 4，所需时间 为 4。 第三个画匠画 3，所需时间为 3。返回 4。
 */
public class SplitArrayLargestSum {

    public static int splitArray1(int[] arr,int k) {
        int n = arr.length;
        int[] sum = new int[n + 1];
        for (int i = 0; i < n; i++) {
            sum[i + 1] = arr[i] + sum[i];
        }
        int[][] dp = new int[n][k + 1];
        for (int j = 1; j <= k; j++) {
            dp[0][j] = arr[0];
        }
        for (int i = 1; i < n; i++) {
            dp[i][1] = sum(sum,0,i);
        }
        for (int i = 1; i < n; i++) {
            for (int j = 2; j <= k; j++) {
                int ans = Integer.MAX_VALUE;
                for (int leftEnd = 0; leftEnd <= i; leftEnd++) {
                    int leftCost = dp[leftEnd][j - 1];
                    int rightCost = sum(sum,leftEnd + 1, i);
                    int cur = Math.max(leftCost,rightCost);
                    if (cur < ans) {
                        ans = cur;
                    }
                }
                dp[i][j] = ans;
            }
        }
        return dp[n - 1][k];
    }

    /**
     * 四边形不等式
     */
    public static int splitArray2(int[] nums, int K) {
        int N = nums.length;
        int[] sum = new int[N + 1];
        for (int i = 0; i < N; i++) {
            sum[i + 1] = sum[i] + nums[i];
        }
        int[][] dp = new int[N][K + 1];
        int[][] best = new int[N][K + 1];
        for (int j = 1; j <= K; j++) {
            dp[0][j] = nums[0];
            best[0][j] = -1;
        }
        for (int i = 1; i < N; i++) {
            dp[i][1] = sum(sum, 0, i);
            best[i][1] = -1;
        }
        // 从第2列开始，从左往右
        // 每一列，从下往上
        // 为什么这样的顺序？因为要去凑（左，下）优化位置对儿！
        for (int j = 2; j <= K; j++) {
            for (int i = N - 1; i >= 1; i--) {
                int down = best[i][j - 1];
                // 如果i==N-1，则不优化上限
                int up = i == N - 1 ? N - 1 : best[i + 1][j];
                int ans = Integer.MAX_VALUE;
                int bestChoose = -1;
                for (int leftEnd = down; leftEnd <= up; leftEnd++) {
                    int leftCost = leftEnd == -1 ? 0 : dp[leftEnd][j - 1];
                    int rightCost = leftEnd == i ? 0 : sum(sum, leftEnd + 1, i);
                    int cur = Math.max(leftCost, rightCost);
                    if (cur < ans) {
                        ans = cur;
                        bestChoose = leftEnd;
                    }
                }
                dp[i][j] = ans;
                best[i][j] = bestChoose;
            }
        }
        return dp[N - 1][K];
    }
    public static int splitArray3(int[] nums, int M) {
        long sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }
        long l = 0;
        long r = sum;
        long ans = 0;
        while (l <= r) {
            long mid = (l + r) / 2;
            long cur = getNeedParts(nums, mid);
            if (cur <= M) {
                ans = mid;
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return (int) ans;
    }

    public static int getNeedParts(int[] arr, long aim) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > aim) {
                return Integer.MAX_VALUE;
            }
        }
        int parts = 1;
        int all = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (all + arr[i] > aim) {
                parts++;
                all = arr[i];
            } else {
                all += arr[i];
            }
        }
        return parts;
    }

    public static int[] randomArray(int len, int maxValue) {
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * maxValue);
        }
        return arr;
    }

    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static int sum(int[] sum,int left,int right) {
        //算出arr中i-j的累加和
        return sum[right + 1] - sum[left];
    }

    public static void main(String[] args) {
        int N = 100;
        int maxValue = 100;
        int testTime = 10000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * N) + 1;
            int M = (int) (Math.random() * N) + 1;
            int[] arr = randomArray(len, maxValue);
            int ans1 = splitArray1(arr, M);
            int ans2 = splitArray2(arr, M);
            int ans3 = splitArray3(arr, M);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.print("arr : ");
                printArray(arr);
                System.out.println("M : " + M);
                System.out.println("ans1 : " + ans1);
                System.out.println("ans2 : " + ans2);
                System.out.println("ans3 : " + ans3);
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("测试结束");
    }
}
