package com.linka39.graphUtilThree;

/**
 * Java 图邻接表算法梳理
 *
 * @author linka39
 * @date 2020/04/23
 */

import java.io.IOException;
import java.util.*;

public class ListUDG {
    private static int INF = Integer.MAX_VALUE;

    // 邻接表中表对应的链表的顶点
    private class ENode {
        int ivex;       // 该边所指向的顶点的位置
        int weight;     // 该边的权
        ENode nextEdge; // 指向下一条弧的指针
    }

    // 邻接表中表的顶点
    private class VNode {
        char data;          // 顶点信息
        Integer in = 0;         // 顶点入度
        ENode firstEdge;    // 指向第一条依附该顶点的弧
    }

    private int mEdgNum;    // 边的数量
    private VNode[] mVexs;  // 顶点数组


    /*
     * 创建图(自己输入数据)
     */
    public ListUDG() {

        // 输入"顶点数"和"边数"
        System.out.printf("input vertex number: ");
        int vlen = readInt();
        System.out.printf("input edge number: ");
        int elen = readInt();
        if ( vlen < 1 || elen < 1 || (elen > (vlen*(vlen - 1)))) {
            System.out.printf("input error: invalid parameters!\n");
            return ;
        }

        // 初始化"顶点"
        mVexs = new VNode[vlen];
        for (int i = 0; i < mVexs.length; i++) {
            System.out.printf("vertex(%d): ", i);
            mVexs[i] = new VNode();
            mVexs[i].data = readChar();
            mVexs[i].firstEdge = null;
        }

        // 初始化"边"
        mEdgNum = elen;
        for (int i = 0; i < elen; i++) {
            // 读取边的起始顶点和结束顶点
            System.out.printf("edge(%d):", i);
            char c1 = readChar();
            char c2 = readChar();
            int weight = readInt();

            int p1 = getPosition(c1);
            int p2 = getPosition(c2);
            // 初始化node1
            ENode node1 = new ENode();
            node1.ivex = p2;
            node1.weight = weight;
            // 将node1链接到"p1所在链表的末尾"
            if(mVexs[p1].firstEdge == null)
                mVexs[p1].firstEdge = node1;
            else
                linkLast(mVexs[p1].firstEdge, node1);
            // 初始化node2
            ENode node2 = new ENode();
            node2.ivex = p1;
            node2.weight = weight;
            // 将node2链接到"p2所在链表的末尾"
            if(mVexs[p2].firstEdge == null)
                mVexs[p2].firstEdge = node2;
            else
                linkLast(mVexs[p2].firstEdge, node2);
        }
    }

    /*
     * 创建图(用已提供的矩阵)
     *
     * 参数说明：
     *     vexs  -- 顶点数组
     *     edges -- 边
     */
    public ListUDG(char[] vexs, EData[] edges) {

        // 初始化"顶点数"和"边数"
        int vlen = vexs.length;
        int elen = edges.length;

        // 初始化"顶点"
        mVexs = new VNode[vlen];
        for (int i = 0; i < mVexs.length; i++) {
            mVexs[i] = new VNode();
            mVexs[i].data = vexs[i];
            mVexs[i].firstEdge = null;
        }

        // 初始化"边"
        mEdgNum = elen;
        for (int i = 0; i < elen; i++) {
            // 读取边的起始顶点和结束顶点
            char c1 = edges[i].start;
            char c2 = edges[i].end;
            int weight = edges[i].weight;

            // 读取边的起始顶点和结束顶点
            int p1 = getPosition(c1);
            int p2 = getPosition(c2);
            // 初始化node1
            ENode node1 = new ENode();
            node1.ivex = p2;
            node1.weight = weight;
            // 将node1链接到"p1所在链表的末尾"
            if(mVexs[p1].firstEdge == null)
                mVexs[p1].firstEdge = node1;
            else
                linkLast(mVexs[p1].firstEdge, node1);
            // 初始化node2，有向图需要注释掉
            /*ENode node2 = new ENode();
            node2.ivex = p1;
            node2.weight = weight;
            // 将node2链接到"p2所在链表的末尾"
            if(mVexs[p2].firstEdge == null)
                mVexs[p2].firstEdge = node2;
            else
                linkLast(mVexs[p2].firstEdge, node2);*/
        }
    }

