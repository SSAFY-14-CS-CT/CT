import java.io.*;
import java.util.*;

public class Solution {
    static int N, M;
    static int[] parent, rank; // rank는 union-by-rank 최적화용

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder out = new StringBuilder();
        int T = Integer.parseInt(br.readLine().trim());

        for (int tc = 1; tc <= T; tc++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());

            initDSU(N);

            for (int i = 0; i < M; i++) {
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                union(a, b);
            }

            // 대표자(루트) 개수 카운트
            int groups = 0;
            boolean[] seen = new boolean[N + 1];
            for (int i = 1; i <= N; i++) {
                int root = find(i);
                if (!seen[root]) {
                    seen[root] = true;
                    groups++;
                }
            }

            out.append('#').append(tc).append(' ').append(groups).append('\n');
        }

        System.out.print(out.toString());
    }

    // ----- DSU -----
    static void initDSU(int n) {
        parent = new int[n + 1];
        rank = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            parent[i] = i; // 자기 자신이 부모
            rank[i] = 0;
        }
    }

    static int find(int x) {
        if (parent[x] != x) parent[x] = find(parent[x]); // path compression
        return parent[x];
    }

    static void union(int a, int b) {
        int ra = find(a), rb = find(b);
        if (ra == rb) return;
        // union by rank
        if (rank[ra] < rank[rb]) parent[ra] = rb;
        else if (rank[ra] > rank[rb]) parent[rb] = ra;
        else {
            parent[rb] = ra;
            rank[ra]++;
        }
    }
}