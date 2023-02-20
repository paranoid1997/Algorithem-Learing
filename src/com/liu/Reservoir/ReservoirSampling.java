package com.liu.Reservoir;

public class ReservoirSampling {

    public static void main(String[] args) {
        int test = 100000;
        int[] count = new int[1730];
        for (int i = 0; i < test; i++) {
            int[] bag = new int[10];
            int bagi = 0;
            for (int num = 1; num <= 1729; num++) {
                if (num <= 10) {
                    bag[bagi++] = num;
                }else {
                    //num > 10
                    if (random(num) <= 10) {
                        bagi = (int) (Math.random() * 10);
                        bag[bagi] = num;
                    }
                }
            }
            for (int num : bag) {
                count[num]++;
            }
        }
        for (int i = 0; i <= 1729; i++) {
            System.out.println(count[i]);
        }
    }
    //等概率返回1-i中的一个数字
    public static int random(int i) {
        return (int) ((Math.random() * i) + 1);
    }
}
