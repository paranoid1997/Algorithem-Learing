package com.liu.LinkedList;

import java.util.ArrayList;

public class SkipList {

    public static class SkipListNode<K extends Comparable<K>,V> {
        public K key;
        public V value;
        public ArrayList<SkipListNode<K,V>> nextNodes;

        public SkipListNode(K key, V value) {
            this.key = key;
            this.value = value;
            nextNodes = new ArrayList<SkipListNode<K,V>>();
        }
        // node里面的key是否比otherKey小，true，不是false
        public boolean isKeyLess(K otherKey) {
            return otherKey != null &&(key == null || key.compareTo(otherKey) < 0);
        }
        public boolean isKeyEqual(K otherKey) {
            return (key == null && otherKey == null) || (key != null && otherKey != null && key.compareTo(otherKey) == 0);
        }
    }

    public static class SkipListMap<K extends Comparable<K>,V> {
        private static final double PROBABILITY = 0.5;// < 0.5 继续做，>=0.5 停
        private SkipListNode<K,V> head;
        private int size;
        private int maxLevel;

        public SkipListMap() {
            head = new SkipListNode<K, V>(null, null);
            head.nextNodes.add(null);
            size = 0;
            maxLevel = 0;
        }
        // 从最高层开始，一路找下去，
        // 最终，找到第0层的<key的最右的节点
        private SkipListNode<K,V> mostRightLessNode(K key) {
            if (key == null) return null;
            int level = maxLevel;
            SkipListNode<K,V> cur = head;
            while (level >= 0) {
                //从上层跳到下层
                cur = mostRightLessNodeInLevel(key,cur,level--);
            }
            return cur;
        }
        // 在level层里，如何往右移动
        // 现在来到的节点是cur，来到了cur的level层，在level层上，找到<key最后一个节点并返回
        private SkipListNode<K, V> mostRightLessNodeInLevel(K key, SkipListNode<K, V> cur, int level) {
            SkipListNode<K, V> next = cur.nextNodes.get(level);
            while (next != null && next.isKeyLess(key)) {
                cur = next;
                next = cur.nextNodes.get(level);
            }
            return cur;
        }
        public boolean containsKey(K key) {
            if (key == null) return false;
            SkipListNode<K, V> less = mostRightLessNode(key);
            SkipListNode<K, V> next = less.nextNodes.get(0);
            return next != null && next.isKeyEqual(key);
        }

        public void add(K key,V value) {
            if (key == null) return;
            SkipListNode<K, V> less = mostRightLessNode(key);
            SkipListNode<K, V> find = less.nextNodes.get(0);
            if (find != null && find.isKeyEqual(key)) {
                find.value = value;
            }else {
                size++;
                int newNodeLevel = 0;
                while (Math.random() < PROBABILITY) {
                    newNodeLevel++;
                }
                while (newNodeLevel > maxLevel) {
                    //如果最左边head节点比新节点的层数少
                    //则最左边的head节点就一直加层数到和新节点一样高
                    head.nextNodes.add(null);
                    maxLevel++;
                }
                SkipListNode<K, V> newNode = new SkipListNode<>(key, value);
                for (int i = 0; i <= newNodeLevel; i++) {
                    //一共有多少层就有多少链表
                    newNode.nextNodes.add(null);
                }
                int level = maxLevel;
                SkipListNode<K,V> pre = head;
                while (level >= 0) {
                    //从最高层开始
                    // level 层中，找到最右的 < key 的节点
                    pre = mostRightLessNodeInLevel(key,pre,level);
                    if (level <= newNodeLevel) {
                        //相当于插入节点的操作
                        newNode.nextNodes.set(level,pre.nextNodes.get(level));
                        pre.nextNodes.set(level,newNode);
                    }
                    level--;
                }
            }
        }
        public void remove(K key) {
            if (containsKey(key)) {
                //一直删到某一层节点有数为止
                size--;
                int level = maxLevel;
                SkipListNode<K,V> pre = head;
                while (level >= 0) {
                    pre = mostRightLessNodeInLevel(key,pre,level);
                    SkipListNode<K, V> next = pre.nextNodes.get(level);
                    // 1）在这一层中，pre下一个就是key
                    // 2）在这一层中，pre的下一个key是>要删除key
                    if (next != null && next.isKeyEqual(key)) {
                        //相当于链表节点删除操作
                        pre.nextNodes.set(level,next.nextNodes.get(level));
                    }
                    // 在level层只有一个节点了，就是默认节点head
                    if (level != 0 && pre == head && pre.nextNodes.get(level) == null) {
                        head.nextNodes.remove(level);
                        maxLevel--;
                    }
                    level--;
                }
            }
        }

        public K firstKey() {
            return head.nextNodes.get(0) != null ? head.nextNodes.get(0).key : null;
        }

        public K lastKey() {
            int level = maxLevel;
            SkipListNode<K, V> cur = head;
            while (level >= 0) {
                SkipListNode<K, V> next = cur.nextNodes.get(level);
                while (next != null) {
                    cur = next;
                    next = cur.nextNodes.get(level);
                }
                level--;
            }
            return cur.key;
        }

        public K ceilingKey(K key) {
            if (key == null) {
                return null;
            }
            SkipListNode<K, V> less = mostRightLessNode(key);
            SkipListNode<K, V> next = less.nextNodes.get(0);
            return next != null ? next.key : null;
        }

        public K floorKey(K key) {
            if (key == null) {
                return null;
            }
            SkipListNode<K, V> less = mostRightLessNode(key);
            SkipListNode<K, V> next = less.nextNodes.get(0);
            return next != null && next.isKeyEqual(key) ? next.key : less.key;
        }

        public int size() {
            return size;
        }

        public V get(K key) {
            if (key == null) {
                return null;
            }
            SkipListNode<K, V> less = mostRightLessNode(key);
            SkipListNode<K, V> next = less.nextNodes.get(0);
            return next != null && next.isKeyEqual(key) ? next.value : null;
        }
    }


    // for test
    public static void printAll(SkipListMap<String, String> obj) {
        for (int i = obj.maxLevel; i >= 0; i--) {
            System.out.print("Level " + i + " : ");
            SkipListNode<String, String> cur = obj.head;
            while (cur.nextNodes.get(i) != null) {
                SkipListNode<String, String> next = cur.nextNodes.get(i);
                System.out.print("(" + next.key + " , " + next.value + ") ");
                cur = next;
            }
            System.out.println();
        }
    }


    public static void main(String[] args) {
        SkipListMap<String, String> test = new SkipListMap<>();
        printAll(test);
        System.out.println("======================");
        test.add("A", "10");
        printAll(test);
        System.out.println("======================");
        test.remove("A");
        printAll(test);
        System.out.println("======================");
        test.add("E", "E");
        test.add("B", "B");
        test.add("A", "A");
        test.add("F", "F");
        test.add("C", "C");
        test.add("D", "D");
        printAll(test);
        System.out.println("======================");
        System.out.println(test.containsKey("B"));
        System.out.println(test.containsKey("Z"));
        System.out.println(test.firstKey());
        System.out.println(test.lastKey());
        System.out.println(test.floorKey("D"));
        System.out.println(test.ceilingKey("D"));
        System.out.println("======================");
        test.remove("D");
        printAll(test);
        System.out.println("======================");
        System.out.println(test.floorKey("D"));
        System.out.println(test.ceilingKey("D"));

    }
}
