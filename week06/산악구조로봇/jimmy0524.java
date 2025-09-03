import java.io.*;
import java.util.*;

public class problem2 {
    static int[][] arr;
    static boolean[][] visited;
    static int[] dx = {0, 0, -1, 1};
    static int[] dy = {1, -1, 0, 0};
    static int N;

    public static void main(String[] args) throws Exception {
        //높이가 같은 곳 : 연료 1소모
        //높이가 낮은 곳 : 연료 소모 x
        //높이가 높은 곳 : 높이 차의 두배
        //최소 연료 소비해야됨

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int T = Integer.parseInt(br.readLine());
        for (int t = 1; t <= T; t++) {
            N = Integer.parseInt(br.readLine());
            arr = new int[N + 1][N + 1];
            visited = new boolean[N + 1][N + 1];
            for (int i = 1; i <= N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 1; j <= N; j++) {
                    arr[i][j] = Integer.parseInt(st.nextToken());
                }
            }
            int answer = bfs();
            System.out.println("#" + t + " " + answer);
        }
    }

    public static int bfs() {
        PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> a.sum - b.sum);
        pq.add(new Node(1, 1, arr[1][1], 0));

        while(!pq.isEmpty()) {
            Node now = pq.poll();
            int nowVal = now.value;
            int nowSum = now.sum;
            visited[now.x][now.y] = true;
            if (now.x == N && now.y == N) {
                return now.sum;
            }
            for (int i = 0; i < 4; i++) {
                int nx = now.x + dx[i];
                int ny = now.y + dy[i];
                if (nx > N || ny > N || nx <= 0 || ny <= 0 || visited[nx][ny]) continue;
                int nextVal = arr[nx][ny];
                if (nowVal == nextVal) {
                    pq.add(new Node(nx, ny, nextVal, nowSum + 1));
                } else if (nowVal < nextVal) {
                    pq.add(new Node(nx, ny, nextVal, nowSum + (nextVal - nowVal) * 2));
                } else {
                    pq.add(new Node(nx, ny, nextVal, nowSum));
                }
            }
        }
        return -1;
    }

    public static class Node {
        int x, y, value, sum;
        Node (int x, int y, int value, int sum) {
            this.x = x;
            this.y = y;
            this.value = value;
            this.sum = sum;
        }
    }
}
