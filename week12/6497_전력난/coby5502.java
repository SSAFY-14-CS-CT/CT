import java.io.*;
import java.util.*;

public class Main {
    static int[] parent;
    static int[] rank; // 경로 압축용 랭크

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        while (true) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int m = Integer.parseInt(st.nextToken()); // 집 수(정점)
            int n = Integer.parseInt(st.nextToken()); // 길 수(간선)
            if (m == 0 && n == 0) break;

            List<int[]> edges = new ArrayList<>();
            long total = 0;

            for (int i = 0; i < n; i++) {
                st = new StringTokenizer(br.readLine());
                int u = Integer.parseInt(st.nextToken());
                int v = Integer.parseInt(st.nextToken());
                int w = Integer.parseInt(st.nextToken());
                edges.add(new int[]{u, v, w});
                total += w;
            }

            // 정렬
            edges.sort(Comparator.comparingInt(a -> a[2]));

            // 유니온파인드 초기화
            parent = new int[m];
            rank = new int[m];
            for (int i = 0; i < m; i++) parent[i] = i;

            long mst = 0;
            int count = 0;

            for (int[] e : edges) {
                int u = e[0], v = e[1], w = e[2];
                if (union(u, v)) {
                    mst += w;
                    count++;
                    if (count == m - 1) break;
                }
            }

            sb.append(total - mst).append('\n');
        }

        System.out.print(sb);
    }

    // find 함수 (경로 압축)
    static int find(int x) {
        if (parent[x] == x) return x;
        return parent[x] = find(parent[x]);
    }

    // union 함수
    static boolean union(int a, int b) {
        int ra = find(a);
        int rb = find(b);
        if (ra == rb) return false;

        if (rank[ra] < rank[rb]) parent[ra] = rb;
        else if (rank[ra] > rank[rb]) parent[rb] = ra;
        else {
            parent[rb] = ra;
            rank[ra]++;
        }
        return true;
    }
}