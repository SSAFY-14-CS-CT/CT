import java.io.*;
import java.util.*;

public class MayoneJY {
    static int N, M, S, E;
    static long max;
    static Map<Integer, List<Edge>> edges = new HashMap<>();
    static class Edge{
        int v;
        long w, m = 0;
        Edge(int v, long w){
            this.v = v;
            this.w = w;
        }
        Edge(int v, long w, long m){
            this.v = v;
            this.w = w;
            this.m = m;
        }
    }
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        S = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());
        max = Long.parseLong(st.nextToken());
        
        for(int i = 0; i < M; i++){
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());
            long v = Long.parseLong(st.nextToken());
            edges.putIfAbsent(s, new ArrayList<>());
            edges.putIfAbsent(e, new ArrayList<>());

            edges.get(s).add(new Edge(e, v));
            edges.get(e).add(new Edge(s, v));
        }

        bfs();
    }

    static void bfs(){
        PriorityQueue<Edge> pq = new PriorityQueue<>((o1, o2) -> {
            if(o1.m != o2.m)
                return Long.compare(o1.m, o2.m);
            return Long.compare(o1.w, o2.w);
        });

        long[] visited = new long[N+1];
        long[] visitedMax = new long[N+1];
        Arrays.fill(visited, Long.MAX_VALUE);
        Arrays.fill(visitedMax, Long.MAX_VALUE);
        visited[S] = 0;
        visitedMax[S] = 0;
        pq.add(new Edge(S, 0));
        long result = Long.MAX_VALUE;
        while (!pq.isEmpty()) {
            Edge now = pq.poll();

            if(now.m >= result)
                continue;
            if(now.v == E){
                result = now.m;
                // break;
            }

            if(visited[now.v] < now.w) continue;

            for(Edge e: edges.get(now.v)){
                long mm = Math.max(now.m, e.w);
                if(now.w + e.w <= max && visitedMax[e.v] > mm){
                    visited[e.v] = now.w + e.w;
                    visitedMax[e.v] = mm;
                    pq.add(new Edge(e.v, visited[e.v], mm));
                }
            }
        }
        // while (!pq.isEmpty()) {
        //     Edge now = pq.poll();
        //     if(now.v == E){
        //         result = Math.min(result, now.m);
        //     }
        // }
        if(result == Long.MAX_VALUE)
            System.out.println(-1);
        else
            System.out.println(result);
    }
}
