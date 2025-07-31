import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {

    static int N, M, D;
    static int[][] map;
    static int[][] copyMap;
    static boolean[][] visited;
    static int[] archers;
    static int ans = 0;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        D = Integer.parseInt(st.nextToken());
        map = new int[N][M];
        copyMap = new int[N][M];
        visited = new boolean[N][M];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                copyMap[i][j] = map[i][j];
            }
        }

        MakeArchers();

        bw.write(ans + "\n");
        bw.flush();
        bw.close();
        br.close();
    }

    public static void RestoreMap() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                map[i][j] = copyMap[i][j];
            }
        }
    }

    public static void RestoreVisited() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                visited[i][j] = false;
            }
        }
    }

    public static int GetDistance(int r1, int c1, int r2, int c2) {
        return Math.abs(r1-r2) + Math.abs(c1-c2);
    }

    public static void MakeArchers() {
        for (int i = 0; i < M-2; i++) {
            for (int j = i+1; j < M-1; j++) {
                for (int k = j+1; k < M; k++) {
                    int[] newArchers = {i, j, k};
                    archers = newArchers;
                
                    RestoreMap();
                    int cnt = Fight(archers);

                    if (ans < cnt) {
                        ans = cnt;
                    }
                }
            }
        }
    }

    public static int Fight(int[] archers) {
        int cnt = 0;

        for(int play = 0; play < N; play++) {
            RestoreVisited();

            for(int archer: archers) {
                int minD = Integer.MAX_VALUE;
                int minY = Integer.MAX_VALUE;
                int minX = Integer.MAX_VALUE;
                
                for(int i = N-1; i >= 0; i--) {
                    for(int j = 0; j < M; j++) {
                        if(map[i][j] == 1) {
                            int distance = GetDistance(j, i, archer, N);

                            if (j == archer && distance > D) break;
                            if (distance <= D) {
                                if (minD > distance) {
                                    minD = distance;
                                    minY = i;
                                    minX = j;
                                } else if (minD == distance && minX > j) {
                                    minY = i;
                                    minX = j;
                                }
                            }
                        }
                    }
                }

                if (minD < Integer.MAX_VALUE) {
                    visited[minY][minX] = true;
                }
            }

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    if (visited[i][j]) {
                        map[i][j] = 0;
                        cnt++;
                    }
                }
            }

            Wave();
        }

        return cnt;
    }

    public static void Wave() {
        for(int i = N-1; i > 0; i--) {
            for(int j = 0; j < M; j++) {
                map[i][j] = map[i-1][j];
            }
        }

        for(int i = 0; i < M; i++) {
            map[0][i] = 0;
        }
    }
}