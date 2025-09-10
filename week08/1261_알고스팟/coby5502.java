import java.io.*;
import java.util.*;

public class Main {
    static final int INF = 1 << 30;
    static int N, M;
    static int[][] map, dist;
    static final int[] dx = { -1, 1, 0, 0 };
    static final int[] dy = { 0, 0, -1, 1 };

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        M = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());

        map = new int[N][M];
        for (int i = 0; i < N; i++) {
            String s = br.readLine();
            for (int j = 0; j < M; j++) {
                map[i][j] = s.charAt(j) - '0';
            }
        }

        dist = new int[N][M];
        for (int i = 0; i < N; i++) {
            Arrays.fill(dist[i], INF);
        }

        ArrayDeque<int[]> dq = new ArrayDeque<>();
        dist[0][0] = 0;
        dq.addFirst(new int[] { 0, 0 });

        while (!dq.isEmpty()) {
            int[] cur = dq.pollFirst();
            int x = cur[0], y = cur[1];

            for (int d = 0; d < 4; d++) {
                int nx = x + dx[d], ny = y + dy[d];
                if (nx < 0 || ny < 0 || nx >= N || ny >= M)
                    continue;

                int w = map[nx][ny]; // 0: 빈방, 1: 벽
                if (dist[nx][ny] > dist[x][y] + w) {
                    dist[nx][ny] = dist[x][y] + w;
                    if (w == 0)
                        dq.addFirst(new int[] { nx, ny });
                    else
                        dq.addLast(new int[] { nx, ny });
                }
            }
        }

        System.out.println(dist[N - 1][M - 1]);
    }
}