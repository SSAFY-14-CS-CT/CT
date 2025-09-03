import java.util.*;
import java.io.*;

public class problem1 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        for (int t = 1; t <= T; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());
            int K = Integer.parseInt(st.nextToken());
            int[][][] dp = new int[N + 1][M + 1][K + 1];
            for (int i = 0; i <= N; i++) {
                for (int j = 0; j <= M; j++) {
                    Arrays.fill(dp[i][j], Integer.MAX_VALUE);
                }
            }
            dp[0][0][0] = 0;
            // i: 지금 진행중인 문제 index
            // j: 지금까지 맞은 문제 수
            // k: count (K에 도달하면 0)
            for (int i = 0; i < N; i++) {
                for (int j = 0; j <= M; j++) {
                    for (int c = 0; c < K; c++) {
                        if (dp[i][j][c] == Integer.MAX_VALUE) {
                            continue;
                        }
                        // 선택할 때
                        if (j + 1 <= M) {
                            if (c + 1 == K) {
                                dp[i + 1][j + 1][0] = Math.min(
                                        dp[i + 1][j + 1][0], (dp[i][j][c] + 1) * 2);
                            } else {
                                dp[i + 1][j + 1][c + 1] = Math.min(dp[i + 1][j + 1][c + 1], dp[i][j][c] + 1);
                            }
                        }
                        // 선택 안할때
                        dp[i + 1][j][0] = Math.min(dp[i + 1][j][0], dp[i][j][c]);
                    }
                }
            }
            int answer = Integer.MAX_VALUE;
            for (int c = 0; c <= K - 1; c++) {
                answer = Math.min(answer, dp[N][M][c]);
            }
            System.out.println("#" + t + " " + answer);
        }
    }
}
