import java.io.*;
import java.util.*;

public class Main {
    static ArrayList<Node> arr;
    static int[] parent;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int m = Integer.parseInt(st.nextToken()); //집의수
            int n = Integer.parseInt(st.nextToken()); //길의수
            int sumCost = 0;
            arr = new ArrayList<>();
            if (m == 0 && n == 0) return;
            parent = new int[m];
            for (int i = 0; i < m; i++) {
                parent[i] = i;
            }

            for (int i = 0; i < n; i++) {
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                int c = Integer.parseInt(st.nextToken());
                arr.add(new Node(a, b, c));
                sumCost += c;
            }

            Collections.sort(arr, (a, b) -> a.cost - b.cost);

            int count = 0; //연결된 간선의 수
            int answer = 0; //누적 비용
            for (Node now : arr) {
                if (count == m - 1) break;
                int from = now.from;
                int to = now.to;
                int cost = now.cost;

                if (find(from) != find(to)) {
                    union(from, to);
                    answer += cost;
                    count++;
                }
            }
            System.out.println(sumCost - answer);
        }
    }

    public static void union(int a, int b) {
        a = find(a);
        b = find(b);
        if (a == b) return;
        if (a > b) {
            parent[a] = b;
        } else {
            parent[b] = a;
        }
    }

    public static int find(int a) {
        if (parent[a] == a) {
            return a;
        }

        return find(parent[a]);
    }

    public static class Node {
        int from, to, cost;

        Node(int from, int to, int cost) {
            this.from = from;
            this.to = to;
            this.cost = cost;
        }
    }
}
