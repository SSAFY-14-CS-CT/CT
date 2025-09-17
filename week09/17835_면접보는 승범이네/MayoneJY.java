import java.io.*;
import java.util.*;

public class MayoneJY {
    static int N, M, K;
    static Map<Integer, List<Edge>> map = new HashMap<>();
    static int[] targets;

    static class Edge implements Comparable<Edge>{
        int v;
        long w;
        Edge(int v, long w){
            this.v = v;
            this.w = w;
        }

        @Override
        public int compareTo(Edge e){
            return Long.compare(this.w, e.w);
        }
    }
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        targets = new int[K];

        for(int m = 0; m < M; m++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            long c = Long.parseLong(st.nextToken());
            map.putIfAbsent(b, new ArrayList<>());
            map.putIfAbsent(a, new ArrayList<>());            
            map.get(b).add(new Edge(a, c));
        }

        map.forEach((key, value) -> {
            Collections.sort(value);
        });

        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < K; i++){
            targets[i] = Integer.parseInt(st.nextToken());
        }

        bfs();

    }

    static void bfs(){
        PriorityQueue<Edge> pq = new PriorityQueue<>();

        long resultMax = 0;
        int resultTarget = 100_001;
        long[] visited = new long[N+1];
        Arrays.fill(visited, Long.MAX_VALUE);
        for(int i = 0; i < K; i++){
            pq.add(new Edge(targets[i], 0));
            visited[targets[i]] = 0;
        }


        while (!pq.isEmpty()) {
            Edge now = pq.poll();
            if(now.w != visited[now.v]) continue;

            if(resultMax < now.w){
                resultMax = visited[now.v];
                resultTarget = now.v;
            }
            else if(resultMax == now.w){
                if(resultTarget > now.v)
                    resultTarget = now.v;
            }

            List<Edge> edges = map.get(now.v);
            for(int i = 0; i < edges.size(); i++){
                Edge next = new Edge(edges.get(i).v, edges.get(i).w + now.w);
                if(visited[next.v] >= next.w){
                    visited[next.v] = next.w;
                    pq.add(next);
                }
            }
        }

        System.out.println(resultTarget);
        System.out.println(resultMax);
        
    }
}
