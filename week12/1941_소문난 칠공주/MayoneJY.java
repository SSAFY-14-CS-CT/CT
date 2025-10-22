import java.util.*;
import java.io.*;

public class MayoneJY {
    static final int N = 5;
    static final int MIN = 4;
    static final int M = 7;
    static int result = 0;
    static char[] map = new char[N*N];
    static List<Integer> pick = new ArrayList<>();
    static int[] dx = {1, 0, -1, 0};
    static int[] dy = {0, 1, 0, -1};

    static class Node implements Comparable<Node> {
        int y, x;
        Node(int y, int x){
            this.y = y;
            this.x = x;
        }

        @Override
        public int compareTo(Node o){
            if (this.x != o.x) return Integer.compare(this.x, o.x);
            return Integer.compare(this.y, o.y);
        }
    }
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = 0;
        for(int i = 0; i < N; i++){
            char[] str = br.readLine().toCharArray();
            for(int j = 0; j < N; j++){
                map[t++] = str[j];
            }
        }
    
        for(int i = 0; i < 25; i++){
            pick.clear();
            pick.add(i);
            dfs(i+1, 1, map[i] == 'S'?1:0);
        }
        System.out.println(result);
    }

    public static void bfs(int y, int x){
        ArrayDeque<int[]> q = new ArrayDeque<>();
        q.add(new int[]{y, x});
        boolean[][] visited = new boolean[N][N];
        visited[y][x] = true;
        int count = 0;

        while(!q.isEmpty()){
            int[] now = q.poll();
            count++;
            for(int i = 0; i < 4; i++){
                int ny = now[0] + dy[i];
                int nx = now[1] + dx[i];

                if(ny >= 0 && nx >= 0 && ny < N && nx < N && !visited[ny][nx]){
                    
                    visited[ny][nx] = true;
                    if(pick.contains(ny*5+nx)){
                        q.add(new int[] {ny, nx});
                    }
                }
            }
        }
        if(count == 7)
            result++;
    }

    public static void dfs(int idx, int count, int sCount){
        if(count == M){
            if(sCount >= MIN){
                bfs(pick.get(0)/5, pick.get(0)%5);
            }
            return;
        }
        if(count - sCount == 4)
            return;
        for(int i = idx; i < 25; i++){
            pick.add(i);
            dfs(i+1, count+1, map[i] == 'S'?sCount+1:sCount);
            pick.remove(pick.size()-1);
        }
    }
}
