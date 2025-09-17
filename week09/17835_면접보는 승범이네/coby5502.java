import java.io.*;
import java.util.*;

public class Main {

    static class Edge {
        int to;
        int w;

        Edge(int to, int w) {
            this.to = to;
            this.w = w;
        }
    }

    static class Node implements Comparable<Node> {
        int v;
        long d;

        Node(int v, long d) {
            this.v = v;
            this.d = d;
        }

        public int compareTo(Node o) {
            return Long.compare(this.d, o.d);
        }
    }

    static int N, M, K;
    static List<Edge>[] rev; // 역방향 그래프
    static long[] dist;
    static final long INF = Long.MAX_VALUE / 4;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        rev = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++) {
            rev[i] = new ArrayList<>();
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            rev[v].add(new Edge(u, c));
        }

        int[] interviews = new int[K];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < K; i++) {
            interviews[i] = Integer.parseInt(st.nextToken());
        }

        // 다중 시작점 다익스트라
        dist = new long[N + 1];
        Arrays.fill(dist, INF);
        PriorityQueue<Node> pq = new PriorityQueue<>();

        for (int s : interviews) {
            dist[s] = 0;
            pq.add(new Node(s, 0));
        }

        while (!pq.isEmpty()) {
            Node cur = pq.poll();
            if (cur.d != dist[cur.v])
                continue; // outdated
            for (Edge e : rev[cur.v]) {
                long nd = cur.d + e.w;
                if (nd < dist[e.to]) {
                    dist[e.to] = nd;
                    pq.add(new Node(e.to, nd));
                }
            }
        }

        // 가장 먼 도시(거리 최댓값), 거리 같으면 번호가 작은 도시
        int ansCity = 1;
        long ansDist = dist[1];
        for (int i = 2; i <= N; i++) {
            if (dist[i] > ansDist || (dist[i] == ansDist && i < ansCity)) {
                ansCity = i;
                ansDist = dist[i];
            }
        }

        System.out.println(ansCity);
        System.out.println(ansDist);
    }
}