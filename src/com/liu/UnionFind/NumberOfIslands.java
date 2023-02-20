package com.liu.UnionFind;

/**
 * 给你一个由 '1'（陆地）和 '0'（水）组成的的二维网格，请你计算网格中岛屿的数量。
 * 岛屿总是被水包围，并且每座岛屿只能由水平方向和/或竖直方向上相邻的陆地连接形成。
 * 此外，你可以假设该网格的四条边均被水包围
 * 示例 1：
 *
 * 输入：grid = [
 *   ["1","1","1","1","0"],
 *   ["1","1","0","1","0"],
 *   ["1","1","0","0","0"],
 *   ["0","0","0","0","0"]
 * ]
 * 输出：1
 */
public class NumberOfIslands {


    public static class UnionFind2 {
        public int[] parent;
        public int[] size;
        public int[] helper;
        public int col;
        public int sets;

        public UnionFind2(char[][] board) {
            col = board[0].length;
            sets = 0;
            int row = board.length;
            int len = row * col;
            parent = new int[len];
            size = new int[len];
            helper = new int[len];
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    if (board[i][j] == '1') {
                        int k = index(i,j);//将二维下标转化为一维数组下标
                        parent[k] = k;
                        size[k] = 1;
                        sets++;
                    }
                }
            }
        }
        public int index(int i, int j) {
            return i * col + j;
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
        public void union(int r1,int c1,int r2,int c2) {
            int i1 = index(r1,c1);
            int i2 = index(r2,c2);
            int f1 = findFather(i1);
            int f2 = findFather(i2);
            if (f1 != f2) {
                if (size[f1] >= size[f2]) {
                    size[f1] += size[f2];
                    parent[f2] = f1;
                }else {
                    size[f2] += size[f1];
                    parent[f1] = f2;
                }
                sets--;
            }
        }
        public int sets() {
            return sets;
        }
    }
    /**
     *解法一:用数组实现的并查集来实现
     * 核心思想:把二维[i,j]->映射成一维i
     */
    public static int numlslands2(char[][] board) {
        int row = board.length;
        int col = board[0].length;
        UnionFind2 unionFind2 = new UnionFind2(board);
        for (int j = 1; j < col; j++) {
            //遍历二维数组的这一行
            if (board[0][j - 1] == '1' && board[0][j] == '1') {
                unionFind2.union(0,j - 1,0,j);
            }
        }
        for (int i = 1; i < row; i++) {
            if (board[i - 1][0] == '1' && board[i][0] == '1') {
                //遍历二维数组的这一列
                unionFind2.union(i - 1,0,i,0);
            }
        }
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                //向上或向左依次遍历
                if (board[i][j] == '1') {
                    if (board[i][j-1] == '1') {
                        unionFind2.union(i,j-1,i,j);
                    }
                    if (board[i-1][j] == '1') {
                        unionFind2.union(i-1,j,i,j);
                    }
                }
            }
        }
        return unionFind2.sets();
    }

    /**
     *最优解，用深度优先遍历去做
     */
    public static int numIslands1(char[][] board) {
        int islands = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == '1') {
                    islands++;
                    infect(board, i, j);
                }
            }
        }
        return islands;
    }

    // 从(i,j)这个位置出发，把所有练成一片的'1'字符，变成0
    public static void infect(char[][] board, int i, int j) {
        if (i < 0 || i == board.length || j < 0 || j == board[0].length || board[i][j] != '1') {
            return;
        }
        board[i][j] = 0;
        infect(board, i - 1, j);
        infect(board, i + 1, j);
        infect(board, i, j - 1);
        infect(board, i, j + 1);
    }
    // for test
    public static char[][] generateRandomMatrix(int row, int col) {
        char[][] board = new char[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                board[i][j] = Math.random() < 0.5 ? '1' : '0';
            }
        }
        return board;
    }

    // for test
    public static char[][] copy(char[][] board) {
        int row = board.length;
        int col = board[0].length;
        char[][] ans = new char[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                ans[i][j] = board[i][j];
            }
        }
        return ans;
    }


    public static void main(String[] args) {
        int row = 1000;
        int col = 1000;
        char[][] board1 = generateRandomMatrix(row,col);
        char[][] board2 = copy(board1);
        long start = System.currentTimeMillis();
        System.out.println("感染方法的运行结果: " + numIslands1(board1));
        long end = System.currentTimeMillis();
        System.out.println("感染方法的运行时间: " + (end - start) + " ms");

        start = System.currentTimeMillis();
        System.out.println("并查集(数组实现)的运行结果: " + numlslands2(board2));
        end = System.currentTimeMillis();
        System.out.println("并查集(数组实现)的运行时间: " + (end - start) + " ms");
     }
}
