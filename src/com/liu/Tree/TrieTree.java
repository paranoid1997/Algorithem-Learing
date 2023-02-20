package com.liu.Tree;

import java.util.HashMap;

/**
 * 1）单个字符串中，字符从前到后的加到一棵多叉树上
 * 2）字符放在路上，节点上有专属的数据项（常见的是pass和end值）
 * 3）所有样本都这样添加，如果没有路就新建，如有路就复用
 * 4）沿途节点的pass值增加1，每个字符串结束时来到的节点end值增加1
 *可以完成前缀相关的查询
 */
public class TrieTree {
    /**
     * 限制字母型
     */
    public static class Node1 {
        public int pass;//有多少字母经过这个节点
        public int end;//有多少字母以这个节点作为结束
        public Node1[] nexts;//是否存在下一个节点

        public Node1() {
            pass = 0;
            end = 0;
            nexts = new Node1[26];//26个英语字母
        }
    }

    /**
     * 限制字母型前缀树
     * 增删改查
     */
    public static class Trie1 {
        private Node1 root;

        public Trie1() {
            root = new Node1();//先创建根节点
        }

        /**
         * 前缀树之增加节点
         *
         * @param word
         */
        public void insert(String word) {
            if (word == null) return;
            char[] str = word.toCharArray();
            Node1 node = root;
            node.pass++;
            int path = 0;
            for (int i = 0; i < str.length; i++) {
                path = str[i] - 'a';//由字符，对应成走向那条路
                if (node.nexts[path] == null) {
                    node.nexts[path] = new Node1();
                }
                node = node.nexts[path];
                node.pass++;
            }
            node.end++;
        }

        /**
         * 前缀树之删除节点
         *
         * @param word
         */
        public void delete(String word) {
            if (search(word) != 0) {
                //删除这个单词，首先得这个单词要存在
                char[] str = word.toCharArray();
                Node1 node = root;
                node.pass--;
                int path = 0;
                for (int i = 0; i < str.length; i++) {
                    path = str[i] - 'a';
                    if (--node.nexts[path].pass == 0) {
                        //如果发现下级那个节点pass--为0
                        //直接把后面得节点置为空，因为java有jvm所有后面的节点都自动释放
                        //c++就不能这样
                        node.nexts[path] = null;
                        return;
                    }
                    node = node.nexts[path];
                }
                node.end--;
            }
        }

        /**
         * 所有加入的字符串中，
         * 有几个是以pre这个字符串作为前缀的
         */
        public int prefixNumber(String pre) {
            if (pre == null) return 0;
            char[] str = pre.toCharArray();
            Node1 node = root;
            int index = 0;
            for (int i = 0; i < str.length; i++) {
                index = str[i] - 'a';
                if (node.nexts[index] == null) return 0;
                node = node.nexts[index];
            }
            return node.pass;
        }

        /**
         * 前缀树之查找节点
         * word这个单词之前加入过几次
         *
         * @param word
         */
        public int search(String word) {
            if (word == null) return 0;
            char[] str = word.toCharArray();
            Node1 node = root;
            int index = 0;
            for (int i = 0; i < str.length; i++) {
                index = str[i] - 'a';
                if (node.nexts[index] == null) {
                    //如果这单词后面没路了
                    return 0;
                }
                node = node.nexts[index];
            }
            return node.end;
        }
    }

    /**
     * 不限制字符种类的前缀树
     */
    public static class Node2 {
        public int pass;
        public int end;
        public HashMap<Integer, Node2> nexts;//Integer代表的是字母转化为Ascll的值

        public Node2() {
            pass = 0;
            end = 0;
            nexts = new HashMap<>();
        }
    }

    public static class Trie2 {
        private Node2 root;

        public Trie2() {
            root = new Node2();
        }

        public void insert(String word) {
            if (word == null) {
                return;
            }
            char[] chs = word.toCharArray();
            Node2 node = root;
            node.pass++;
            int index = 0;
            for (int i = 0; i < chs.length; i++) {
                index = (int) chs[i];
                if (!node.nexts.containsKey(index)) {
                    node.nexts.put(index, new Node2());
                }
                node = node.nexts.get(index);
                node.pass++;
            }
            node.end++;
        }

        // word这个单词之前加入过几次
        public int search(String word) {
            if (word == null) {
                return 0;
            }
            char[] chs = word.toCharArray();
            Node2 node = root;
            int index = 0;
            for (int i = 0; i < chs.length; i++) {
                index = (int) chs[i];
                if (!node.nexts.containsKey(index)) {
                    return 0;
                }
                node = node.nexts.get(index);
            }
            return node.end;
        }

        public void delete(String word) {
            if (search(word) != 0) {
                char[] chs = word.toCharArray();
                Node2 node = root;
                node.pass--;
                int index = 0;
                for (int i = 0; i < chs.length; i++) {
                    index = (int) chs[i];
                    if (--node.nexts.get(index).pass == 0) {
                        node.nexts.remove(index);
                        return;
                    }
                    node = node.nexts.get(index);
                }
                node.end--;
            }
        }

        // 所有加入的字符串中，有几个是以pre这个字符串作为前缀的
        public int prefixNumber(String pre) {
            if (pre == null) {
                return 0;
            }
            char[] chs = pre.toCharArray();
            Node2 node = root;
            int index = 0;
            for (int i = 0; i < chs.length; i++) {
                index = (int) chs[i];
                if (!node.nexts.containsKey(index)) {
                    return 0;
                }
                node = node.nexts.get(index);
            }
            return node.pass;
        }
    }

    // for test
    public static String generateRandomString(int strLen) {
        char[] ans = new char[(int) (Math.random() * strLen) + 1];
        for (int i = 0; i < ans.length; i++) {
            int value = (int) (Math.random() * 26);
            ans[i] = (char) (97 + value);
        }
        return String.valueOf(ans);
    }

    // for test
    public static String[] generateRandomStringArray(int arrLen, int strLen) {
        String[] ans = new String[(int) (Math.random() * arrLen) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = generateRandomString(strLen);
        }
        return ans;
    }

    public static void main(String[] args) {
        int arrLen = 100;
        int strLen = 20;
        int testTimes = 100000;
        System.out.println("测试开始");
        for (int i = 0; i < testTimes; i++) {
            String[] arr = generateRandomStringArray(arrLen, strLen);
            Trie1 trie1 = new Trie1();
            Trie2 trie2 = new Trie2();
            for (int j = 0; j < arr.length; j++) {
                double decide = Math.random();
                if (decide < 0.25) {
                    trie1.insert(arr[j]);
                    trie2.insert(arr[j]);
                } else if (decide < 0.5) {
                    trie1.delete(arr[j]);
                    trie2.delete(arr[j]);
                } else if (decide < 0.75) {
                    int ans1 = trie1.search(arr[j]);
                    int ans2 = trie2.search(arr[j]);
                    if (ans1 != ans2) {
                        System.out.println("Oops!");
                    }
                } else {
                    int ans1 = trie1.prefixNumber(arr[j]);
                    int ans2 = trie2.prefixNumber(arr[j]);
                    if (ans1 != ans2) {
                        System.out.println("Oops!");
                    }
                }
            }
        }
        System.out.println("测试结束");
    }
}
