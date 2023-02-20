package com.liu.DP;

/**
 * 求一个字符串中，最长无重复字符子串长度
 */
public class LongestSubCharacters {

    /**
     * 有两种可能性
     * 第一种:到上一次重复字符出现的位置之前
     * 第二种:之前i-1算出的最大长度 + 1(加上包含自身的长度)
     */
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.equals("")) {
            return 0;
        }
        int ans = 1;
        int pre = 1;//向前推了多少的长度
        char[] str = s.toCharArray();
        int[] map = new int[256];
        for (int i = 0; i < map.length; i++) {
            map[i] = -1;//字符串出现在i的位置
        }
        map[str[0]] = 0;
        for (int i = 1; i < str.length; i++) {
            int p1 = i - map[str[i]];
            int p2 = pre + 1;
            int cur = Math.min(p1,p2);
            ans = Math.max(ans,cur);
            pre = cur;
            map[str[i]] = i;
        }
        return ans;
    }
}
