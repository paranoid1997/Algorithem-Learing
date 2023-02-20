package com.liu.Graph;

import java.util.*;

/**
 * 并查集 + 小根堆实现
 */
public class Kruskal {

    public static class edgeComparator implements Comparator<Edge> {

        @Override
        public int compare(Edge o1, Edge o2) {
            return o1.weight - o2.weight;
        }
    }
    public static Set<Edge> kruskalMST(Graph graph) {
        UnionFind unionFind = new UnionFind(graph.nodes.values());
        PriorityQueue<Edge> queue = new PriorityQueue<>(new edgeComparator());
        for (Edge edge : graph.edges) {
            queue.add(edge);
        }
        HashSet<Edge> result = new HashSet<>();//进行边去重
        while (!queue.isEmpty()) {
            Edge edge = queue.poll();
            if (!unionFind.isSameSet(edge.from,edge.to)) {
                //如果两个边都在一个集合
                //则必存在回路，而kruskal不存在回路
                result.add(edge);
                unionFind.union(edge.from,edge.to);
            }
        }
        return result;
    }

    // Union-Find Set
    public static class UnionFind {
        // key 某一个节点， value key节点往上的节点
        private HashMap<Node, Node> fatherMap;
        // key 某一个集合的代表节点, value key所在集合的节点个数
        private HashMap<Node, Integer> sizeMap;

        public UnionFind(Collection<Node> values) {
            fatherMap = new HashMap<>();
            sizeMap = new HashMap<>();
            for (Node node : values) {
                fatherMap.put(node, node);
                sizeMap.put(node, 1);
            }
        }

        private Node findFather(Node n) {
            Stack<Node> path = new Stack<>();
            while(n != fatherMap.get(n)) {
                path.add(n);
                n = fatherMap.get(n);
            }
            while(!path.isEmpty()) {
                fatherMap.put(path.pop(), n);
            }
            return n;
        }

        public boolean isSameSet(Node a, Node b) {
            return findFather(a) == findFather(b);
        }

        public void union(Node a, Node b) {
            if (a == null || b == null) {
                return;
            }
            Node aHead = findFather(a);
            Node bHead = findFather(b);
            if (aHead != bHead) {
                int aSetSize = sizeMap.get(aHead);
                int bSetSize = sizeMap.get(bHead);
                if (aSetSize <= bSetSize) {
                    fatherMap.put(aHead, bHead);
                    sizeMap.put(bHead, aSetSize + bSetSize);
                    sizeMap.remove(aHead);
                } else {
                    fatherMap.put(bHead, aHead);
                    sizeMap.put(aHead, aSetSize + bSetSize);
                    sizeMap.remove(bHead);
                }
            }
        }
    }
}
