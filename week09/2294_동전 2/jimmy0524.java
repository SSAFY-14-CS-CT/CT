import java.util.*;
import java.io.*;

public class boj_2294 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int[] coin = new int[n];
        int[] dp = new int[k + 1]; //동전의 최소 개수
        for (int i = 0; i < n; i++) {
            coin[i] = Integer.parseInt(br.readLine());
        }

        int INF = 999999999;

        Arrays.fill(dp, INF);

        dp[0] = 0;

        for (int i = 0; i < n; i++) {
            int c = coin[i];
            if (c > k) continue;
            for (int j = c; j <= k; j++) {
                dp[j] = Math.min(dp[j - c] + 1, dp[j]);
            }
        }

        if (dp[k] == INF) {
            System.out.println(-1);
        } else {
            System.out.println(dp[k]);
        }
    }
}
