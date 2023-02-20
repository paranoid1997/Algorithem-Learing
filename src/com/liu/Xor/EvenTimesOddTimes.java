package com.liu.Xor;

public class EvenTimesOddTimes {
    /**
     * 打印一种数，它只出现过奇数次
     * @param arr
     */
    public static void printOddTimesNum1(int[] arr) {
        int xor = 0;
        for (int i = 0; i < arr.length; i++) {
            xor ^= arr[i];//偶数位的数无论什么次序最后都会异或为0
        }
        System.out.println("出现奇数次的一种数为" + xor);
    }

    /**
     * 打印两种数，分别都出现了奇数次
     * @param arr
     */
    public static void printOddTimesNum2(int[] arr) {
        int xor = 0;
        for (int i = 0; i < arr.length; i++) {
            //最后结果必定为两个出现了奇数次的数相异或的结果
            //xor = a ^ b(a,b均是出现奇数次的数)
            xor ^= arr[i];
        }
        int rightOne = xor &(-xor);//提取最右侧的1
        int onlyOne = 0;//eor'
        //可把一个数分为第i为为1的数，和第i为0的数
        //让第i位为1的数和onlyOne异或，第i为0的数onlyOne碰都不碰
        //这思路谁能想的到???反正我是不会。。。。记住过程即可。。
        for (int i = 0; i < arr.length; i++) {
            if ((arr[i] & rightOne) != 0) {
                //最后结果必定会出现a,b之间的一个数
                //其余的数均为出现了偶数次的数
                //故onlyOne = a或onlyOne = b
                onlyOne ^= arr[i];
            }
        }
        //因为xor = a ^ b
        //xor ^ onlyOne = a ^ b ^ a = b;
        System.out.println("两个出现了奇数次的数分别为 a =" + onlyOne + " b = " + (xor ^ onlyOne));
    }
    public static void main(String[] args) {
        //简单测试下
        int[] arr1 = { 3, 3, 2, 3, 1, 1, 1, 3, 1, 1, 1 };
        printOddTimesNum1(arr1);

        int[] arr2 = { 4, 3, 4, 2, 2, 2, 4, 1, 1, 1, 3, 3, 1, 1, 1, 4, 2, 2 };
        printOddTimesNum2(arr2);
    }
}
