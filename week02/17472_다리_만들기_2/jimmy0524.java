import java.util.*;
import java.io.*;

public class Main {
    static int index;
    static boolean[][] visited;
    static int[] dx = {0, 0, 1, -1};
    static int[] dy = {1, -1, 0, 0};
    static int N;
    static int M;
    static int[][] arr;
    static int[][] length;
    static List<Bridge> bridges;
    static int[] parent;

    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        arr = new int[N][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        visited = new boolean[N][M];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (!visited[i][j] && arr[i][j] == 1) {
                    index++;
                    bfs(new Node(i, j));
                }
            }
        }

        if (index <= 1) {
            System.out.println(0);
            return;
        }

        findBridges();

        int result = kruskal();
        System.out.println(result);
    }

    public static void bfs(Node node) {
        Queue<Node> q = new LinkedList<>();
        q.add(node);
        visited[node.x][node.y] = true;
        arr[node.x][node.y] = index;

        while(!q.isEmpty()) {
            Node now = q.poll();
            for (int i = 0; i < 4; i++) {
                int nx = now.x + dx[i];
                int ny = now.y + dy[i];
                if (nx >= 0 && ny >= 0 && nx < N && ny < M && !visited[nx][ny] && arr[nx][ny] == 1) {
                    visited[nx][ny] = true;
                    arr[nx][ny] = index;
                    q.add(new Node(nx, ny));
                }
            }
        }
    }

    static void findBridges() {
        bridges = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (arr[i][j] != 0) {
                    findBridge(i, j);
                }
            }
        }
    }

    static void findBridge(int x, int y) {
        int start = arr[x][y];

        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];
            int length = 0;
            while (nx >= 0 && nx < N && ny >= 0 && ny < M && arr[nx][ny] != start) {
                if (arr[nx][ny] != 0) { // 다른 섬을 만남
                    if (length > 1) {
                        bridges.add(new Bridge(start, arr[nx][ny], length));
                    }
                    break;
                }
                length++;
                nx += dx[i];
                ny += dy[i];
            }
        }
    }

    static int kruskal() {
        bridges.sort((a, b) -> {return a.length - b.length;});
        parent = new int[index + 1];
        for (int i = 1; i <= index; i++) {
            parent[i] = i;
        }

        int totalLength = 0;
        int connectedCount = 0;

        for (Bridge bridge : bridges) {
            if (find(bridge.start) != find(bridge.end)) {
                union(bridge.start, bridge.end);
                totalLength += bridge.length;
                connectedCount++;
            }
        }

        return connectedCount == index - 1 ? totalLength : -1;
    }

    static void union(int a, int b) {
        int findA = find(a);
        int findB = find(b);

        if (findA != findB) {
            if (findA < findB) {
                parent[findB] = findA;
            } else {
                parent[findA] = findB;
            }
        }
    }

    static int find(int x) {
        if (parent[x] == x) {
            return x;
        }
        return parent[x] = find(parent[x]);
    }

    static class Bridge {
        int start, end, length;

        public Bridge(int start, int end, int length) {
            this.start = start;
            this.end = end;
            this.length = length;
        }
    }

    public static class Node {
        int x, y;
        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

}
