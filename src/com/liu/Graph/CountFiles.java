package com.liu.Graph;

import java.io.File;
import java.util.Stack;

/**
 * 给定一个文件目录的路径
 * 写一个函数统计这个目录下所有的文件数量并返回
 * 隐藏文件也算，但是文件夹不算
 */
public class CountFiles {

    /**
     * 运用栈来解题
     * 先判断边界条件，然后只要是文件夹的放入栈中
     * 然后弹出统计文件的数量
     */

    public static int getFileNumber(String folderPath) {
        File root = new File(folderPath);
        //处理边界条件
        if (!root.isFile() && !root.isDirectory()) {
            return 0;
        }
        if (root.isFile()) {
            return 1;
        }
        Stack<File> stack = new Stack<>();
        stack.add(root);
        int Files = 0;
        while (!stack.isEmpty()) {
            File folder = stack.pop();
            for (File nexts : folder.listFiles()) {
                if (nexts.isFile()) {
                    Files++;
                }
                if (nexts.isDirectory()) {
                    stack.push(nexts);
                }
            }
        }
        return Files;
    }
    public static void main(String[] args) {
        String path = "C:/Users/18053/Desktop/notes2";
        System.out.println("此目录下一共的文件数量：" + getFileNumber(path));
    }
}
