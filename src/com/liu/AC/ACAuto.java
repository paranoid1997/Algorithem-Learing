package com.liu.AC;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ACAuto {

    public static class Node {
        public String end;
        // 只有在上面的end变量不为空的时候，endUse才有意义
        // 表示，这个字符串之前有没有加入过答案
        public boolean endUse;
        public Node fail;
        public Node[] nexts;

        public Node() {
            endUse = false;
            end = null;
            fail = null;
            nexts = new Node[26];
        }
    }
    public static class ACAutomation {
        private Node root;

        public ACAutomation() {
            root = new Node();
        }
        /**
         * 敏感词的插入操作
         */
        public void insert(String s) {
            char[] str = s.toCharArray();
            Node cur = root;
            int index = 0;
            for (int i = 0; i < str.length; i++) {
                index = str[i] - 'a';
                if (cur.nexts[index] == null) {
                    cur.nexts[index] = new Node();
                }
                cur = cur.nexts[index];
            }
            cur.end = s;
        }
        /**
         * build函数的功能就是构建fail指针
         */
        public void build() {
            LinkedList<Node> queue = new LinkedList<>();
            queue.add(root);
            Node cur = null;//父亲
            Node cfail = null;//子节点
            while (!queue.isEmpty()) {
                cur = queue.poll();
                for (int i = 0; i < 26; i++) {
                    if (cur.nexts[i] != null) {
                        //如果真的有i号儿子
                        cur.nexts[i].fail = root;//先把i号节点的fail指针指向父亲,不合适再改
                        cfail = cur.fail;//cur.fail = null
                        while (cfail != null) {
                            if (cfail.nexts[i] != null) {
                                cur.nexts[i].fail = cfail.nexts[i];
                                break;
                            }
                            cfail = cfail.fail;
                        }
                        queue.add(cur.nexts[i]);
                    }
                }
            }
        }

        /**
         * 查询一个大文章里面包含的所有敏感词
         */
        public List<String> containWords(String content) {
            char[] str = content.toCharArray();
            Node cur = root;
            Node follow = null;
            int index = 0;
            List<String> ans = new ArrayList<>();
            for (int i = 0; i < str.length; i++) {
                index = str[i] - 'a';
                // 如果当前字符在这条路上没配出来，
                // 就随着fail方向走向下条路径
                while (cur.nexts[index] == null && cur != root) {
                    //如果到了最后一个节点没有路了
                    //跳到父节点上去了
                    cur = cur.fail;
                }
                // 1) 现在来到的路径，是可以继续匹配的
                // 2) 现在来到的节点，就是前缀树的根节点
                cur = cur.nexts[index] != null ? cur.nexts[index] : root;
                follow = cur;
                while (follow != root) {
                    //遍历整个fail指针找敏感词
                    if (follow.endUse) {
                        //如果某个字符遍历过一遍
                        //直接跳出
                        break;
                    }
                    if (follow.end != null) {
                        //收集答案
                        ans.add(follow.end);
                        follow.endUse = true;
                    }
                    follow = follow.fail;
                }
            }
            return ans;
        }
    }

    public static void main(String[] args) {
        ACAutomation ac = new ACAutomation();
        ac.insert("dhe");
        ac.insert("he");
        ac.insert("abcdheks");
        // 设置fail指针
        ac.build();

        List<String> contains = ac.containWords("abcdhekskdjfafhasldkflskdjhwqaerheuv");
        for (String word : contains) {
            System.out.println(word);
        }
    }
}
