package week06.산악구조로봇;

import java.io.*;
import java.util.*;

public class MayoneJY {
    static int T, N;
    static int[][] map;
    static int[] dx = {1, 0, -1, 0};
    static int[] dy = {0, 1, 0, -1};
    static boolean[][] visited;
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        T = Integer.parseInt(br.readLine());
        for(int t = 1; t <= T; t++){
            N = Integer.parseInt(br.readLine());
            map = new int[N][N];
            for(int i = 0; i < N; i++){
                StringTokenizer st = new StringTokenizer(br.readLine());
                for(int j = 0; j < N; j++){
                    map[i][j] = Integer.parseInt(st.nextToken());
                }
            }
            System.out.printf("#%d ",t);
            bfs();
        }
    }    

    static void bfs(){
        PriorityQueue<Node> q = new PriorityQueue<>((n1, n2) -> n1.idx - n2.idx);

        Node n = new Node(0, 0, 1);
        visited = new boolean[N][N];
        q.add(n);

        while (!q.isEmpty()) {
            Node now = q.poll();
            visited[now.y][now.x] = true;
            if(now.y == N-1 && now.x == N-1){
                System.out.printf("%d\n",now.idx-1);
                return;
            }
            for(int i = 0; i < 4; i++){
                Node next = new Node(now.y+dy[i], now.x+dx[i], now.idx);
                if(inRagne(next)){
                    int nowH = map[now.y][now.x];
                    int nextH = map[next.y][next.x];
                    if(nowH > nextH){
                        
                    }
                    else if(nowH == nextH){
                        next.idx++;
                    }
                    else if(nowH < nextH){
                        next.idx += (nextH - nowH) * 2;
                    }
                    q.add(next);
                }
            }
        }
    }

    static boolean inRagne(Node n){
        if(n.y < N && n.x < N && n.y >= 0 && n.x >= 0 && !visited[n.y][n.x]) return true;
        return false;
    }

    static class Node{
        int y, x, idx;
        Node(int y, int x, int idx){
            this.y = y;
            this.x = x;
            this.idx = idx;
        }
    }
}
