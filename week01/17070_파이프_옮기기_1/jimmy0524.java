import java.io.*;
import java.util.*;

public class Main {
    // 54~
    static int[][] arr;
    static int answer;
    static int N;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        arr = new int[N][N];
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        dfs(0, 1, 0);
        System.out.println(answer);
    }

    // state = 0 -> 가로 1 -> 세로 2 -> 대각선
    public static void dfs(int x, int y, int state) {
        if (x == N - 1 && y == N - 1) {
            answer++;
            return;
        }
        // 가로
        if (state == 0) {
            if (y <= N - 2 && arr[x][y + 1] == 0) {
                dfs(x, y + 1, 0);
                if (x <= N - 2) {
                    if (arr[x + 1][y + 1] == 0 && arr[x + 1][y] == 0) {
                        dfs(x + 1, y + 1, 2);
                    }
                }
            }
        }
        // 세로
        else if (state == 1) {
            if (x <= N - 2 && arr[x + 1][y] == 0) {
                dfs(x + 1, y, 1);
                if (y <= N - 2) {
                    if (arr[x + 1][y + 1] == 0 && arr[x][y + 1] == 0) {
                        dfs(x + 1, y + 1, 2);
                    }
                }
            }
        }
        // 대각선
        else {
            if (y <= N - 2 && arr[x][y + 1] == 0) {
                dfs(x, y + 1, 0);
            }
            if (x <= N - 2 && arr[x + 1][y] == 0) {
                dfs(x + 1, y, 1);
                if (y <= N - 2) {
                    if (arr[x + 1][y + 1] == 0 && arr[x][y + 1] == 0) {
                        dfs(x + 1, y + 1, 2);
                    }
                }
            }
        }
    }
}
