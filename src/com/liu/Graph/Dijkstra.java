package com.liu.Graph;

import java.util.HashMap;

/**
 * 此方法为改进后的Dijkstra算法
 * 用加强堆优化实现
 * 优化在之前的算法找到最小值都要遍历所有节点一遍才能找到
 * 现在直接通过反向索引表
 */
public class Dijkstra {

    // 改进后的dijkstra算法
    // 从head出发，所有head能到达的节点，生成到达每个节点的最小路径记录并返回
    public static HashMap<Node,Integer> dijkstra(Node head,int size) {
        NodeHeap nodeHeap = new NodeHeap(size);
        HashMap<Node,Integer> result = new HashMap<>();
        nodeHeap.addOrUpdateOrIgore(head,0);//head到自己距离为0
        while (!nodeHeap.isEmpty()) {
            NodeRecord record = nodeHeap.pop();
            Node cur = record.node;
            int distance = record.distance;
            for (Edge edge : cur.edges) {
                nodeHeap.addOrUpdateOrIgore(edge.to,edge.weight + distance);
            }
            result.put(cur,distance);
        }
        return result;
    }

    public static class NodeRecord {
        public Node node;
        public int distance;

        public NodeRecord(Node node, int distance) {
            this.node = node;
            this.distance = distance;
        }
    }

    public static class NodeHeap {
        public Node[] nodes;
        public HashMap<Node,Integer> heapIndexMap;//反向索引表
        public HashMap<Node,Integer> distanceMap;
        public int size;

        public NodeHeap(int size) {
            nodes = new Node[size];
            heapIndexMap = new HashMap<>();
            distanceMap = new HashMap<>();
            size = 0;
        }
        public boolean isEmpty() {
          return size == 0;
        }
        // 有一个点叫node，现在发现了一个从源节点出发到达node的距离为distance
        // 判断要不要更新，如果需要的话，就更新
        public void addOrUpdateOrIgore(Node node,int distance) {
            if (inHeap(node)) {//update
                distanceMap.put(node,Math.min(distanceMap.get(node),distance));
                //因为距离只可能变小，所以要在堆上进行向上的调整
                heapInsert(node,heapIndexMap.get(node));
            }
            if (!isEntered(node)) {
                nodes[size] = node;
                heapIndexMap.put(node,size);
                distanceMap.put(node,distance);
                heapInsert(node,size++);
            }
        }
        public NodeRecord pop() {
            NodeRecord nodeRecord = new NodeRecord(nodes[0], distanceMap.get(nodes[0]));
            swap(0,size - 1);
            heapIndexMap.put(nodes[size - 1], -1);
            distanceMap.remove(nodes[size - 1]);
            nodes[size - 1] = null;
            heapify(0,--size);
            return nodeRecord;
        }
        public void heapInsert(Node node ,int index) {
            while (distanceMap.get(nodes[index]) < distanceMap.get(nodes[(index - 1) / 2])) {
                swap(index,(index - 1) / 2);
                index = (index - 1) / 2;
            }
        }
        public void heapify(int index,int size) {
            int left = index * 2 + 1;
            while (left < size) {
                int smallest = left + 1 < size && distanceMap.get(nodes[left + 1]) < distanceMap.get(nodes[left]) ? left + 1 : left;
                smallest = distanceMap.get(nodes[smallest]) < distanceMap.get(nodes[index]) ? smallest : index;
                if (smallest == index) {
                    break;
                }
                swap(smallest,index);
                index = smallest;
                left = index * 2 + 1;
            }
        }
        public void swap(int index1, int index2) {
            //反向索引表换
            heapIndexMap.put(nodes[index1],index2);
            heapIndexMap.put(nodes[index2],index1);
            //堆上换
            Node temp = nodes[index1];
            nodes[index1] = nodes[index2];
            nodes[index2] = temp;
        }
        public boolean isEntered(Node node) {
            return heapIndexMap.containsKey(node);
        }

        /**
         *如果这个节点进来过且值不为-1
         * 说明这个元素现在在堆上
         */
        public boolean inHeap(Node node) {
            return isEntered(node) && heapIndexMap.get(node) != -1;
        }
    }
}
