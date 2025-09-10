
import java.io.*;
import java.util.*;

public class MayoneJY {
    static int N, M, K;
    static List<Node>[] map;
    
    static class Node{
        int s, e, dis;
        Node(int s, int e, int dis){
            this.s = s;
            this.e = e;
            this.dis = dis;
        }
    }
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        map = new ArrayList[N];
        for(int i = 0; i < N; i++){
            map[i] = new ArrayList<>();
        }
        for(int m = 0; m < M; m++){
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());

            map[s-1].add(new Node(s-1, e-1, d));
        }
        int[] result = bfs();
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        for(int i = 0; i < N; i++){
            bw.write(Integer.toString(result[i]));
            bw.newLine();
        }
        bw.flush();
        bw.close();
    }

    static int[] bfs(){
        PriorityQueue<Node> q = new PriorityQueue<>((o1, o2) -> o1.dis - o2.dis);
        int[] result = new int[N];
        Arrays.fill(result, -1);
        for(int j = 0; j < map[0].size(); j++){
            Node n = map[0].get(j);
            q.add(new Node(n.s, n.e, n.dis));
        }
        int[] count = new int[N];
        count[0] = 1;
        
        while (!q.isEmpty()) {
            Node now = q.poll();
            if(count[now.e] < K) {
                count[now.e]++;
                result[now.e] = now.dis;
                
                for(int j = 0; j < map[now.e].size(); j++){
                    Node next = map[now.e].get(j);
                    if(count[next.e] < K) {
                        q.add(new Node(next.s, next.e, now.dis + next.dis));
                    }
                }
            }
        }

        for(int i = 0; i < N; i++){
            if(count[i] != K){
                result[i] = -1;
            }
        }
        if(result[0] == -1 && K == 1) result[0] = 0;
        return result;
    }
}
