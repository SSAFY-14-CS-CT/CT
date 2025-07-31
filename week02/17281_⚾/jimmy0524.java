import java.util.*;
import java.io.*;

public class Main {
    static int N;
    static int[][] arr;
    static int answer;
    static int[] order;
    static boolean[] visited;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        arr = new int[N + 1][10];
        for (int i = 1; i <= N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= 9; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        order = new int[10];
        visited = new boolean[10];
        dfs(1);
        System.out.println(answer);
    }

    //순서 정하기
    public static void dfs(int index) {
        if (index == 10) {
            simulate();
            return;
        }

        if (index == 4) {
            order[4] = 1;
            visited[1] = true;
            dfs(index + 1);
            return;
        }

        for (int i = 2; i <= 9; i++) {
            if (!visited[i]) {
                order[index] = i;
                visited[i] = true;
                dfs(index + 1);
                visited[i] = false;
            }
        }
    }

    public static void simulate() {
        int score = 0;
        int playerIndex = 1;

        for (int i = 1; i <= N; i++) {
            int out = 0;
            boolean[] base = new boolean[4];

            while(out < 3) {
                int batter = order[playerIndex];
                int value = arr[i][batter];
                switch(value) {
                    case 0 :
                        out++;
                        break;
                    case 1 :
                        if (base[3]) {
                            score++;
                        }
                        base[3] = base[2];
                        base[2] = base[1];
                        base[1] = true;
                        break;
                    case 2 :
                        if (base[3]) {
                            score++;
                        }
                        if (base[2]) {
                            score++;
                        }
                        base[3] = base[1];
                        base[2] = true;
                        base[1] = false;
                        break;
                    case 3 :
                        for (int j = 1; j <= 3; j++) {
                            if (base[j]) {
                                score++;
                            }
                        }
                        base[3] = true;
                        base[1] = base[2] = false;
                        break;
                    case 4 :
                        for (int j = 1; j <= 3; j++) {
                            if (base[j]) {
                                score++;
                            }
                        }
                        score++;
                        base[1] = base[2] = base[3] = false;
                        break;
                }

                playerIndex++;
                if (playerIndex == 10) {
                    playerIndex = 1;
                }
            }
        }
        answer = Math.max(answer, score);
    }
}
