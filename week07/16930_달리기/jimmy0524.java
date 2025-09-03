import java.util.*;
import java.io.*;

public class Main {
    static int[] dx = { 1, -1, 0, 0 };
    static int[] dy = { 0, 0, 1, -1 };
    static char[][] arr;
    static int[][] visit;
    static int result = -1;
    static int N, M, K;
    static int sx, sy, ex, ey;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        arr = new char[N + 1][M + 1];
        visit = new int[N + 1][M + 1];

        for (int i = 1; i <= N; i++) {
            String s = br.readLine();
            for (int j = 1; j <= M; j++) {
                arr[i][j] = s.charAt(j - 1);
            }
        }

        st = new StringTokenizer(br.readLine());
        sx = Integer.parseInt(st.nextToken());
        sy = Integer.parseInt(st.nextToken());
        ex = Integer.parseInt(st.nextToken());
        ey = Integer.parseInt(st.nextToken());

        bfs();

        System.out.println(result);
    }

    public static void bfs() {
        Queue<int[]> q = new ArrayDeque<>();
        q.add(new int[] { sx, sy, 1 });
        visit[sx][sy] = 1;

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int x = cur[0], y = cur[1], move = cur[2];

            for (int d = 0; d < 4; d++) {
                for (int step = 1; step <= K; step++) {
                    int nx = x + dx[d] * step;
                    int ny = y + dy[d] * step;

                    if (nx < 1 || ny < 1 || nx > N || ny > M || arr[nx][ny] == '#')
                        break;

                    if (nx == ex && ny == ey) {
                        result = move;
                        return;
                    }

                    if (visit[nx][ny] == 0) {
                        visit[nx][ny] = move + 1;
                        q.add(new int[] { nx, ny, move + 1 });
                    } else if (visit[nx][ny] == move + 1)
                        continue;
                    else
                        break;
                }
            }
        }
    }
}
