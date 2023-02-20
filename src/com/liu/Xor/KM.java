package com.liu.Xor;

import java.util.HashMap;
import java.util.HashSet;

/**
 * 题目：一个数组中有一个数出现k次，其余都出现了M次，
 * M > 1,K < M,找到出现K次的数。
 * 要求：空间复杂度O(1),时间复杂度O(N)
 */
public class KM {
    /**
     *用HashMap的方法测试代码是否正确
     */
    public static int testCode(int[] arr, int k, int m) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int num : arr) {
            if (map.containsKey(num)) {
                map.put(num,map.get(num) + 1);
            }else {
                map.put(num,1);
            }
        }
        for (int num : map.keySet()) {
            if (map.get(num) == k) {
                return num;
            }
        }
        return -1;
    }
    //请保证arr中，只有一种数出现了k次，其余都出现了M次
    public static int onlyKTimes(int[] arr, int k, int m) {
        int[] t = new int[32];//int型二进制数组
        //t[0] 0位置的1出现了几个
        //t[i] 0位置的i出现了几个
        for(int num : arr) {
            for (int i = 0; i <= 31; i++) {
                t[i] += (num >> i) & 1;//同来表示32位二进制每个位置上是否为0还是为1
                //代表num在i为是1
//                if (((num >> i) & 1) != 0) {
//                    t[i]++;
//                    //优化版本t[i]+= ((num >> i) & 1
//                }
            }
        }
            int ans = 0;
            for (int i = 0; i < 32; i++) {
                //在第i位置上有1
                if (t[i] % m != 0) {//在第i个位置上有1
                 ans |= (1 << i);
                }
            }
            return ans;
    }

    /**
     * 功能：分别把出现了k次的数和其余出现了m的数字放入数组中
     * @param maxKind:最多出现10种不同类型的数
     * @param range
     * @param k
     * @param m
     * @return
     */
    public static int[] randomArray(int maxKind, int range,int k,int m) {
        int ktimeNum = randomNumber(range);//出现了k次的那个数
        int numberKinds = (int)(Math.random() * maxKind) + 2;//保证这个数组至少要有两种数
        // k * 1 + (numberKind - 1) * m
        int[] arr = new int[k + (numberKinds - 1) * m];
        int index = 0;
        //这步就是把出现了k次的那个数依次填到数组里面
        for (; index < k; index++) {
            arr[index] = ktimeNum;
        }
        numberKinds--;//还剩下numberkind - 1种类型的数没有填进数组
        HashSet<Integer> set = new HashSet<>();//保证其余数精确的填入m次
        set.add(ktimeNum);
        while (numberKinds != 0) {
            int curNumber = 0;
            //保证其余数只能填入m次
            do {
                curNumber = randomNumber(range);
            }while (set.contains(curNumber));
            set.add(curNumber);
            numberKinds--;
            for (int i = 0; i < m; i++) {
                arr[index++] = curNumber;
            }
        }
        //代码执行到这一步 arr数组已经填好了，比较有规律
        //我不想数据这么有规律，故我要打乱它
        for (int i = 0; i < arr.length; i++) {
            //i位置的数，我想随机和j位置的数做交换
            int j = (int) (Math.random() * arr.length);//0-N-1
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
        return arr;
    }

    /**
     * 功能：返回一个[-range,range]随机数
     * @param range
     * @return
     */
    public static int randomNumber(int range) {
        return ((int) (Math.random() * range) + 1) - ((int) (Math.random() * range) + 1);
    }
    public static void main(String[] args) {
//        int[] arr = {4,3,1,3,3,1,1,4};
//        int k = 2;//表示4出现了2次
//        int m = 3;//其余都出现了3次
//        System.out.println("找到出现K次的数为:"+ onlyKTimes(arr,2,3));//这只是小小测试下
        int maxKind = 4;//最多有10种数
        int range = 200;
        int testTime = 10000000;
        int max = 9;
        System.out.println("测试开始-----------------");
        long startTime = System.currentTimeMillis(); //获取开始时间
        for (int i = 0; i < testTime; i++) {
            int a = (int)(Math.random() * max) + 1;//a的范围 1-9
            int b = (int)(Math.random() * max) + 1;
            int k = Math.min(a,b);
            int m = Math.max(a,b);
            //k < m
            if (k == m) m++;
            int[] arr = randomArray(maxKind,range,k,m);
            int ans1 = testCode(arr,k,m);
            int ans2 = onlyKTimes(arr,k,m);
            System.out.println(ans2);
            if (ans1 != ans2) {
                System.out.println("测试结果出错了！！！！");
            }
//          if (ans1 == ans2){
//              System.out.println("MoherFucking!!!代码跑通了！！！！");
//          }
        }
        long endTime = System.currentTimeMillis(); //获取结束时间
        System.out.println("测试代码运行时间：" + (endTime - startTime) + "ms"); //输出程序运行时间
        System.out.println("测试结束-----------------");
    }
}