    /*
     * 将node节点链接到list的最后
     */
    private void linkLast(ENode list, ENode node) {
        ENode p = list;
        while(p.nextEdge!=null)
            p = p.nextEdge;
        p.nextEdge = node;
    }

    /*
     * 返回ch位置
     */
    private int getPosition(char ch) {
        for(int i=0; i<mVexs.length; i++)
            if(mVexs[i].data==ch)
                return i;
        return -1;
    }

    /*
     * 读取一个输入字符
     */
    private char readChar() {
        char ch='0';

        do {
            try {
                ch = (char)System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } while(!((ch>='a'&&ch<='z') || (ch>='A'&&ch<='Z')));

        return ch;
    }

    /*
     * 读取一个输入字符
     */
    private int readInt() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    /*
     * 深度优先搜索遍历图的递归实现
     */
    private void DFS(int i, boolean[] visited) {
        ENode node;

        visited[i] = true;
        System.out.printf("%c ", mVexs[i].data);
        node = mVexs[i].firstEdge;
        while (node != null) {
            if (!visited[node.ivex])
                DFS(node.ivex, visited);
            node = node.nextEdge;
        }
    }

    /*
     * 深度优先搜索遍历图
     */
    public void DFS() {
        boolean[] visited = new boolean[mVexs.length];       // 顶点访问标记

        // 初始化所有顶点都没有被访问
        for (int i = 0; i < mVexs.length; i++)
            visited[i] = false;

        System.out.printf("DFS: ");
        for (int i = 0; i < mVexs.length; i++) {
            if (!visited[i])
                DFS(i, visited);
        }
        System.out.printf("\n");
    }

    /*
     * 深度优先搜索遍历图，并打印所有的环
     */
    private void DFScircle(int i, ArrayList<Character> vnList) {
        ENode node;
        vnList.add(mVexs[i].data);
        node = mVexs[i].firstEdge;
        while (node != null) {
            if (vnList.indexOf(mVexs[node.ivex].data)==-1){
                DFScircle(node.ivex, vnList);
                vnList.remove(vnList.size()-1);
            }else {
                Character tempchar =  mVexs[node.ivex].data;
                int startint = vnList.indexOf(tempchar);
                int endint = vnList.indexOf(mVexs[i].data);
                if(startint < endint){
                    System.out.printf(" (存在环：");
                    while (startint <= endint){
                        System.out.printf("%c->",vnList.get(startint++));
                    }
                    System.out.printf("%c) %n",tempchar);
                }

            }

            node = node.nextEdge;
        }
    }
    public void DFScircle() {
        ArrayList<Character> vnList = new ArrayList<>();

        System.out.printf("DFScircle: ");
        /*for (int i = 0; i < mVexs.length; i++) {
            DFScircle(i, vnList);
        }*/
        DFScircle(0, vnList);
        System.out.printf("\n");
    }

    static ArrayList<Integer> trace=new ArrayList<Integer>();
    static boolean hasCycle=false;


    /*
     * 广度优先搜索（类似于树的层次遍历）
     */
    public void BFS() {
        int head = 0;
        int rear = 0;
        int[] queue = new int[mVexs.length];            // 辅组队列
        boolean[] visited = new boolean[mVexs.length];  // 顶点访问标记

        for (int i = 0; i < mVexs.length; i++)
            visited[i] = false;

        System.out.printf("BFS: ");
        for (int i = 0; i < mVexs.length; i++) {
            if (!visited[i]) {
                visited[i] = true;
                System.out.printf("%c ", mVexs[i].data);
                queue[rear++] = i;  // 入队列
            }

            while (head != rear) {
                int j = queue[head++];  // 出队列
                ENode node = mVexs[j].firstEdge;
                while (node != null) {
                    int k = node.ivex;
                    if (!visited[k])
                    {
                        visited[k] = true;
                        System.out.printf("%c ", mVexs[k].data);
                        queue[rear++] = k;
                    }
                    node = node.nextEdge;
                }
            }
        }
        System.out.printf("\n");
    }

    /*
     * 打印矩阵队列图
     */
    public void print() {
        System.out.printf("List Graph:\n");
        for (int i = 0; i < mVexs.length; i++) {
            System.out.printf("%d(%c): ", i, mVexs[i].data);
            ENode node = mVexs[i].firstEdge;
            while (node != null) {
                System.out.printf("%d(%c) ", node.ivex, mVexs[node.ivex].data);
                node = node.nextEdge;
            }
            System.out.printf("\n");
        }
    }

    /*
     * 获取边<start, end>的权值；若start和end不是连通的，则返回无穷大。
     */
    private int getWeight(int start, int end) {

        if (start==end)
            return 0;

        ENode node = mVexs[start].firstEdge;
        while (node!=null) {
            if (end==node.ivex)
                return node.weight;
            node = node.nextEdge;
        }

        return INF;
    }

    /*
     * prim最小生成树
     *
     * 参数说明：
     *   start -- 从图中的第start个元素开始，生成最小树
     */
    public void prim(int start) {
        int min,i,j,k,m,n,tmp,sum;
        int num = mVexs.length;
        int index=0;                   // prim最小树的索引，即prims数组的索引
        char[] prims = new char[num];  // prim最小树的结果数组
        int[] weights = new int[num];  // 顶点间边的权值

        // prim最小生成树中第一个数是"图中第start个顶点"，因为是从start开始的。
        prims[index++] = mVexs[start].data;

        // 初始化"顶点的权值数组"，
        // 将每个顶点的权值初始化为"第start个顶点"到"该顶点"的权值。
        for (i = 0; i < num; i++ )
            weights[i] = getWeight(start, i);

        for (i = 0; i < num; i++) {
            // 由于从start开始的，因此不需要再对第start个顶点进行处理。
            if(start == i)
                continue;

            j = 0;
            k = 0;
            min = INF;
            // 在未被加入到最小生成树的顶点中，找出权值最小的顶点。
            while (j < num) {
                // 若weights[j]=0，意味着"第j个节点已经被排序过"(或者说已经加入了最小生成树中)。
                if (weights[j] != 0 && weights[j] < min) {
                    min = weights[j];
                    k = j;
                }
                j++;
            }

            // 经过上面的处理后，在未被加入到最小生成树的顶点中，权值最小的顶点是第k个顶点。
            // 将第k个顶点加入到最小生成树的结果数组中
            prims[index++] = mVexs[k].data;
            // 将"第k个顶点的权值"标记为0，意味着第k个顶点已经排序过了(或者说已经加入了最小树结果中)。
            weights[k] = 0;
            // 当第k个顶点被加入到最小生成树的结果数组中之后，更新其它顶点的权值。
            for (j = 0 ; j < num; j++) {
                // 获取第k个顶点到第j个顶点的权值
                tmp = getWeight(k, j);
                // 当第j个节点没有被处理，并且需要更新时才被更新。
                if (weights[j] != 0 && tmp < weights[j])
                    weights[j] = tmp;
            }
        }

        // 计算最小生成树的权值
        sum = 0;
        for (i = 1; i < index; i++) {
            min = INF;
            // 获取prims[i]在邻接表中的位置
            n = getPosition(prims[i]);
            // 在vexs[0...i]中，找出到j的权值最小的顶点。
            for (j = 0; j < i; j++) {
                m = getPosition(prims[j]);
                tmp = getWeight(m, n);
                if (tmp < min)
                    min = tmp;
            }
            sum += min;
        }
        // 打印最小生成树
        System.out.printf("PRIM(%c)=%d: ", mVexs[start].data, sum);
        for (i = 0; i < index; i++)
            System.out.printf("%c ", prims[i]);
        System.out.printf("\n");
    }

    /*
     * 克鲁斯卡尔（Kruskal)最小生成树
     */
    public void kruskal() {
        int index = 0;                      // rets数组的索引
        int[] vends = new int[mEdgNum];     // 用于保存"已有最小生成树"中每个顶点在该最小树中的终点。
        EData[] rets = new EData[mEdgNum];  // 结果数组，保存kruskal最小生成树的边
        EData[] edges;                      // 图对应的所有边

        // 获取"图中所有的边"
        edges = getEdges();
        // 将边按照"权"的大小进行排序(从小到大)
        sortEdges(edges, mEdgNum);

        for (int i=0; i<mEdgNum; i++) {
            int p1 = getPosition(edges[i].start);      // 获取第i条边的"起点"的序号
            int p2 = getPosition(edges[i].end);        // 获取第i条边的"终点"的序号

            int m = getEnd(vends, p1);                 // 获取p1在"已有的最小生成树"中的终点
            int n = getEnd(vends, p2);                 // 获取p2在"已有的最小生成树"中的终点
            // 如果m!=n，意味着"边i"与"已经添加到最小生成树中的顶点"没有形成环路
            if (m != n) {
                vends[m] = n;                       // 设置m在"已有的最小生成树"中的终点为n
                rets[index++] = edges[i];           // 保存结果
            }
        }

        // 统计并打印"kruskal最小生成树"的信息
        int length = 0;
        for (int i = 0; i < index; i++)
            length += rets[i].weight;
        System.out.printf("Kruskal=%d: ", length);
        for (int i = 0; i < index; i++)
            System.out.printf("(%c,%c) ", rets[i].start, rets[i].end);
        System.out.printf("\n");
    }

    /*
     * 最短路径（Floyd)
     */
    public void Floyd() {
        //第一个 result，key为出发点，value是map，这个map的key是结束点，value是出发点到结束点的最短距离
        Map<VNode, HashMap<VNode, Integer>> result=new HashMap<>();
        //第二个 path，key为出发点，value是map，这个map的key是结束点，value是出发点到结束点的最短距离的路径的最后的中转节点
        Map<VNode, HashMap<VNode, VNode>> path=new HashMap<>();
        VNode vnode;
        ENode enode;

        for(VNode begin:mVexs){
            HashMap<VNode, Integer> distanceMap=new HashMap<>();
            HashMap<VNode, VNode> pathMap=new HashMap<>();
            for(VNode end:mVexs){
                //一开始，result里的value为maxDouble(到自己的value为0），path里的value是结束点
                distanceMap.put(end, INF);
                pathMap.put(end, begin);
            }
            //result里的value为maxDouble(到自己的value为0），path里的value是结束点
            distanceMap.put(begin, 0);
            result.put(begin, distanceMap);
            path.put(begin, pathMap);
        }

        for(VNode begin:mVexs){
            HashMap<VNode, Integer> distanceMap=result.get(begin);
            ENode edgeNode=begin.firstEdge;
            while(edgeNode!=null){
                //用图里的所有的边对result做初始化，当不经过任意第三节点时，其最短路径为初始路径，只对图里先有的只经过两点的边，对result里的value更新
                distanceMap.put(mVexs[edgeNode.ivex], edgeNode.weight);
                edgeNode = edgeNode.nextEdge;
            }
            result.put(begin, distanceMap);
        }

        for(VNode mid:mVexs){
            for(VNode begin:mVexs){
                HashMap<VNode, Integer> distanceMap=result.get(begin);
                HashMap<VNode, VNode> pathMap=path.get(begin);
                for(VNode end:mVexs){
                    Integer beginEnd=distanceMap.get(end);
                    Integer beginMid=distanceMap.get(mid);
                    Integer midEnd=result.get(mid).get(end);
                    if(beginMid!=INF && midEnd!=INF && beginMid + midEnd < beginEnd){
                        //如果通过中转点不行，或者通过中转点的距离大于原先距离，就不考虑这个中转点
                        distanceMap.put(end, beginMid + midEnd);
                        pathMap.put(end, mid);
                    }
                }
                result.put(begin, distanceMap);
                path.put(begin, pathMap);
            }
        }

        for(VNode begin:mVexs){
            HashMap<VNode, Integer> distanceMap = result.get(begin);
            HashMap<VNode, VNode> pathMap=path.get(begin);
            for(VNode end:mVexs){
                System.out.println("从顶点:"+begin.data+" ，到顶点："+end.data+
                        " ，最短距离为："+distanceMap.get(end)+" ，最后中转点为:"+pathMap.get(end).data);
            }
        }

        VNode vn1 = mVexs[1];
        VNode vn2 = mVexs[6];
        VNode tempvn = vn2;
        Stack<VNode> stack = new Stack<>();
        while (tempvn != vn1){
            tempvn = path.get(vn1).get(tempvn);
            stack.push(tempvn);
        }
        System.out.print(vn1.data+"->" + vn2.data + " 路径节点为：");
        while (!stack.isEmpty()){
            System.out.printf("%c -> ",stack.pop().data);
        }
        System.out.printf("%c%n",vn2.data );
    }

    /*
     * 最短路径（Dijkstra)
     */
    public void Dijkstra(){
        int first = 0;
        Set<VNode> open = new HashSet<>();
        Set<VNode> close = new HashSet<>();
        HashMap<VNode, VNode> path=new HashMap<>();
        HashMap<VNode, Integer> distance = new HashMap<>();
        VNode startVertex = mVexs[0];
        if(startVertex == null){
            System.out.println("开始节点为空");
        }
        //初始阶段，将所有节点放入open,distance里面先初始为doubleMax，Path先初始为vertex自己
        for(VNode vertex:mVexs){
            open.add(vertex);
            distance.put(vertex, INF);
            path.put(vertex, vertex);
        }
        //将起始点放入close，设置distance=0，path=自己，更新起始点周围的节点的距离，设置他们的distance=边的距离，path=起始点
        open.remove(startVertex);
        close.add(startVertex);
        distance.put(startVertex, 0);
        path.put(startVertex, startVertex);
        ENode edge = startVertex.firstEdge;
        while(edge != null){
            distance.put(mVexs[edge.ivex], edge.weight);
            path.put(mVexs[edge.ivex], startVertex);
            edge = edge.nextEdge;
        }
        //以初始节点为中心向外一层层遍历，获取离指定节点最近的子节点（遍历open中的vertex,找到distance最小的vertex）
        //放入close并从新计算路径，直至close包含所有子节点（或者说open为空）
        while(!open.isEmpty()){
            VNode minVertex = null;
            int minDistance = INF;
            for(VNode vertex:open){
                if(distance.get(vertex) < minDistance){
                    //遍历open中的vertex,找到distance最小的vertex
                    minDistance=distance.get(vertex);
                    minVertex=vertex;
                }
            }
            //放入close并从新计算路径，直至close包含所有子节点（或者说open为空）
            open.remove(minVertex);
            close.add(minVertex);
            //System.out.println("加入节点："+minVertex.getLabel());
            edge  = minVertex.firstEdge;
            while(edge != null){
                VNode endVertex = mVexs[edge.ivex];
                //如果之前的距离大于 初始到minVertex+minVertex到endVertex，就替换
                if(distance.get(endVertex) > distance.get(minVertex) + edge.weight){
                    distance.put(endVertex, distance.get(minVertex) + edge.weight);
                    path.put(endVertex, minVertex);
                }
                edge = edge.nextEdge;
            }
        }

        for(VNode vertex:mVexs){
            System.out.println(startVertex.data + "到顶点："+vertex.data+
                    " ，最短距离为："+distance.get(vertex)+" ，最后中转点为:"+path.get(vertex).data);
        }

        VNode vn2 = mVexs[3];
        VNode tempvn = vn2;
        Stack<VNode> stack = new Stack<>();
        while (tempvn != startVertex){
            tempvn = path.get(tempvn);
            stack.push(tempvn);
        }
        System.out.print(startVertex.data+"->" + vn2.data + " 路径节点为：");
        while (!stack.isEmpty()){
            System.out.printf("%c -> ",stack.pop().data);
        }
        System.out.printf("%c%n",vn2.data );
    }

    /*
     * 拓扑排序（topology)
     */
    public void topology() {
        int[] intIn = new int[mVexs.length];
        for (VNode vex : mVexs) {// 遍历所有的点，获取顶点的入度
            ENode enode = vex.firstEdge;
            while (enode!=null){
                intIn[enode.ivex]++;
                enode = enode.nextEdge;
            }
        }
        System.out.printf("各顶点入度依次为：");
        for(int i : intIn)
            System.out.printf("%d ",i);
        System.out.println();
        Queue<VNode> zeroInQueue = new LinkedList<>();
        int i = 0;
        for (VNode vex : mVexs) {// 遍历所有的点
            if (intIn[i++] == 0) {
                zeroInQueue.add(vex);// 当前入度为0的节点加入队列
            }
        }
        List<VNode> result = new ArrayList<>();
        System.out.printf("拓扑排序顺序为: ");
        while (!zeroInQueue.isEmpty()) {// 入度为0的节点从队列弹出并且把加入list，相当于从图中去掉，所以还要把其邻接节点的入度减1
            VNode cur = zeroInQueue.poll();
            System.out.printf("%c ", cur.data);
            result.add(cur);// 存的就是队列弹出的节点，就是拓扑排序的顺序
            ENode enode = cur.firstEdge;
            while (enode != null){
                intIn[enode.ivex]--;
                if (intIn[enode.ivex] == 0) {
                    zeroInQueue.add(mVexs[enode.ivex]);
                }
                enode = enode.nextEdge;
            }
        }
        System.out.printf("%n");
        if(result.size()!=mVexs.length){
            System.out.println("此图不为无环有向图，拓扑排序未成功！");
        }
    }

    /*
     * 求关键路径（CriticalPath)
     */
    /**
     *     事件的最早发生时间etv：即顶点v_kvk​的最早发生时间
     *     事件的最晚发生时间ltv：即顶点v_kvk​的最晚发生时间，也就是每个顶点对应的事件最晚需要开始的时间，超过此时间将会延误整个工期。
     *     活动的最早开工时间ete：即弧a_kak​的最早发生时间
     *     活动的最晚开工时间lte：即弧a_kak​的最晚发生时间，也就是不推迟工期的最晚开工时间。
     */
    Stack<Integer> stack = new Stack<>();
    Stack<Integer> stack2 = new Stack<>();
    public int[] ToplogicalSort(int[] etv) {
        ENode e;
        int k, gettop;
        int count = 0;
        for (VNode vex : mVexs) {// 遍历所有的点，获取顶点的入度
            for(ENode enode = vex.firstEdge;enode !=null;enode=enode.nextEdge)
                mVexs[enode.ivex].in++;
        }

        etv = new int[mVexs.length];
        for (int i = 0; i < mVexs.length; i++) {
            if(mVexs[i].in == 0) {
                stack.push(i);
            }
        }
        for (int i = 0; i < mVexs.length; i++) {
            etv[i] = 0;
        }

        while(!stack.isEmpty()) {
            gettop = (int) stack.pop();
            count++;
            stack2.push(gettop);
            for (e = mVexs[gettop].firstEdge; e != null; e = e.nextEdge) {
                k = e.ivex;
                if((--mVexs[k].in) == 0) {
                    stack.push(k);
                }
                if(etv[gettop] + e.weight > etv[k]) {
                    etv[k] = etv[gettop] + e.weight;
                }
            }
        }
        if(count < mVexs.length)
            return null;
        else return etv;
    }
    public void CriticalPath() {
        ENode e;
        int gettop, k, j;
        int ete, lte;
        int[] etv = new int[mVexs.length];
        int[] ltv = new int[mVexs.length];
        if((etv=ToplogicalSort(etv))==null) {
            System.out.println("该网中存在回路!");
            return;
        }
        for (int i = 0; i < mVexs.length; i++) {
            ltv[i] = etv[etv.length - 1];
        }

        while(!stack2.isEmpty()) {
            gettop = (int) stack2.pop();
            for(e = mVexs[gettop].firstEdge; e != null; e = e.nextEdge) {
                k = e.ivex;
                if(ltv[k] - e.weight < ltv[gettop]) {
                    ltv[gettop] = ltv[k] - e.weight;
                }
            }
        }
        int count = 0;
        for (int i = 0; i < mVexs.length; i++) {
            for(e = mVexs[i].firstEdge; e != null; e = e.nextEdge) {
                k = e.ivex;
                ete = etv[i];
                lte = ltv[k] - e.weight;
                if(ete == lte) {
                    System.out.print("<" + mVexs[i].data + "," + mVexs[k].data + ">length:" + e.weight + ",  ");
                    count+=e.weight;
                }
            }
        }
        System.out.println("长度为："+count);
    }

    /*
     * 获取图中的边
     */
    private EData[] getEdges() {
        int index=0;
        EData[] edges;

        edges = new EData[mEdgNum];
        for (int i=0; i < mVexs.length; i++) {

            ENode node = mVexs[i].firstEdge;
            while (node != null) {
                if (node.ivex > i) {
                    edges[index++] = new EData(mVexs[i].data, mVexs[node.ivex].data, node.weight);
                }
                node = node.nextEdge;
            }
        }

        return edges;
    }

    /*
     * 对边按照权值大小进行排序(由小到大)
     */
    private void sortEdges(EData[] edges, int elen) {

        for (int i=0; i<elen; i++) {
            for (int j=i+1; j<elen; j++) {

                if (edges[i].weight > edges[j].weight) {
                    // 交换"边i"和"边j"
                    EData tmp = edges[i];
                    edges[i] = edges[j];
                    edges[j] = tmp;
                }
            }
        }
    }

    /*
     * 获取i的终点
     */
    private int getEnd(int[] vends, int i) {
        while (vends[i] != 0)
            i = vends[i];
        return i;
    }

    // 边的结构体
    private static class EData {
        char start; // 边的起点
        char end;   // 边的终点
        int weight; // 边的权重

        public EData(char start, char end, int weight) {
            this.start = start;
            this.end = end;
            this.weight = weight;
        }
    }

    public static void main(String[] args) {
        char[] vexs = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        EData[] edges = {
                // 起点 终点 权
                new EData('A', 'B', 12),
                new EData('A', 'F', 16),
                new EData('A', 'G', 14),
                new EData('B', 'C', 10),
                new EData('B', 'F',  7),
                new EData('C', 'D',  3),
                new EData('C', 'E',  5),
                new EData('C', 'F',  6),
                new EData('D', 'E',  4),
                new EData('E', 'F',  2),
                new EData('E', 'G',  8),
                new EData('F', 'G',  9),

                // 形成环路
                /*new EData('F', 'A',  9),
                new EData('E', 'C',  9),
                new EData('F', 'B',  9),*/
        };
        ListUDG pG;

        // 自定义"图"(输入矩阵队列)
        //pG = new ListUDG();
        // 采用已有的"图"
        pG = new ListUDG(vexs, edges);

        pG.print();   // 打印图
        //pG.topology();  // 拓扑排序
        //pG.DFS();     // 深度优先遍历
        //pG.DFScircle();   //深度优先打印环
        //pG.BFS();     // 广度优先遍历

        //pG.prim(0);   // prim算法生成最小生成树
        //pG.kruskal(); // Kruskal算法生成最小生成树

        //pG.Floyd();    // Floyd求最短路径
        //pG.Dijkstra();    // Dijkstra求最短路径
        pG.CriticalPath();  // CriticalPath 求AOE关键路径
    }
}
