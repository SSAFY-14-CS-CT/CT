import java.io.*;
import java.util.*;

public class Main {

    static int V, E, K;
    static final int INF = Integer.MAX_VALUE;
    static List<Node>[] graph;
    static int[] dist;

    static class Node implements Comparable<Node> {
        int index;
        int cost;

        public Node(int index, int cost) {
            this.index = index;
            this.cost = cost;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.cost, other.cost);
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder out = new StringBuilder();

        st = new StringTokenizer(br.readLine());
        V = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(br.readLine());
        graph = new ArrayList[V + 1];
        dist = new int[V + 1];

        for (int i = 0; i <= V; i++) {
            graph[i] = new ArrayList<>();
            dist[i] = INF;
        }

        for (int i = 0; i < E; i++) {
            int u, v, w;
            st = new StringTokenizer(br.readLine());
            u = Integer.parseInt(st.nextToken());
            v = Integer.parseInt(st.nextToken());
            w = Integer.parseInt(st.nextToken());
            graph[u].add(new Node(v, w));
        }

        dijkstra(K);

        for (int i = 1; i <= V; i++) {
            out.append(dist[i] == INF ? "INF" : dist[i]).append("\n");
        }

        System.out.print(out.toString());
    }

    public static void dijkstra(int start) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.offer(new Node(start, 0));
        dist[start] = 0;

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            int curIndex = current.index;
            int curCost = current.cost;

            if (dist[curIndex] < curCost)
                continue;

            for (Node next : graph[curIndex]) {
                int cost = dist[curIndex] + next.cost;
                if (cost < dist[next.index]) {
                    dist[next.index] = cost;
                    pq.offer(new Node(next.index, cost));
                }
            }
        }
    }
}
