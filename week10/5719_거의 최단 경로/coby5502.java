import java.io.*;
import java.util.*;

/**
 * BOJ 5719 - 거의 최단 경로
 *
 * 풀이:
 * 1) S에서 1차 다익스트라 실행: dist[], prev[v] (v로 오는 모든 최단 '직전 정점들') 기록
 * 2) D에서 prev를 타고 거꾸로 올라가며 "최단 경로에 포함되는 모든 간선(u->v)" 제거 표시
 * 3) 간선을 무시한 상태로 2차 다익스트라 실행 → S->D 거리 출력(없으면 -1)
 */
public class Main {

    static class Edge {
        int to;
        int w;
        Edge(int to, int w) { this.to = to; this.w = w; }
    }

    static class Node implements Comparable<Node> {
        int v; int d;
        Node(int v, int d) { this.v = v; this.d = d; }
        public int compareTo(Node o) { return Integer.compare(this.d, o.d); }
    }

    static int N, M;
    static int S, D;
    static List<Edge>[] g;          // 원본 그래프
    static List<Integer>[] prev;    // prev[v]: v로 최단으로 오기 직전의 모든 정점 u
    static int[] dist;
    static boolean[][] removed;     // removed[u][v] = true면 간선 u->v 사용 금지
    static final int INF = 1_000_000_000;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String line;
        while (true) {
            line = br.readLine();
            if (line == null || line.isEmpty()) return;
            StringTokenizer st = new StringTokenizer(line);
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            if (N == 0 && M == 0) break;

            st = new StringTokenizer(br.readLine());
            S = Integer.parseInt(st.nextToken());
            D = Integer.parseInt(st.nextToken());

            g = new ArrayList[N];
            for (int i = 0; i < N; i++) g[i] = new ArrayList<>();

            for (int i = 0; i < M; i++) {
                st = new StringTokenizer(br.readLine());
                int u = Integer.parseInt(st.nextToken());
                int v = Integer.parseInt(st.nextToken());
                int w = Integer.parseInt(st.nextToken());
                g[u].add(new Edge(v, w));
            }

            // 1차 다익스트라
            prev = new ArrayList[N];
            for (int i = 0; i < N; i++) prev[i] = new ArrayList<>();
            removed = new boolean[N][N]; // 간선 제거 표시 초기화

            dist = dijkstra(S, true);    // prev 채우기 모드

            // 최단 경로 없으면 바로 -1
            if (dist[D] == INF) {
                System.out.println(-1);
                continue;
            }

            // 최단 경로 간선들 제거
            removeShortestEdges(D);

            // 2차 다익스트라 (제거된 간선 무시)
            dist = dijkstra(S, false);

            System.out.println(dist[D] == INF ? -1 : dist[D]);
        }
    }

    /**
     * 다익스트라 실행
     * @param start 시작 정점
     * @param fillPrev true면 prev를 채움(1차), false면 간선 제거만 반영(2차)
     * @return dist[] 배열
     */
    static int[] dijkstra(int start, boolean fillPrev) {
        int[] d = new int[N];
        Arrays.fill(d, INF);
        PriorityQueue<Node> pq = new PriorityQueue<>();
        d[start] = 0;
        pq.add(new Node(start, 0));

        while (!pq.isEmpty()) {
            Node cur = pq.poll();
            if (cur.d != d[cur.v]) continue;

            for (Edge e : g[cur.v]) {
                int nv = e.to;
                int nd = cur.d + e.w;
                if (removed[cur.v][nv]) continue; // 제거된 간선은 무시

                if (nd < d[nv]) {
                    d[nv] = nd;
                    pq.add(new Node(nv, nd));
                    if (fillPrev) {
                        // 더 짧은 경로 발견 → prev 갱신(기존 후보들 폐기)
                        prev[nv].clear();
                        prev[nv].add(cur.v);
                    }
                } else if (fillPrev && nd == d[nv]) {
                    // 같은 길이의 최단 경로 또 발견 → prev 후보 추가
                    prev[nv].add(cur.v);
                }
            }
        }
        return d;
    }

    /**
     * prev를 이용해 D에서 거꾸로 올라가며
     * 최단 경로에 포함된 모든 간선(u->v)을 removed[u][v]=true로 표시
     */
    static void removeShortestEdges(int dest) {
        Queue<Integer> q = new ArrayDeque<>();
        boolean[] visitedBack = new boolean[N];
        q.add(dest);
        visitedBack[dest] = true;

        while (!q.isEmpty()) {
            int v = q.poll();        // 현재 노드 v
            for (int u : prev[v]) {  // v로 최단으로 올 수 있는 모든 직전 정점 u
                if (!removed[u][v]) {
                    removed[u][v] = true; // 간선 u->v 제거
                    if (!visitedBack[u]) {
                        visitedBack[u] = true;
                        q.add(u);
                    }
                }
            }
        }
    }
}
