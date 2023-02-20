package com.liu.Graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/**
 * DFS + 记忆化搜索
 * 点次越高，拓扑序排在前面
 */
public class TopologicalOrderDFS {

    public static class DirectedGraphNode {
        public int label;
        public ArrayList<DirectedGraphNode> neighbors;

        public DirectedGraphNode(int x) {
            label = x;
            neighbors = new ArrayList<DirectedGraphNode>();
        }
    }
    public static class Record {
        public DirectedGraphNode node;
        public int nodes;//点次

        public Record(DirectedGraphNode node, int nodes) {
            this.node = node;
            this.nodes = nodes;
        }
    }
    public static class myComparator implements Comparator<Record> {

        @Override
        public int compare(Record o1, Record o2) {
            return o2.nodes - o1.nodes;//按点次从大到小排序
        }
    }
    public static ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
        HashMap<DirectedGraphNode,Record> order = new HashMap<>();
        for (DirectedGraphNode cur : graph) {
            function(cur,order);
        }
        ArrayList<Record> recordArr = new ArrayList<>();
        for (Record r : order.values()) {
            recordArr.add(r);//把所有的点次放进这个数组中
        }
        recordArr.sort(new myComparator());
        ArrayList<DirectedGraphNode> ans = new ArrayList<>();
        for (Record r : recordArr) {
            ans.add(r.node);
        }
        return ans;
    }

    /**
     *这个函数的功能是专门记录点次的
     */
    public static Record function(DirectedGraphNode cur, HashMap<DirectedGraphNode,Record> order) {
        if (order.containsKey(cur)) {
            return order.get(cur);
        }
        int nodes = 0;
        for (DirectedGraphNode next : cur.neighbors) {
            nodes += function(next,order).nodes;
        }
        Record ans = new Record(cur, nodes + 1);//在计算自己的点次
        order.put(cur,ans);//加入缓存
        return ans;

    }
}
