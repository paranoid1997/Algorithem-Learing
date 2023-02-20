package com.liu.DP;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 1.打印一个字符串的全部子序列
 * 2.打印一个字符串的全部不重复值的子序列
 */
public class PrintAllSubsquences {

    public static List<String> subs(String s) {
        char[] str = s.toCharArray();
        String path = "";
        ArrayList<String> ans = new ArrayList<>();
        process(str,0,ans,path);
        return ans;
    }
    // 之前的决定已经不能改变了，就是path
    // str[index....]还能决定，之前已经确定，而后面还能自由选择的话，
    // 把所有生成的子序列，放入到ans里去
    private static void process(char[] str, int index, ArrayList<String> ans, String path) {
        if (index == str.length) {
            ans.add(path);
            return;
        }
        process(str,index + 1,ans,path);//不要这个字符
        process(str,index + 1,ans,path + String.valueOf(str[index]));//要这个字符
    }

    public static List<String> subsNoRepeat(String s) {
        char[] str = s.toCharArray();
        String path = "";
        HashSet<String> set = new HashSet<>();
        process1(str,0,set,path);
        ArrayList<String> ans = new ArrayList<>();
        for (String cur : set) {
            ans.add(cur);
        }
        return ans;

    }

    private static void process1(char[] str, int index, HashSet<String> set, String path) {
        if (index == str.length) {
            set.add(path);
            return;
        }
        String no = path;
        process1(str,index + 1,set,no);
        String yes = path + String.valueOf(str[index]);
        process1(str,index + 1,set,yes);
    }

    public static void main(String[] args) {
        String str = "acccc";
        List<String> ans1 = subs(str);
        for (String s : ans1) {
            System.out.println(s);
        }
        System.out.println("=================");
        List<String> ans2 = subsNoRepeat(str);
        for (String s : ans2) {
            System.out.println(s);
        }

    }
}
