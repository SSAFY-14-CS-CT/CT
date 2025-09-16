import java.util.*;
import java.io.*;

public class Main {
    static ArrayList<Node>[] arr;
    static boolean[] visited;
    static PriorityQueue<Node> pq;
    static PriorityQueue<Integer>[] dist;
    static int k;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        arr = new ArrayList[n + 1];
        dist = new PriorityQueue[n + 1];
        pq = new PriorityQueue<>((a, b) -> a.value - b.value);
        for (int i = 1; i <= n; i++) {
            arr[i] = new ArrayList<>();
            dist[i] = new PriorityQueue<>((a, b) -> b - a);
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            arr[a].add(new Node(b, c));
        }

        dijk();

        StringBuffer sb = new StringBuffer();
        for (int i = 1; i <= n; i++) {
            if (dist[i].size() < k) {
                sb.append("-1");
            } else {
                sb.append(dist[i].peek());
            }
            sb.append("\n");
        }
        System.out.println(sb);
    }

    public static void dijk() {
        pq.add(new Node(1, 0));
        dist[1].add(0);

        while (!pq.isEmpty()) {
            Node now = pq.poll();
            int nowTo = now.to;
            int nowValue = now.value;
            for (Node next : arr[nowTo]) {
                int cost = nowValue + next.value;
                if (dist[next.to].size() < k) {
                    dist[next.to].add(cost);
                    pq.add(new Node(next.to, cost));
                } else if (dist[next.to].peek() > cost) { // k개 차있는 상태에 더 작은값이면 갱신
                    dist[next.to].poll();
                    dist[next.to].add(cost);
                    pq.add(new Node(next.to, cost));
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
