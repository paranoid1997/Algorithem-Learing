package com.liu.Xor;

/**
 * 给定一个非负整数num
 * 如何不用循环语句
 * 返回>=num,并且离num最近的，2的某次方
 */
public class Near2Power {

    public static int near2Power(int num) {
        if (num < 0)  return 1;
        num--;
        //下面这段代码就是把最高位1后面的所有的0都填充为1
        //因为int是四字节32位，故num >>> 16停止
        //>>>表示无符号右移
        num |= num >>> 1;
        num |= num >>> 2;
        num |= num >>> 4;
        num |= num >>> 8;
        num |= num >>> 16;

        return num+=1;
    }

    public static void main(String[] args) {
        int n = 14;
        System.out.println("离num最近的2的某次方是:" + near2Power(n));
    }
}
