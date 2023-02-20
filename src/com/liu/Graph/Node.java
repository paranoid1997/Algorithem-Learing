package com.liu.Graph;


import java.util.ArrayList;

public class Node {
    public int value;
    public int in;//入度
    public int out;//出度
    public ArrayList<Node> nexts;//邻居节点
    public ArrayList<Edge> edges;//边

    public Node(int value) {
        this.value = value;
        in = 0;
        out = 0;
        nexts = new ArrayList<>();
        edges = new ArrayList<>();
    }
}
