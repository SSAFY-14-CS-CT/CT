import java.util.*;
import java.io.*;

public class Main {
    static int T, N;
    static int[][] map;
    static int[][] dist;
    final static int[] dx = {0, 1, 0, -1};
    final static int[] dy = {1, 0, -1, 0};

    static class Node implements Comparable<Node> {
        int x, y, d;
        Node(int x, int y, int d) {
            this.x = x;
            this.y = y;
            this.d = d;
        }

        public int compareTo(Node other) {
            return this.d - other.d;
        }
    }

    public static void main(String[]args)throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        T = Integer.parseInt(br.readLine());

        for (int t = 0; t < T; t++) {
            N = Integer.parseInt(br.readLine());
            map = new int[N + 1][N + 1];
            dist = new int[N + 1][N + 1];

            for (int i = 1; i <= N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 1; j <= N; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                    dist[i][j] = Integer.MAX_VALUE;
                }
            }

            sb.append("#").append(t + 1).append(" ").append(dijkstra()).append("\n");
        }

        System.out.print(sb);
    }

    static int dijkstra() {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.offer(new Node(1, 1, map[1][1]));
        dist[1][1] = 0;

        while(!pq.isEmpty()) {
            Node cur = pq.poll();
            int cx = cur.x;
            int cy = cur.y;


            for (int dir = 0; dir < 4; dir++) {
                int nx = cur.x + dx[dir];
                int ny = cur.y + dy[dir];

                if (inBound(nx, ny)) {
                    int cost = map[nx][ny] - map[cur.x][cur.y];
                    if (cost == 0) {
                        cost = 1;
                    } else if (cost > 0) {
                        cost = cost * 2;
                    } else {
                        cost = 0;
                    }

                    if (dist[cx][cy] + cost < dist[nx][ny]) {
                        pq.offer(new Node(nx, ny, map[nx][ny]));
                        dist[nx][ny] = dist[cx][cy] + cost;
                    }
                }
            }
        }

        return dist[N][N];
    }

    static boolean inBound(int x, int y) {
        return x >= 1 && x <= N && y >= 1 && y <= N;
    }
}