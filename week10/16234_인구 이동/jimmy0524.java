import java.util.*;
import java.io.*;

public class Main {
    static boolean[][] visited;
    static int[][] arr;
    static int N, L, R;
    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        L = Integer.parseInt(st.nextToken()); //L명 이상
        R = Integer.parseInt(st.nextToken()); //R명 이하
        arr = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int day =  0;
        boolean check = true;
        while (check) {
            check = false;
            visited = new boolean[N][N];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (visited[i][j]) continue;
                    List<int[]> list = bfs(new int[]{i, j});
                    if (list.size() >= 2) {
                        check = true;
                        move(list);
                    }
                }
            }
            if (check) {
                day++;
            }
        }
        System.out.println(day);
    }

    private static void move(List<int[]> list) {
        int sum = 0;
        for (int[] a : list) {
            sum += arr[a[0]][a[1]];
        }

        for (int[] a : list) {
            arr[a[0]][a[1]] = sum / list.size();
        }
    }

    public static List<int[]> bfs(int[] start) {
        List<int[]> union = new ArrayList<>();
        Queue<int[]> q = new ArrayDeque<>();
        q.add(start);
        visited[start[0]][start[1]] = true;

        while(!q.isEmpty()) {
            int[] now = q.poll();
            union.add(now);
            for (int i = 0; i < 4; i++) {
                int nx = now[0] + dx[i];
                int ny = now[1] + dy[i];
                if (nx < 0 || ny < 0 || nx >= N || ny >= N || visited[nx][ny]) continue;
                if (Math.abs(arr[now[0]][now[1]] - arr[nx][ny]) < L || Math.abs(arr[now[0]][now[1]] - arr[nx][ny]) > R)
                    continue;
                q.add(new int[]{nx, ny});
                visited[nx][ny] = true;
            }
        }
        return union;
    }
}