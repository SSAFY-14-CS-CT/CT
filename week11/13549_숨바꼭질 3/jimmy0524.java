import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.StringTokenizer;

public class boj_13549 {
    static final int MAX = 100000;
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int start = Integer.parseInt(st.nextToken());
        int target = Integer.parseInt(st.nextToken());

        int[] dist = new int[MAX + 1];
        Arrays.fill(dist, -1);

        Deque<Node> dq = new ArrayDeque<>();
        dq.offerFirst(new Node(start, 0));
        dist[start] = 0;

        while (!dq.isEmpty()) {
            Node now = dq.poll();
            int cur = now.pos;

            if (cur == target) {
                System.out.println(dist[cur]);
                return;
            }

            if (cur * 2 <= MAX && dist[cur * 2] == -1) {
                dist[cur * 2] = dist[cur];
                dq.offerFirst(new Node(cur * 2, dist[cur]));
            }

            if (cur - 1 >= 0 && dist[cur - 1] == -1) {
                dist[cur - 1] = dist[cur] + 1;
                dq.offerLast(new Node(cur - 1, dist[cur] + 1));
            }

            if (cur + 1 <= MAX && dist[cur + 1] == -1) {
                dist[cur + 1] = dist[cur] + 1;
                dq.offerLast(new Node(cur + 1, dist[cur] + 1));
            }
        }
    }

    public static class Node {
        int pos;
        int time;

        Node(int pos, int time) {
            this.pos = pos;
            this.time = time;
        }
    }
}