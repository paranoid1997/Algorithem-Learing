package com.liu.Greedy;
import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeMap;

/**
 * 给定数组hard和money，长度都为N, hard[i]表示i号的难度，
 * money[i]表示i号工作的收入给定数组ability，长度都为M，ability[j]表示j号人的能力,
 * 每一号工作，都可以提供无数的岗位，难度和收入都一样
 * 但是人的能力必须>=这份工作的难度，才能上班.
 * 返回一个长度为M的数组ans，ans[j]表示j号人能获得的最好收入
 */
public class ChooseWork {

    public static class job {
        public int money;
        public int hard;

        public job(int money, int hard) {
            this.money = money;
            this.hard = hard;
        }
    }

    //对难度数组按从低到高排序，难度一样的时候按钱最高的排在前面
    public static class jobComparator implements Comparator<job> {
        @Override
        public int compare(job o1, job o2) {
            return o1.hard != o2.hard ? (o1.hard - o2.hard) : (o2.money - o1.money);
        }
    }

    public static int[] bestMoney(job[] jobs,int[] ability) {
        Arrays.sort(jobs,new jobComparator());

        //key表示难度,value表示报酬
        TreeMap<Integer, Integer> map = new TreeMap<>();
        map.put(jobs[0].hard,jobs[0].money);
        job pre = jobs[0];
        for (int i = 1; i < jobs.length; i++) {
            if (jobs[i].hard != pre.hard && jobs[i].money > pre.money) {
                pre = jobs[i];
                map.put(jobs[i].hard,jobs[i].money);
            }
        }
        int[] ans = new int[ability.length];
        for (int i = 0; i < ability.length; i++) {
            //ability[i]个人能力 >= key工作需要的能力
            Integer key = map.floorKey(ability[i]);
            if (key != null) {
                ans[i] = map.get(key);
            }else {
                ans[i] = 0;
            }
        }
        return ans;
    }
}
