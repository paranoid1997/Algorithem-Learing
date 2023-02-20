package com.liu.Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class TopologySort {
    //适用于拓扑排序的一定是有向无环图
    public static List<Node> sortedTopology(Graph graph) {
        //key为某个节点,value剩余的入度
        HashMap<Node,Integer> inMap = new HashMap<>();
        //只有入度为0才能进入这个队列
        LinkedList<Node> zeroInQueue = new LinkedList<>();
        for (Node node : graph.nodes.values()) {
            inMap.put(node,node.in);
            if (node.in == 0) {
                zeroInQueue.add(node);
            }
        }
        ArrayList<Node> result = new ArrayList<>();
        while (!zeroInQueue.isEmpty()) {
            Node cur = zeroInQueue.poll();
            result.add(cur);
            for (Node next : cur.nexts) {
                inMap.put(next,inMap.get(next) - 1);
                if (inMap.get(next) == 0) {
                    zeroInQueue.add(next);
                }
            }
        }
        return result;
    }
}