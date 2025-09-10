import java.util.*;
import java.io.*;

public class Main {
    static ArrayList<Node>[] arr;
    static int[] dist;
    static boolean[] visited;
    static PriorityQueue<Node> pq;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int V = Integer.parseInt(st.nextToken());
        int E = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(br.readLine());

        arr = new ArrayList[V + 1];
        dist = new int[V + 1];
        visited = new boolean[V + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        pq = new PriorityQueue<>((a, b) -> a.value - b.value);

        for (int i = 1; i <= V; i++) {
            arr[i] = new ArrayList<>();
        }

        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int dist = Integer.parseInt(st.nextToken());
            arr[from].add(new Node(to, dist));
        }
        disk(K);
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= V; i++) {
            if (dist[i] == Integer.MAX_VALUE) {
                sb.append("INF");
            } else {
                sb.append(dist[i]);
            }
            sb.append("\n");
        }
        System.out.println(sb);
    }

    public static void disk(int start) {
        dist[start] = 0;
        pq.add(new Node(start, 0));

        while (!pq.isEmpty()) {
            Node now = pq.poll();
            int to = now.to;
            if (visited[to])
                continue;
            visited[to] = true;

            for (Node next : arr[to]) {
                if (dist[next.to] > dist[to] + next.value) {
                    dist[next.to] = dist[to] + next.value;
                    pq.add(new Node(next.to, dist[next.to]));
                }
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
