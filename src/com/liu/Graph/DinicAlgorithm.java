package com.liu.Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class DinicAlgorithm {

    public static class Edge {
        public int from;
        public int to;
        public int available;//这条线路还剩余的可用容载量

        public Edge(int from, int to, int available) {
            this.from = from;
            this.to = to;
            this.available = available;
        }
    }
    public static class Dinic {
        private int n;//节点
        private ArrayList<ArrayList<Integer>> nexts;//下条边的集合
        private ArrayList<Edge> edges;//放边的集合
        private int[] depth;//优化 高度数组
        private int[] cur;//优化 哪条边可以自动跳过

        public Dinic(int nums) {
            n = nums + 1;//自动补一个顶点，以防下边从0或者从1开始
            nexts = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                nexts.add(new ArrayList<>());
            }
            edges = new ArrayList<>();
            depth = new int[n];
            cur = new int[n];
        }

        /**
         * @param u:出发边
         * @param v:要到达的边
         * @param r:可用额度
         */
        public void addEdge(int u,int v,int r) {
            int m = edges.size();
            edges.add(new Edge(u,v,r));
            nexts.get(u).add(m);//u这座城市增加一条编号为m的边
            edges.add(new Edge(v,u,0));//反向边
            nexts.get(v).add(m + 1);
        }
        /**
         * 主函数:网络最大流算法
         * s:源点
         * t:目标点
         */
        public int maxFlow(int s,int t) {
            int flow = 0;
            while (bfs(s,t)) {
                //可以从源点到目标点t
                Arrays.fill(cur,0);
                flow += dfs(s,t,Integer.MAX_VALUE);
                Arrays.fill(depth,0);
            }
            return flow;
        }
        private boolean bfs(int s,int t) {
            LinkedList<Integer> queue = new LinkedList<>();
            queue.add(s);
            boolean[] visited = new boolean[n];
            visited[s] = true;
            while (!queue.isEmpty()) {
                int u = queue.poll();
                for (int i = 0; i < nexts.get(u).size(); i++) {
                    Edge edge = edges.get(nexts.get(u).get(i));
                    int v = edge.to;
                    if (!visited[v] && edge.available > 0) {
                        visited[v] = true;
                        depth[v] = depth[u] + 1;
                        queue.add(v);
                    }
                }
            }
            return visited[t];
        }

        /**
         * 当前来到了s点，s可变
         * 最终目标是t，t固定参数
         * 	r，收到的任务
         * 	收集到的流，作为结果返回，ans <= r
         */
        private int dfs(int s, int t, int r) {
            if (s == t || r == 0) {
                return r;
            }
            int f = 0;//代表每条线路能够承载的最大流量
            int flow = 0;//代表最终的网络最大流
            for (; cur[s] < nexts.get(s).size(); cur[s]++) {
                int ei = nexts.get(s).get(cur[s]);//每条边的编号
                Edge e = edges.get(ei);
                Edge other = edges.get(ei^1);//反向边
                if (depth[e.to] == depth[s] + 1 && (f = dfs(e.to,t,Math.min(e.available,r))) != 0) {
                    e.available -= f;
                    other.available += f;
                    flow += f;
                    r -= f;
                    if (r <= 0) {
                        break;
                    }
                }
            }
            return flow;
        }
    }
    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        int cases = cin.nextInt();
        for (int i = 1; i <= cases; i++) {
            int n = cin.nextInt();
            int s = cin.nextInt();
            int t = cin.nextInt();
            int m = cin.nextInt();
            Dinic dinic = new Dinic(n);
            for (int j = 0; j < m; j++) {
                int from = cin.nextInt();
                int to = cin.nextInt();
                int weight = cin.nextInt();
                dinic.addEdge(from, to, weight);
                dinic.addEdge(to, from, weight);
            }
            int ans = dinic.maxFlow(s, t);
            System.out.println("Case " + i + ": " + ans);
        }
        cin.close();
    }
}
