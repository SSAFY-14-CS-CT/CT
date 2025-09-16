import java.io.*;
import java.util.*;

public class Main {
    static class Edge {
        int to, w;

        Edge(int to, int w) {
            this.to = to;
            this.w = w;
        }
    }

    static class State implements Comparable<State> {
        int v;
        long cost;

        State(int v, long cost) {
            this.v = v;
            this.cost = cost;
        }

        public int compareTo(State o) {
            return Long.compare(this.cost, o.cost);
        }
    }

    static int n, m, k;
    static List<Edge>[] g;

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        g = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++)
            g[i] = new ArrayList<>();

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            g[a].add(new Edge(b, c));
        }

        PriorityQueue<Long>[] topK = kShortest(1);

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            if (topK[i].size() == k)
                sb.append(topK[i].peek()).append('\n');
            else
                sb.append(-1).append('\n');
        }
        System.out.print(sb);
    }

    static PriorityQueue<Long>[] kShortest(int start) {
        @SuppressWarnings("unchecked")
        PriorityQueue<Long>[] topK = new PriorityQueue[n + 1];
        for (int i = 1; i <= n; i++)
            topK[i] = new PriorityQueue<>(Collections.reverseOrder());

        PriorityQueue<State> pq = new PriorityQueue<>();
        topK[start].add(0L);
        pq.add(new State(start, 0L));

        while (!pq.isEmpty()) {
            State cur = pq.poll();
            if (topK[cur.v].size() == k && topK[cur.v].peek() < cur.cost)
                continue;

            for (Edge e : g[cur.v]) {
                long nc = cur.cost + e.w;
                if (topK[e.to].size() < k) {
                    topK[e.to].add(nc);
                    pq.add(new State(e.to, nc));
                } else if (topK[e.to].peek() > nc) {
                    topK[e.to].poll();
                    topK[e.to].add(nc);
                    pq.add(new State(e.to, nc));
                }
            }
        }
        return topK;
    }
}