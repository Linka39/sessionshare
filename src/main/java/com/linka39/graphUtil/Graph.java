package com.linka39.graphUtil;

public class Graph {
    VertexNode[] adjList;
    int e;
    int v;
    boolean[] visit;

    public Graph(int v, int e) {
        this.v = v;
        this.e = e;
        adjList = new VertexNode[e + 1];
        visit = new boolean[e + 1];
        for (int i = 0; i < e; i++) {
            visit[i] = false;
        }
    }
}
