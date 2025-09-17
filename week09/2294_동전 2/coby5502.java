import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static int n, k;
    static Set<Integer> coinSet = new HashSet<>();
    static final int INF = 1_000_000_000;
    // DP 배열: dp[x] == x원을 만드는 최소 동전 개수
    static int[] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        for (int i = 0; i < n; i++) {
            int c = Integer.parseInt(br.readLine());
            if (c <= k)
                coinSet.add(c);
        }

        if (coinSet.isEmpty()) {
            System.out.println(-1);
            return;
        }

        dp = new int[k + 1];
        Arrays.fill(dp, INF);
        dp[0] = 0;

        for (int c : coinSet) {
            for (int x = c; x <= k; x++) {
                if (dp[x - c] != INF) {
                    dp[x] = Math.min(dp[x], dp[x - c] + 1);
                }
            }
        }

        System.out.println(dp[k] == INF ? -1 : dp[k]);
    }
}