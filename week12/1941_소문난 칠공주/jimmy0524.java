import java.io.*;
import java.util.*;

public class Main {
    static char[][] arr;
    static int answer;
    static int[] dx = {0,0,1,-1};
    static int[] dy = {1,-1,0,0};
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        arr = new char[5][5];
        for (int i = 0; i < 5; i++) {
            String s = br.readLine();
            for (int j = 0; j < 5; j++) {
                arr[i][j] = s.charAt(j);
            }
        }
        boolean[][] tmp = new boolean[5][5];
        combination(0, 0, 0, tmp);
        System.out.println(answer);
    }

    public static void combination(int idx, int yCount, int xCount, boolean[][] tmp) {
        if (yCount + xCount == 7) {
            if (yCount >= 4) return;
            boolean[][] visited = new boolean[5][5];
            breakpoint:
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (tmp[i][j]) {
                        bfs(i, j, tmp, visited);
                        break breakpoint;
                    }
                }
            }
            return;
        }

        for (int i = idx; i < 25; i++) {
            int x = i / 5;
            int y = i % 5;
            tmp[x][y] = true;
            if (arr[x][y] == 'Y') {
                combination(i + 1, yCount + 1, xCount, tmp);
            } else {
                combination(i + 1, yCount, xCount + 1, tmp);
            }
            tmp[x][y] = false;
        }
    }

    public static void bfs(int x, int y, boolean[][] tmp, boolean[][] visited) {
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{x, y});
        visited[x][y] = true;
        int count = 0;

        while(!q.isEmpty()) {
            int[] now = q.poll();
            count++;
            int nx = now[0];
            int ny = now[1];

            for (int i = 0; i < 4; i++) {
                int nextX = nx + dx[i];
                int nextY = ny + dy[i];

                if (nextX >= 5 || nextY >= 5 || nextX < 0 || nextY < 0 || visited[nextX][nextY]) continue;
                if (tmp[nextX][nextY]) {
                    visited[nextX][nextY] = true;
                    q.add(new int[]{nextX, nextY});
                }
            }
        }

        if (count == 7) {
            answer++;
        }
    }
}
