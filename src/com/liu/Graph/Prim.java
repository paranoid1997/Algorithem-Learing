package com.liu.Graph;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class Prim {

    public static class edgeComparator implements Comparator<Edge> {

        @Override
        public int compare(Edge o1, Edge o2) {
            return o1.weight - o2.weight;
        }
    }

    /**
     *prim算法返回最小边的集合
     */
    public static Set<Edge> primMST(Graph graph) {
        PriorityQueue<Edge> queue = new PriorityQueue<>(new edgeComparator());
        HashSet<Node> nodeSet = new HashSet<>();
        Set<Edge> result = new HashSet<>();//依次挑选的边放在result里
        for (Node node : graph.nodes.values()) {
            if (!nodeSet.contains(node)) {
                nodeSet.add(node);
                for (Edge edge : node.edges) {
                    //有一个点解锁所有的边
                    queue.add(edge);
                }
                while (!queue.isEmpty()) {
                    Edge edge = queue.poll();//弹出解锁边中最小的边
                    Node toNode = edge.to;//解锁新的节点
                    if (nodeSet.contains(toNode)) {
                        //不含有的时候 就是新的点
                        nodeSet.add(toNode);
                        result.add(edge);
                        for (Edge nextEdge : toNode.edges) {
                            queue.add(nextEdge);
                        }
                    }
                }
            }
        }
        return result;
    }
    // 请保证graph是连通图
    // graph[i][j]表示点i到点j的距离，如果是系统最大值代表无路
    // 返回值是最小连通图的路径之和
    public static int prim(int[][] graph) {
        int size = graph.length;
        int[] distance = new int[size];
        boolean[] visit = new boolean[size];
        visit[0] = true;
        int sum = 0;
        for (int i = 0; i < size; i++) {
            distance[i] = graph[0][i];
        }
        for (int i = 1; i < size; i++) {
            int minPath = Integer.MAX_VALUE;
            int minIndex = -1;
            for (int j = 0; j < size; j++) {
                if (!visit[j] && distance[j] < minPath) {
                    minPath = distance[j];
                    minIndex = j;
                }
            }
            if (minIndex == -1) {
                return sum;
            }
            visit[minIndex] = true;
            sum += minPath;
            for (int j = 0; j < size; j++) {
                if (!visit[j] && distance[j] > graph[minIndex][j]) {
                    distance[j] = graph[minIndex][j];
                }
            }
        }
        return sum;
    }
}
