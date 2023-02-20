package com.liu.Graph;

import java.util.HashSet;
import java.util.LinkedList;

/**
 * 就是比树的层序遍历多加了一个HashSet
 *判断节点有没有重复加入队列中
 * 起到了去重的作用,其余跟树的层序遍历没有任何区别
 */
public class BFS {

    public static void bfs(Node start) {
        if (start == null) return;
        LinkedList<Node> queue = new LinkedList<>();
        HashSet<Node> set = new HashSet<>();
        queue.add(start);
        set.add(start);
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            System.out.println(cur.value);
            for (Node next : cur.nexts) {
                if (!set.contains(next)) {
                    set.add(next);
                    queue.add(next);
                }
            }
        }
    }
}
