import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static int[][] map;
    static int ans = 0;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        map = new int[N+1][N+1];
        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        DFS(1, 2, 0);

        bw.write(ans + "\n");
        bw.flush();
        bw.close();
        br.close();
    }

    // direction: 가로(0), 세로(1), 대각선(2)
    public static void DFS(int y, int x, int direction) {
        if (x == N && y == N) {
            ans++;
            return;
        }

        // 가로 -> 동쪽, 대각선
        // 세로 -> 남쪽, 대각선
        // 대각선 -> 동쪽, 남쪽, 대각선
        switch (direction) {
        case 0: // 가로
            if (x+1 <= N && map[y][x+1] == 0) {
                DFS(y, x+1, 0); // 동쪽
            }
            break;
        case 1: // 세로
            if (y+1 <= N && map[y+1][x] == 0) {
                DFS(y+1, x, 1); // 남쪽
            }
            break;
        case 2: // 대각선
            if (x+1 <= N && map[y][x+1] == 0) {
                DFS(y, x+1, 0); // 동쪽
            }

            if (y+1 <= N && map[y+1][x] == 0) {
                DFS(y+1, x, 1); // 남쪽
            }
            break;
        }

        if (x+1 <= N && y+1 <= N && map[y][x+1] == 0 && map[y+1][x] == 0 && map[y+1][x+1] == 0) {
            DFS(y+1, x+1, 2); // 대각선
        }
    }
}