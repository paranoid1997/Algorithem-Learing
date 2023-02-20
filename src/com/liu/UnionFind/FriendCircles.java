package com.liu.UnionFind;

public class FriendCircles {

    public static int findCircleNum(int[][] M) {
        int n = M.length;
        UnionFind unionFind = new UnionFind(n);
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {//遍历上三角
                if (M[i][j] == 1) {
                    unionFind.union(i,j);
                }
            }
        }
        return unionFind.sets();
    }
    /**
     * 数组实现并查集
     */
    public static class UnionFind {
        public int[] parent;
        public int[] size;
        public int[] helper;//相当于栈的功能
        public int sets;//一共有多少集合

        public UnionFind(int n) {
            parent = new int[n];
            size = new int[n];
            helper = new int[n];
            sets = n;
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }
        public int findFather(int i) {
            int index = 0;
            while (i != parent[i]) {
                helper[index++] = i;//相当于入栈
                i = parent[i];
            }
            for (index--;index >= 0;index--) {
                parent[helper[index]] = i;//路径压缩
            }
            return i;
        }
        public void union(int i, int j) {
            int fatheri = findFather(i);
            int fatherj = findFather(j);
            if (fatheri != fatherj) {
                //小树向大树合并
                if (size[fatheri] >= size[fatherj]) {
                    size[fatheri] += size[fatherj];
                    parent[fatherj] = fatheri;
                }else {
                    size[fatherj] += size[fatheri];
                    parent[fatheri] = fatherj;
                }
                sets--;
            }
        }
        public int sets() {
            return sets;
        }
    }
}
