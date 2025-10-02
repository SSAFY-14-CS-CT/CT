import java.io.*;
import java.util.*;

public class Main {
    static int N, L, R;
    static int[][] A;
    static boolean[][] visited;
    static final int[] dx = {1, -1, 0, 0};
    static final int[] dy = {0, 0, 1, -1};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        L = Integer.parseInt(st.nextToken());
        R = Integer.parseInt(st.nextToken());

        A = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                A[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int days = 0;

        while (true) {
            visited = new boolean[N][N];
            boolean moved = false;

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (!visited[i][j]) {
                        List<int[]> union = new ArrayList<>();
                        int sum = bfs(i, j, union);

                        if (union.size() > 1) {
                            moved = true;
                            int avg = sum / union.size();

                            for (int[] p : union) {
                                A[p[0]][p[1]] = avg;
                            }
                        }
                    }
                }
            }

            if (!moved) break;
            days++;
        }

        System.out.println(days);
    }

    static int bfs(int sx, int sy, List<int[]> union) {
        Queue<int[]> q = new ArrayDeque<>();
        q.add(new int[]{sx, sy});
        visited[sx][sy] = true;
        union.add(new int[]{sx, sy});
        int sum = A[sx][sy];

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int x = cur[0], y = cur[1];

            for (int dir = 0; dir < 4; dir++) {
                int nx = x + dx[dir], ny = y + dy[dir];
                if (nx < 0 || ny < 0 || nx >= N || ny >= N) continue;
                if (visited[nx][ny]) continue;

                int diff = Math.abs(A[x][y] - A[nx][ny]);
                if (diff >= L && diff <= R) {
                    visited[nx][ny] = true;
                    q.add(new int[]{nx, ny});
                    union.add(new int[]{nx, ny});
                    sum += A[nx][ny];
                }
            }
        }
        return sum;
    }
}