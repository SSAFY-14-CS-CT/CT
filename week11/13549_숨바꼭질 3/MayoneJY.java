
import java.io.*;
import java.util.*;

public class MayoneJY {
    static final int MAX = 100000;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        if (N >= K) {
            System.out.println(N - K);
            return;
        }

        int[] dist = new int[MAX + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        Deque<Integer> dq = new ArrayDeque<>();

        dist[N] = 0;
        dq.add(N);
        
        while (!dq.isEmpty()) {
            int x = dq.poll();
            if (x == K) break;

            int nx = x * 2;
            if (nx <= MAX && dist[nx] > dist[x]) {
                dist[nx] = dist[x];
                dq.addFirst(nx);
            }

            nx = x - 1;
            if (nx >= 0 && dist[nx] > dist[x] + 1) {
                dist[nx] = dist[x] + 1;
                dq.addLast(nx);
            }

            nx = x + 1;
            if (nx <= MAX && dist[nx] > dist[x] + 1) {
                dist[nx] = dist[x] + 1;
                dq.addLast(nx);
            }
        }
        System.out.println(dist[K]);
    }
}
