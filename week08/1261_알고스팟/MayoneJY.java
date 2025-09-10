
import java.io.*;
import java.util.*;

public class MayoneJY {
    static class Node{
        int y, x, w;
        Node(int y, int x, int w){
            this.y = y;
            this.x = x;
            this.w = w;
        }
    }

    static int N, M;
    static boolean[][] map;
    static int[] dx = {1, 0, -1, 0};
    static int[] dy = {0, 1, 0, -1};
    static boolean[][] visited;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        M = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());
        map = new boolean[N][M];
        for(int i = 0; i < N; i++){
            String str = br.readLine();
            for(int j = 0; j < M; j++){
                map[i][j] = (str.charAt(j) == '1')?true:false;
            }
        }
        
        System.out.println(bfs());

    }

    static int bfs(){
        PriorityQueue<Node> pq = new PriorityQueue<>((o1, o2) -> o1.w - o2.w);
        pq.add(new Node(0, 0, 0));
        visited = new boolean[N][M];

        while (!pq.isEmpty()) {
            Node now = pq.poll();

            if(now.y == N-1 && now.x == M-1)
                return now.w;
                
            for(int i = 0; i < 4; i++){
                Node next = new Node(now.y+dy[i], now.x+dx[i], now.w);
                if(isRange(next)){
                    if(map[next.y][next.x]){
                        next.w += 1;
                    }
                    pq.add(next);
                    visited[next.y][next.x] = true;
                }
            }
        }

        return -1;
    }

    static boolean isRange(Node n){
        if(n.y >= N || n.x >= M || n.y < 0 || n.x < 0 || visited[n.y][n.x]) return false;
        return true;
    }
}
