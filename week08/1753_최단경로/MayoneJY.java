import java.io.*;
import java.util.*;

public class MayoneJY {
    static class Edge implements Comparable<Edge>{
        int v, w;
        Edge(int v, int w){
            this.v = v;
            this.w = w;
        }

        @Override
        public int compareTo(Edge o){
            return this.w - o.w;
        }
    }

    static List<Edge>[] edges;
    static int V, E, K;

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        V = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());

        K = Integer.parseInt(br.readLine());

        edges = new ArrayList[V];
        
        for(int i = 0; i < V; i++){
            edges[i] = new ArrayList<>();
        }

        for(int i = 0; i < E; i++){
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());

            edges[u-1].add(new Edge(v-1, w));
        }
        for(int i = 0; i < V; i++){
            Collections.sort(edges[i]);
        }

        int[] result = bfs();
        for(int i = 0; i < result.length; i++){
            if(result[i] != Integer.MAX_VALUE)
                System.out.println(result[i]);
            else
                System.out.println("INF");
        }
    }    

    static int[] bfs(){
        PriorityQueue<Edge> pq = new PriorityQueue<>((o1, o2) -> o1.w - o2.w);

        boolean[] visited = new boolean[V];
        int[] result = new int[V];
        pq.add(new Edge(K-1, 0));


        Arrays.fill(result, Integer.MAX_VALUE);


        while (!pq.isEmpty()) {
            Edge now = pq.poll();
            if(result[now.v] > now.w)
                result[now.v] = now.w;
            
            if(visited[now.v])
                continue;
            visited[now.v] = true;

            boolean check = false;
            for(int i = 0; i < V; i++){
                if(!visited[i])
                    check = true;
            }
            if(!check)
                break;

            

            for(int i = 0; i < edges[now.v].size(); i++){
                Edge edge = edges[now.v].get(i);
                pq.add(new Edge(edge.v, now.w + edge.w));
            }
        }
        return result;
    }
}
