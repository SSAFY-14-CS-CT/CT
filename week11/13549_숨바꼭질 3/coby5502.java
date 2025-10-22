import java.io.*;
import java.util.*;

public class Main {
    static final int MAX = 100000;
    static final int INF = 1_000_000_000;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        if (N >= K) {
            System.out.println(N - K);
        } else {
            System.out.println(bfs(N, K));
        }
    }

    static int bfs(int start, int target) {
        int[] dist = new int[MAX + 1];
        Arrays.fill(dist, INF);
        Deque<Integer> dq = new ArrayDeque<>();

        dist[start] = 0;
        dq.add(start);

        while (!dq.isEmpty()) {
            int x = dq.pollFirst();
            int cur = dist[x];

            if (x == target)
                return cur;

            // 0초 이동: 순간이동 (x → 2x)
            if (x * 2 <= MAX && dist[x * 2] > cur) {
                dist[x * 2] = cur;
                dq.addFirst(x * 2);
            }

            // 1초 이동: 걷기 (x → x-1, x+1)
            if (x - 1 >= 0 && dist[x - 1] > cur + 1) {
                dist[x - 1] = cur + 1;
                dq.addLast(x - 1);
            }
            if (x + 1 <= MAX && dist[x + 1] > cur + 1) {
                dist[x + 1] = cur + 1;
                dq.addLast(x + 1);
            }
        }

        return dist[target];
    }
}