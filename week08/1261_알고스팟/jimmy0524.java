import java.util.*;
import java.io.*;

public class Main {
    static int[][] arr;
    static int[][] value;
    static int[] dx = { 1, -1, 0, 0 };
    static int[] dy = { 0, 0, 1, -1 };
    static int N, M;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        arr = new int[M][N];
        value = new int[M][N];

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            String s = st.nextToken();
            Arrays.fill(value[i], Integer.MAX_VALUE);
            for (int j = 0; j < N; j++) {
                arr[i][j] = s.charAt(j) - '0';
            }
        }

        bfs();
        System.out.println(value[M - 1][N - 1]);
    }

    static void bfs() {
        Deque<int[]> dq = new ArrayDeque<>();
        dq.add(new int[] { 0, 0 });
        value[0][0] = 0;

        while (!dq.isEmpty()) {
            int[] now = dq.poll();
            int x = now[0];
            int y = now[1];
            for (int k = 0; k < 4; k++) {
                int nx = x + dx[k];
                int ny = y + dy[k];
                if (nx < 0 || ny < 0 || nx >= M || ny >= N)
                    continue;

                int cost = value[x][y] + arr[nx][ny];
                if (value[nx][ny] > cost) {
                    value[nx][ny] = cost;
                    if (arr[nx][ny] == 0) {
                        dq.addFirst(new int[] { nx, ny });
                    } else {
                        dq.addLast(new int[] { nx, ny });
                    }
                }
            }
        }
    }
}