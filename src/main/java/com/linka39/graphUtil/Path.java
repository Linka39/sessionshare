package com.linka39.graphUtil;

import java.util.Scanner;


public class Path {
    static Graph G;
    public static void main(String[] args) {
        initGraph();
        System.out.println("输出邻接表存储情况：");
        outGraph();
        System.out.println("深度优先搜索过程如下：");
        DFS(G,0);
        System.out.println();
        for(int i=0;i<G.adjList.length;++i){
            G.visit[i]=false;
        }
        System.out.println("广度优先搜索过程如下：");
        BFS(G,0);
        System.out.println();
    }
    public static void initGraph(){
        G = new Graph(5, 5);
        // 初始化顶点信息
        String[] VertexArr = {"1","2","3","4","5"};
        for (int i = 0; i < G.v; i++) {
            G.adjList[i] = new VertexNode();
            G.adjList[i].name = VertexArr[i];
            G.adjList[i].link = null;
        }
        // 初始化各边信息
        String[][] EdgenodeArr = {
                {"1","2"},{"2","3"},{"3","5"},{"5","2"},{"2","4"}
        };
        for (int i = 0; i < G.e; i++) {
            String e1 = EdgenodeArr[i][0];
            String e2 = EdgenodeArr[i][1];
            int v1 = Index(e1);
            int v2 = Index(e2);
            EdgeNode en2 = new EdgeNode();
            en2.index = v2;
            en2.next = G.adjList[v1].link;
            G.adjList[v1].link = en2;
            // 有向图注释掉
            /*EdgeNode en1 = new EdgeNode();
            en1.index = v1;
            en1.next = G.adjList[v2].link;
            G.adjList[v2].link = en1;*/
        }
    }
    public static void systemIn(){
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入顶点数v和边数e:");
        int v = sc.nextInt();
        int e = sc.nextInt();
        G = new Graph(v, e);
        System.out.println("请输入各顶点信息:");
        for (int i = 0; i < G.v; i++) {
            G.adjList[i] = new VertexNode();
            G.adjList[i].name = sc.next();
            G.adjList[i].link = null;
        }
        System.out.println("请输入各边信息(用空格隔开):");
        for (int i = 0; i < G.e; i++) {
            String e1 = sc.next();
            String e2 = sc.next();
            int v1 = Index(e1);
            int v2 = Index(e2);
            EdgeNode en2 = new EdgeNode();
            en2.index = v2;
            en2.next = G.adjList[v1].link;
            G.adjList[v1].link = en2;
            // 有向图注释掉
            /*EdgeNode en1 = new EdgeNode();
            en1.index = v1;
            en1.next = G.adjList[v2].link;
            G.adjList[v2].link = en1;*/
        }
    }
    public static void outGraph() {//输出邻接表
        EdgeNode en = new EdgeNode();
        for (int i = 0; i < G.e; i++) {
            System.out.print(G.adjList[i].name);
            en = G.adjList[i].link;
            while (en != null) {
                System.out.print("->" + G.adjList[en.index].name);
                en = en.next;
            }
            System.out.println();
        }
    }

    public static int Index(String e1) {
        for (int i = 0; i < G.v; i++) {
            if (G.adjList[i].name.equals(e1)){
                return i;
            }
        }
        return -1;
    }

    public static void DFS(Graph G, int k) {
        System.out.print(G.adjList[k].name+" ");
        G.visit[k] = true;
        EdgeNode p = G.adjList[k].link;
        while(p!=null){
            if(!G.visit[p.index]){
                DFS(G,p.index);
            }
            p=p.next;
        }
    }
    public static void BFS(Graph G, int k) {
        int[] q=new int[G.adjList.length];
        G.visit[k]=true;
        System.out.print(G.adjList[k].name+" ");
        int f=0,r=0,v=0;
        EdgeNode p = G.adjList[k].link;
        while((p!=null)||(f!=r)){
            while(p!=null) {
                v=p.index;
                if(!G.visit[v]) {
                    r++;
                    q[r]=v;
                    System.out.print(G.adjList[v].name+" ");
                    G.visit[v]=true;
                }
                p=p.next;
            }
            if(f!=r) {
                f++;
                v=q[f];
                p=G.adjList[v].link;
            }
        }
    }
}
