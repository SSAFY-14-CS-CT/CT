import java.io.*;
import java.util.*;

public class MayoneJY {
    static int N, M;
    static PriorityQueue<Edge> pq = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.w, o2.w));
    static int[] parent;
    static int max;
    static class Edge{
        int s, v, w;
        Edge(int s, int v, int w){
            this.s = s;
            this.v = v;
            this.w = w;
        }
    }
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());

            if(N == 0 && M == 0) return;
            max = 0;
            parent = new int[N];
            for(int i = 0; i < N; i++)
                parent[i] = i;

            for(int i = 0; i < M; i++){
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                int c = Integer.parseInt(st.nextToken());
                max += c;
                pq.add(new Edge(a, b, c));
            }
    
            int result = 0;
            while (!pq.isEmpty()) {
                Edge now = pq.poll();

                if(find(now.s) == find(now.v)) continue;

                union(now.s, now.v);
                result += now.w;
                
            }

            System.out.println(max - result);
        }
    }

    public static int find(int a){
        if (parent[a] == a) return a;
        return parent[a] = find(parent[a]);
    }

    public static void union(int a, int b){
        a = find(a);
        b = find(b);
        if(a != b){
            parent[b] = a;
        }
    }
}
