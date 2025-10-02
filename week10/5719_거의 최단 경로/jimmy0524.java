import java.util.*;
import java.io.*;

public class Main {
    static boolean[][] removed;
    static boolean[] visited;
    static int[] values;
    static ArrayList<Node>[] arr;
    static ArrayList<Integer>[] path; // 역추적 위함 본인한테 오는 경로들
    static int start, end;
    static int N, M;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken()); // 장소의 수
            M = Integer.parseInt(st.nextToken()); // 도로의 수
            arr = new ArrayList[N];
            path = new ArrayList[N];

            for (int i = 0; i < N; i++) {
                arr[i] = new ArrayList<>();
                path[i] = new ArrayList<>();
            }
            values = new int[N];
            if (N == 0 && M == 0) {
                break;
            }
            st = new StringTokenizer(br.readLine());
            start = Integer.parseInt(st.nextToken());
            end = Integer.parseInt(st.nextToken());

            // U에서 V로 가는 도로의 길이가 P
            for (int i = 0; i < M; i++) {
                st = new StringTokenizer(br.readLine());
                int U = Integer.parseInt(st.nextToken());
                int V = Integer.parseInt(st.nextToken());
                int P = Integer.parseInt(st.nextToken());
                arr[U].add(new Node(V, P));
            }

            removed = new boolean[N][N];
            dijk(start);
            removePath(end);
            dijk(start);

            if (values[end] == Integer.MAX_VALUE) {
                System.out.println(-1);
            } else {
                System.out.println(values[end]);
            }

        }
    }

    public static void dijk(int start) {
        PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> a.value - b.value);
        pq.add(new Node(start, 0));
        Arrays.fill(values, Integer.MAX_VALUE);
        visited = new boolean[N];
        values[start] = 0;
        while (!pq.isEmpty()) {
            Node now = pq.poll();
            if (visited[now.to])
                continue;
            visited[now.to] = true;
            for (Node next : arr[now.to]) {
                if (removed[now.to][next.to]) continue;
                if (values[next.to] > values[now.to] + next.value) {
                    path[next.to].clear();
                    path[next.to].add(now.to);
                    values[next.to] = values[now.to] + next.value;
                    pq.add(new Node(next.to, values[next.to]));
                } else if (values[next.to] == values[now.to] + next.value) {
                    path[next.to].add(now.to); // 동일 경로여도
                }
            }
        }
    }

    private static void removePath(int index) {
        for (int prev : path[index]) {
            if (!removed[prev][index]) {   // 이미 제거한 간선이면 skip
                removed[prev][index] = true;
                removePath(prev);
            }
        }
    }

    public static class Node {
        int to, value;

        Node(int to, int value) {
            this.to = to;
            this.value = value;
        }
    }
}
