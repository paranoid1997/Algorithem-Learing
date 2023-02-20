package com.liu.Tree;

public class IndexTree {

    public static class indexTree {
        private int[] tree;
        private int n;

        //0不用,下标直接从1开始
        public indexTree(int size) {
            n = size;
            tree = new int[n + 1];
        }

        /**
         *1-index累加和
         */
        public int sum(int index) {
            int sum = 0;
            while (index > 0) {
                sum += tree[index];
                index -= index & -index;//拨掉最右侧的1
            }
            return sum;
        }

        /**
         *找出那些数受到牵连
         * @param index
         * @param d:代表的是增量
         */
        public void update(int index, int d) {
            while (index <= n) {
                tree[index] += d;
        // index & -index : 提取出index最右侧的1出来
                index += index & -index;
            }
        }
    }

    public static class Right {
        private int[] nums;
        private int N;

        public Right(int size) {
            N = size + 1;
            nums = new int[N + 1];
        }

        public int sum(int index) {
            int ret = 0;
            for (int i = 1; i <= index; i++) {
                ret += nums[i];
            }
            return ret;
        }

        public void add(int index, int d) {
            nums[index] += d;
        }

    }
    public static void main(String[] args) {
        int N = 100;
        int V = 100;
        int testTime = 2000000;
        indexTree tree = new indexTree(N);
        Right test = new Right(N);
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int index = (int) (Math.random() * N) + 1;
            if (Math.random() <= 0.5) {
                int add = (int) (Math.random() * V);
                tree.update(index, add);
                test.add(index, add);
            } else {
                if (tree.sum(index) != test.sum(index)) {
                    System.out.println("Oops!");
                }
            }
        }
        System.out.println("测试结束");
    }
}
