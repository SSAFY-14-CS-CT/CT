import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {

    final static int N = 10;
    final static int SIZES = 5;
    static int[][] map = new int[N][N];
    static int[] papers = {0, 0, 0, 0, 0, 0};
    static int ans = Integer.MAX_VALUE;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        DFS(0, 0, 0);

        if (ans == Integer.MAX_VALUE) ans = -1;

        bw.write(ans + "\n");
        bw.flush();
        bw.close();
        br.close();
    }

    public static void DFS(int y, int x, int cnt) {
        if (ans <= cnt) {
            return;
        }

        if (x == N) {
            DFS(y+1, 0, cnt);
            return;
        }

        if (y == N) {
            if (ans > cnt) ans = cnt;
            return;
        }

        if (map[y][x] == 0) {
            DFS(y, x+1, cnt);
            return;
        }

        for(int size = 1; size <= SIZES; size++) {
            if (papers[size] == 5) continue;
            if (check(y, x, size)) {
                papers[size]++;
                fill(y, x, size, 0);

                DFS(y, x, cnt+1);

                papers[size]--;
                fill(y, x, size, 1);
            }
        }

        return;
    }

    public static boolean check(int y, int x, int size) {
        if(y+size > N || x+size > N) return false;

        for(int i = y; i < y+size; i++) {
            for (int j = x; j < x+size; j++) {
                if (map[i][j] == 0) return false;
            }
        }

        return true;
    }

    public static void fill(int y, int x, int size, int value) {
        for(int i = y; i < y+size; i++) {
            for (int j = x; j < x+size; j++) {
                map[i][j] = value;
            }
        }
    }
}