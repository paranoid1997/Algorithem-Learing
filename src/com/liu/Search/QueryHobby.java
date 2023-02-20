package com.liu.Search;

import java.util.ArrayList;
import java.util.HashMap;

public class QueryHobby {

    /**
     * 暴力解
     */
    public static class QueryBox1 {
        private int[] arr;

        public QueryBox1(int[] arr) {
            this.arr = new int[arr.length];
            for (int i = 0; i < this.arr.length; i++) {
                this.arr[i] = arr[i];
            }
        }

        public int query(int left, int right, int v) {
            int ans = 0;
            for (; left <= right; left++) {
                if (arr[left] == v) {
                    ans++;
                }
            }
            return ans;
        }
    }


    /**
     * 预处理结构 + 二分法
     */

    public static class QueryBox2 {
        private HashMap<Integer, ArrayList<Integer>> map;

        public QueryBox2(int[] arr) {
            map = new HashMap<>();
            for (int i = 0; i < arr.length; i++) {
                if (!map.containsKey(arr[i])) {
                    map.put(arr[i],new ArrayList<>());
                }
                map.get(arr[i]).add(i);
            }
        }
        public int query(int left, int right, int v) {
            if (!map.containsKey(v)) {
                return 0;
            }
            ArrayList<Integer> indexArr = map.get(v);
            int a = nearestIndex(indexArr,left - 1);
            int b = nearestIndex(indexArr,right);
            return b - a;
        }
    }


    public static int nearestIndex(ArrayList<Integer> arr, int value) {
        int Left = 0;
        int Right = arr.size() - 1;
        int index = -1; // 记录最右的对号
        while (Left <= Right) {
            int mid = Left + ((Right - Left) >> 1);
            if (arr.get(mid) <= value) {
                index = mid;
                Left = mid + 1;
            } else {
                Right = mid - 1;
            }
        }
        return index;
    }

    public static int[] generateRandomArray(int len, int value) {
        int[] ans = new int[(int) (Math.random() * len) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (int) (Math.random() * value) + 1;
        }
        return ans;
    }

    public static void main(String[] args) {
        int len = 300;
        int value = 20;
        int testTimes = 10000;
        int queryTimes = 1000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            int[] arr = generateRandomArray(len, value);
            int N = arr.length;
            QueryBox1 box1 = new QueryBox1(arr);
            QueryBox2 box2 = new QueryBox2(arr);
            for (int j = 0; j < queryTimes; j++) {
                int a = (int) (Math.random() * N);
                int b = (int) (Math.random() * N);
                int L = Math.min(a, b);
                int R = Math.max(a, b);
                int v = (int) (Math.random() * value) + 1;
                if (box1.query(L, R, v) != box2.query(L, R, v)) {
                    System.out.println("Oops!");
                }
            }
        }
        System.out.println("test end");
    }
}
