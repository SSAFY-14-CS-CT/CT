import java.io.*;
import java.util.*;

public class Main {

    static int n, m;
    static int[][] board;
    static boolean[][] visited;
    static List<Node>[] islands;
    static PriorityQueue<Bridge> bridgeQueue;
    static int[] parent;

    static final int[] dx = {0, 1, 0, -1};
    static final int[] dy = {-1, 0, 1, 0};
    static final int MAX_ISLANDS = 7; // 1번부터 시작, 6개까지 가능

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        board = new int[n][m];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 섬 번호 부여
        visited = new boolean[n][m];
        islands = new ArrayList[MAX_ISLANDS];
        int islandCount = 1;

        for (int i = 0; i < MAX_ISLANDS; i++) {
            islands[i] = new ArrayList<>();
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (!visited[i][j] && board[i][j] == 1) {
                    bfs(i, j, islandCount++);
                }
            }
        }

        // 다리 후보 탐색
        bridgeQueue = new PriorityQueue<>();
        for (int i = 1; i < islandCount; i++) {
            for (Node node : islands[i]) {
                for (int dir = 0; dir < 4; dir++) {
                    findBridge(node.x, node.y, i, dir, 0);
                }
            }
        }

        System.out.println(kruskal(islandCount));
    }

    static void bfs(int x, int y, int islandNum) {
        Queue<Node> q = new LinkedList<>();
        q.offer(new Node(x, y));
        visited[x][y] = true;
        board[x][y] = islandNum;
        islands[islandNum].add(new Node(x, y));

        while (!q.isEmpty()) {
            Node cur = q.poll();
            for (int dir = 0; dir < 4; dir++) {
                int nx = cur.x + dx[dir];
                int ny = cur.y + dy[dir];
                if (isInBounds(nx, ny) && !visited[nx][ny] && board[nx][ny] == 1) {
                    visited[nx][ny] = true;
                    board[nx][ny] = islandNum;
                    q.offer(new Node(nx, ny));
                    islands[islandNum].add(new Node(nx, ny));
                }
            }
        }
    }

    static void findBridge(int x, int y, int fromIsland, int dir, int len) {
        int nx = x + dx[dir];
        int ny = y + dy[dir];

        if (!isInBounds(nx, ny)) return;
        if (board[nx][ny] == fromIsland) return;
        if (board[nx][ny] > 0) {
            if (len >= 2) {
                bridgeQueue.offer(new Bridge(fromIsland, board[nx][ny], len));
            }
            return;
        }
        findBridge(nx, ny, fromIsland, dir, len + 1);
    }

    static int kruskal(int islandCount) {
        parent = new int[islandCount];
        for (int i = 1; i < islandCount; i++) {
            parent[i] = i;
        }

        int totalCost = 0;
        int connected = 0;

        while (!bridgeQueue.isEmpty()) {
            Bridge bridge = bridgeQueue.poll();
            int root1 = find(bridge.from);
            int root2 = find(bridge.to);
            if (root1 != root2) {
                union(root1, root2);
                totalCost += bridge.length;
                connected++;
            }
        }

        if (connected == islandCount - 2) return totalCost; // 섬 번호가 1부터 시작했으므로 -2
        else return -1;
    }

    static int find(int x) {
        if (parent[x] == x) return x;
        return parent[x] = find(parent[x]);
    }

    static void union(int a, int b) {
        parent[find(a)] = find(b);
    }

    static boolean isInBounds(int x, int y) {
        return x >= 0 && y >= 0 && x < n && y < m;
    }

    static class Node {
        int x, y;
        Node(int x, int y) { this.x = x; this.y = y; }
    }

    static class Bridge implements Comparable<Bridge> {
        int from, to, length;
        Bridge(int from, int to, int length) {
            this.from = from;
            this.to = to;
            this.length = length;
        }
        public int compareTo(Bridge o) {
            return Integer.compare(this.length, o.length);
        }
    }
}