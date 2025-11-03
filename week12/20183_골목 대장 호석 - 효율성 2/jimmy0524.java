import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static List<Node>[] arr;
    static int N, M, A, B;
    static long C;
    static long maxValue = Long.MIN_VALUE;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        A = Integer.parseInt(st.nextToken());
        B = Integer.parseInt(st.nextToken());
        C = Long.parseLong(st.nextToken());
        arr = new List[N + 1];

        for (int i = 1; i <= N; i++) {
            arr[i] = new ArrayList<>();
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());
            arr[start].add(new Node(end, cost));
            arr[end].add(new Node(start, cost));
            maxValue = Math.max(maxValue, cost);
        }

        long result = binarySearch();

        if (result == Long.MAX_VALUE) {
            System.out.println(-1);
        } else {
            System.out.println(result);
        }
    }

    public static boolean canGo(long limit) { //다익스트라
        //누적 최소
        PriorityQueue<Node> pq = new PriorityQueue<>((a,b) -> Long.compare(a.cost, b.cost));
        boolean[] visited = new boolean[N + 1];
        long[] dist = new long[N + 1];
        Arrays.fill(dist, Long.MAX_VALUE);
        pq.add(new Node(A, 0));
        dist[A] = 0;

        while(!pq.isEmpty()) {
            Node now = pq.poll();
            if (visited[now.to]) continue;
            visited[now.to] = true;
            for (Node next : arr[now.to]) {
                if (next.cost > limit) continue;
                long newCost = now.cost + next.cost;
                if (newCost > C) continue;
                if (dist[next.to] > newCost) {
                    dist[next.to] = newCost;
                    pq.add(new Node(next.to, dist[next.to]));
                }
            }
        }

        if (dist[B] == Long.MAX_VALUE) {
            return false;
        } else {
            return true;
        }
    }

    public static long binarySearch() { //이분탐색
        long left = 0;
        long right = maxValue;
        long result = Long.MAX_VALUE;
        while (left <= right) {
            long mid = (left + right) / 2;
            if (canGo(mid)) {
                result = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return result;
    }

    public static class Node {
        int to;
        long cost;
        Node (int to, long cost) {
            this.to = to;
            this.cost = cost;
        }
    }
}
