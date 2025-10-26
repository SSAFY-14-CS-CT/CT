import java.io.*;
import java.util.*;

public class Main {
    static class Edge {
        int to;
        int fee; // 이 골목에서 내야 하는 수금액

        Edge(int to, int fee) {
            this.to = to;
            this.fee = fee;
        }
    }

    static int N, M, A, B;
    static long C; // 가진 돈(총 합 제한) — 최대 1e14
    static List<Edge>[] g;
    static int maxFee = 0; // 간선 fee 최댓값(이분 탐색 상한)

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        A = Integer.parseInt(st.nextToken());
        B = Integer.parseInt(st.nextToken());
        C = Long.parseLong(st.nextToken());

        g = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++)
            g[i] = new ArrayList<>();

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int fee = Integer.parseInt(st.nextToken());
            g[u].add(new Edge(v, fee));
            g[v].add(new Edge(u, fee));
            if (fee > maxFee)
                maxFee = fee;
        }

        // 이분 탐색: “허용 가능한 최대 간선요금(mid)”의 최솟값
        int lo = 0, hi = maxFee;
        int answer = -1;

        // 먼저 hi(=maxFee)로도 불가능하면 -1
        if (!canReachWithinBudget(maxFee)) {
            System.out.println(-1);
            return;
        }

        while (lo <= hi) {
            int mid = (lo + hi) >>> 1;
            if (canReachWithinBudget(mid)) {
                answer = mid; // 가능 → 더 작은 최대요금 시도
                hi = mid - 1;
            } else {
                lo = mid + 1; // 불가능 → 최대요금 제한을 늘림
            }
        }
        System.out.println(answer);
    }

    /**
     * 최대 간선요금 제한 cap 이하의 간선만 사용해서
     * A -> B 최소 총비용(수금 합)을 다익스트라로 구해보고, C 이하인지 판정
     */
    static boolean canReachWithinBudget(int cap) {
        final long INF = Long.MAX_VALUE / 4;
        long[] dist = new long[N + 1];
        Arrays.fill(dist, INF);
        dist[A] = 0;

        PriorityQueue<long[]> pq = new PriorityQueue<>(Comparator.comparingLong(o -> o[0]));
        // {누적비용, 정점}
        pq.offer(new long[] { 0L, A });

        while (!pq.isEmpty()) {
            long[] cur = pq.poll();
            long cost = cur[0];
            int u = (int) cur[1];

            if (cost != dist[u])
                continue;
            if (cost > C)
                continue; // 이미 가진 돈 초과 경로는 가망 없음
            if (u == B)
                break; // 최단 경로가 확정되었으니 종료 가능

            for (Edge e : g[u]) {
                if (e.fee > cap)
                    continue; // 최대 간선요금 제한 위반 → 사용 불가
                long nc = cost + e.fee;
                if (nc < dist[e.to] && nc <= C) {
                    dist[e.to] = nc;
                    pq.offer(new long[] { nc, e.to });
                }
            }
        }
        return dist[B] <= C;
    }
}