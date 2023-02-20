package com.liu.UnionFind;

import java.util.ArrayList;
import java.util.List;

/**
 * 这个题目的重点是:动态加入一个二维数组坐标[i,j],
 * 动态加入并查集，动态查看连通的数量
 */
public class NumberOfIslandsII {

    public static List<Integer> numlslands(int m,int n, int[][] positions) {
        UnionFind1 unionFind1 = new UnionFind1(m, n);
        List<Integer> ans = new ArrayList<>();
        for (int[] position : positions) {
            ans.add(unionFind1.connect(position[0],position[1]));
        }
        return ans;
    }
    public static class UnionFind1 {
        private int[] parent;
        //用size[i] = 1表示已经进行初始化
        //size[i] = 0表示没有进行初始化
        private int[] size;
        private int[] help;
        private final int row;
        private final int col;
        private int sets;

        public UnionFind1(int m, int n) {
            row = m;
            col = n;
            sets = 0;
            int len = row * col;
            parent = new int[len];
            size = new int[len];
            help = new int[len];
        }

        private int index(int r, int c) {
            return r * col + c;
        }

        private int findFather(int i) {
            int hi = 0;
            while (i != parent[i]) {
                help[hi++] = i;
                i = parent[i];
            }
            for (hi--; hi >= 0; hi--) {
                parent[help[hi]] = i;
            }
            return i;
        }

        private void union(int r1, int c1, int r2, int c2) {
            if (r1 < 0 || r1 == row || r2 < 0 || r2 == row || c1 < 0 || c1 == col || c2 < 0 || c2 == col) {
                //如果传入的下标越界，则直接return
                return;
            }
            int i1 = index(r1, c1);
            int i2 = index(r2, c2);
            if (size[i1] == 0 || size[i2] == 0) {
                return;
            }
            int f1 = findFather(i1);
            int f2 = findFather(i2);
            if (f1 != f2) {
                if (size[f1] >= size[f2]) {
                    size[f1] += size[f2];
                    parent[f2] = f1;
                } else {
                    size[f2] += size[f1];
                    parent[f1] = f2;
                }
                sets--;
            }
        }
        public int connect(int i,int j) {
            int index = index(i, j);
            if (size[index] == 0 ) {
                parent[index] = index;
                size[index] = 1;
                sets++;
                union(i -1,j,i,j);
                union(i +1,j,i,j);
                union(i,j + 1,i,j);
                union(i,j - 1,i,j);
            }
            return sets;
        }
    }
}
