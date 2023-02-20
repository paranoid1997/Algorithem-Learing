package com.liu.UnionFind;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class UnionFind {

    public static class Node<V> {
        V value;

        public Node(V value) {
            this.value = value;
        }
    }
    public static class unionFind<V> {
        public HashMap<V,Node<V>> nodes;//a变成(a)
        public HashMap<Node<V>,Node<V>> parents;
        public HashMap<Node<V>,Integer> sizeMap;

        public unionFind(List<V> values) {
            nodes = new HashMap<>();
            parents = new HashMap<>();
            sizeMap = new HashMap<>();
            for (V cur : values) {
                Node<V> node = new Node<>(cur);//生成a
                nodes.put(cur,node);
                parents.put(node,node);
                sizeMap.put(node,1);
            }
        }

        /**
         *找到节点所属的集合
         */
        public Node<V> findFather(Node<V> cur) {
            Stack<Node<V>> path = new Stack<>();
            while (cur != parents.get(cur)) {
                path.push(cur);
                cur = parents.get(cur);
            }
            //优化
            while (!path.isEmpty()) {
                //把这个节点往下
                // 这条链上的所有节点都扁平的链接到这条节点上
                //使得查找某个节点所在的集合平均O(1)
                parents.put(path.pop(),cur);
            }
            return cur;
        }

        /**
         *判断两个节点是否在同一个集合
         */
        public boolean isSameSet(V a,V b) {
            return findFather(nodes.get(a)) == findFather(nodes.get(b));
        }

        /**
         *把两个集合合并成同一个集合
         */
        public void union(V a,V b) {
            Node<V> aHead = findFather(nodes.get(a));
            Node<V> bHead = findFather(nodes.get(b));
            if (aHead != bHead) {
                int aSetSize = sizeMap.get(aHead);
                int bSetSize = sizeMap.get(bHead);
                Node<V> big = aSetSize >= bSetSize ? aHead : bHead;
                Node<V> small = big == aHead ? bHead : aHead;
                //优化，一定是要用小集合连大集合
                parents.put(small,big);
                sizeMap.put(big,aSetSize + bSetSize);
                sizeMap.remove(small);
            }
        }

        /**
         *返回这个集合的大小
         */
        public int setsSize() {
            return sizeMap.size();
        }
    }
}
