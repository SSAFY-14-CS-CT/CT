import java.util.*;
import java.io.*;

public class Main {

    static int[][] arr;
    static int N;
    static int M;
    static int D;
    static int answer = 0;
    static int[] dx = { 0, -1, 0 };
    static int[] dy = { -1, 0, 1 };

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        D = Integer.parseInt(st.nextToken());

        arr = new int[N][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 0; i < M; i++) {
            for (int j = i + 1; j < M; j++) {
                for (int k = j + 1; k < M; k++) {
                    simulate(i, j, k);
                }
            }
        }

        System.out.println(answer);
    }

    private static void simulate(int i1, int i2, int i3) {
        int[][] copy = new int[N][M];
        for (int i = 0; i < N; i++) {
            copy[i] = arr[i].clone();
        }
        int killCount = 0;

        for (int turn = 0; turn < N; turn++) {
            boolean[][] killed = new boolean[N][M];

            Node t1 = bfs(i1, copy);
            Node t2 = bfs(i2, copy);
            Node t3 = bfs(i3, copy);

            if (t1 != null) killed[t1.x][t1.y] = true;
            if (t2 != null) killed[t2.x][t2.y] = true;
            if (t3 != null) killed[t3.x][t3.y] = true;

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    if (killed[i][j]) {
                        copy[i][j] = 0;
                        killCount++;
                    }
                }
            }

            for (int i = N - 1; i > 0; i--) {
                copy[i] = copy[i - 1].clone();
            }
            Arrays.fill(copy[0], 0);

        }

        answer = Math.max(answer, killCount);
    }

    private static Node bfs(int index, int[][] copy) {
        Queue<Node> q = new LinkedList<>();
        boolean[][] visited = new boolean[N][M];
        q.add(new Node(N, index, 0));

        while (!q.isEmpty()) {
            Node now = q.poll();

            for (int l = 0; l < 3; l++) {
                int nx = now.x + dx[l];
                int ny = now.y + dy[l];
                int ndist = now.dist + 1;

                if (nx < 0 || ny < 0 || nx >= N || ny >= M) continue;
                if (visited[nx][ny]) continue;
                visited[nx][ny] = true;
                if (ndist > D) continue;

                if (nx >= 0 && ny >= 0 && nx < N && ny < M) {

                    if (copy[nx][ny] == 1) {
                        return new Node(nx, ny, ndist);
                    }
                }
                q.add(new Node(nx, ny, ndist));
            }
        }
        return null;
    }

    public static class Node {
        int x, y, dist;

        public Node(int x, int y, int dist) {
            this.x = x;
            this.y = y;
            this.dist = dist;
        }
    }
}
