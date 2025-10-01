import java.util.*;
import java.io.*;

public class boj_17835 {
    static ArrayList<Node>[] arr;
    static Long[] value;
    static boolean[] visited;
    static int[] interview;
    static int N, M, K;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        arr = new ArrayList[N + 1];
        visited = new boolean[N + 1];
        value = new Long[N + 1];
        interview = new int[K];
        for (int i = 1; i <= N; i++) {
            arr[i] = new ArrayList<>();
        }
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            Long c = Long.parseLong(st.nextToken());
            arr[b].add(new Node(a, c)); //단방향 도로
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < K; i++) {
            interview[i] = Integer.parseInt(st.nextToken());
        }

        dijk();

        Long val = Long.MIN_VALUE;
        int index = 0;
        for (int i = 1; i <= N; i++) {
            if (val < value[i]) {
                index = i;
                val = value[i];
            }
        }
        System.out.println(index);
        System.out.println(val);
    }

    public static void dijk () {
        Arrays.fill(value, Long.MAX_VALUE);
        PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> Long.compare(a.value, b.value));
        for (int i : interview) {
            value[i] = 0L;
            pq.add(new Node(i, 0L));
        }
        while (!pq.isEmpty()) {
            Node now = pq.poll();
            if (visited[now.to]) continue;
            visited[now.to] = true;
            for (Node next : arr[now.to]) {
                if (value[next.to] > value[now.to] + next.value) {
                    value[next.to] = value[now.to] + next.value;
                    pq.add(new Node(next.to, value[next.to]));
                }
            }
        }
    }

    static class Node {
        int to;
        Long value;
        Node (int to, Long value) {
            this.to = to;
            this.value = value;
        }
    }
}
