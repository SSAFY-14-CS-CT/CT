import java.io.*;
import java.util.*;

public class Main {
    static int N, M, K;
    static final int[] dx = {-1, 1, 0, 0};
    static final int[] dy = {0, 0, -1, 1};
    static boolean[][] pass;   // true면 통과 가능('.')
    static int[][] dist;       // 최소 이동 횟수, 미방문은 -1
    static int sx, sy, ex, ey;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        pass = new boolean[N][M];
        dist = new int[N][M];
        for (int i = 0; i < N; i++) {
            Arrays.fill(dist[i], -1);
        }

        for (int i = 0; i < N; i++) {
            String s = br.readLine();
            for (int j = 0; j < M; j++) {
                pass[i][j] = (s.charAt(j) == '.');
            }
        }

        st = new StringTokenizer(br.readLine());
        sx = Integer.parseInt(st.nextToken()) - 1;
        sy = Integer.parseInt(st.nextToken()) - 1;
        ex = Integer.parseInt(st.nextToken()) - 1;
        ey = Integer.parseInt(st.nextToken()) - 1;

        System.out.println(bfs());
    }

    static int bfs() {
        ArrayDeque<int[]> q = new ArrayDeque<>();
        dist[sx][sy] = 0;
        q.offer(new int[]{sx, sy});

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int cx = cur[0], cy = cur[1];

            if (cx == ex && cy == ey) return dist[cx][cy];

            for (int dir = 0; dir < 4; dir++) {
                for (int step = 1; step <= K; step++) {
                    int nx = cx + dx[dir] * step;
                    int ny = cy + dy[dir] * step;

                    // 격자 밖이거나 벽이면 이 방향 더 못 감
                    if (!in(nx, ny) || !pass[nx][ny]) break;

                    // 이미 더 짧은(혹은 같은) 비용으로 도달 가능한 상황 처리
                    if (dist[nx][ny] != -1) {
                        // 이미 현재보다 "짧은" 거리로 방문했다면, 이 방향은 더 가도 의미 없음
                        if (dist[nx][ny] < dist[cx][cy] + 1) break;
                        // 같은 레벨에서 이미 방문했다면, 큐에는 안 넣고 다음 step으로 진행은 가능
                        if (dist[nx][ny] == dist[cx][cy] + 1) continue;
                    }

                    // 첫 방문
                    dist[nx][ny] = dist[cx][cy] + 1;
                    q.offer(new int[]{nx, ny});
                }
            }
        }
        return -1;
    }

    static boolean in(int x, int y) {
        return 0 <= x && x < N && 0 <= y && y < M;
    }
}