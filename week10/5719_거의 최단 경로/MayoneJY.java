
import java.io.*;
import java.util.*;

public class MayoneJY {
    public static int N, M, S, E;
    static Map<Integer, Node>[] map;
    
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
        while (true) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            if(N == 0 && M == 0)
                break;
            st = new StringTokenizer(br.readLine());
            S = Integer.parseInt(st.nextToken());
            E = Integer.parseInt(st.nextToken());
            map = new HashMap[N];
            for(int i = 0; i < N; i++){
                map[i] = new HashMap<>();
            }
            for(int m = 0; m < M; m++){
                st = new StringTokenizer(br.readLine());
                int s = Integer.parseInt(st.nextToken());
                Integer e = Integer.parseInt(st.nextToken());
                int d = Integer.parseInt(st.nextToken());

                map[s].put(e, new Node(s, e, d));
            }
            int result = bfs();
            System.out.println(result);
        }
    }

    static int bfs(){
        PriorityQueue<Node> q = new PriorityQueue<>((o1, o2) -> o1.dis - o2.dis);
        int min = -1;
        int count = 0;
        boolean deleted = false;
        Map<Integer, Integer> m = new HashMap<>();

        breakout:
        while (true) {
            boolean[] result = new boolean[N];
            int[][] prev = new int[N][2];
            int[] dist = new int[N];
            for(int i = 0; i < N; i++){
                prev[i][0] = -1;
                prev[i][1] = Integer.MAX_VALUE;
                dist[i] = Integer.MAX_VALUE;
            }
            for(Integer key : map[S].keySet()){
                Node n = map[S].get(key);
                q.add(new Node(n.s, n.e, n.dis));
            }
            while (!q.isEmpty()) {
                Node now = q.poll();
                if(now.e == E){
                    if(deleted){
                        count++;
                        min = now.dis;
                        q.clear();
                        break breakout;
                    }
                    if(prev[now.e][0] == -1 || prev[now.e][1] >= now.dis){
                        prev[now.e][0] = now.s;
                        prev[now.e][1] = now.dis;
                    }
                    if(min == -1){
                        count++;
                        min = now.dis;
                        
                        int cInt = E;
                        while(cInt != S){
                            int temp = cInt;
                            cInt = prev[cInt][0];
                            m.put(cInt, temp);
                        }
                    }
                    else if(min == now.dis){
                        int cInt = E;
                        while(cInt != S){
                            int temp = cInt;
                            cInt = prev[cInt][0];
                            m.put(cInt, temp);
                        }
                    }
                    else if(min != now.dis){
                        // count++;
                        // min = now.dis;
                        for(int mS : m.keySet()){
                            map[mS].remove(m.get(mS));
                        }
                        deleted = true;
                        m = null;
                        q.clear();
                        continue breakout;
                    }
                }

                if(prev[now.e][0] == -1 || prev[now.e][1] >= now.dis){
                    prev[now.e][0] = now.s;
                    prev[now.e][1] = now.dis;
                    for(Integer key : map[now.e].keySet()){
                        if(!result[now.e]){
                            Node next = map[now.e].get(key);
                            Node newNode = new Node(next.s, next.e, now.dis + next.dis);
                            q.add(newNode);
                        }    
                    }
                }
            }

            if(q.isEmpty())
                break;
        }
        return (count <= 1)?-1:min;
    }
}
