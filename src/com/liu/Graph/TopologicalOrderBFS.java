package com.liu.Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class TopologicalOrderBFS {

    public static class DirectedGraphNode {
        public int label;
        public ArrayList<DirectedGraphNode> neighbors;

        public DirectedGraphNode(int x) {
            label = x;
            neighbors = new ArrayList<DirectedGraphNode>();
        }
    }
    public static ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
        HashMap<DirectedGraphNode,Integer> inMap = new HashMap<>();
        LinkedList<DirectedGraphNode> queue = new LinkedList<>();
        ArrayList<DirectedGraphNode> ans = new ArrayList<>();
        for (DirectedGraphNode cur : graph) {
            inMap.put(cur,0);
        }
        for (DirectedGraphNode cur : graph) {
            for (DirectedGraphNode next : cur.neighbors) {
                inMap.put(next,inMap.get(next) + 1);
            }
        }
        for (DirectedGraphNode cur : inMap.keySet()) {
            if (inMap.get(cur) == 0) {
                queue.add(cur);
            }
        }
        while (!queue.isEmpty()) {
            DirectedGraphNode cur = queue.poll();
            ans.add(cur);
            for (DirectedGraphNode next : cur.neighbors) {
                inMap.put(next,inMap.get(next) - 1);
                if (inMap.get(next) == 0) {
                    queue.add(next);
                }
            }
        }
        return ans;
    }
}
