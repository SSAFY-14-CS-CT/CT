import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder out = new StringBuilder();

        int T = Integer.parseInt(br.readLine().trim());
        int[] ns = new int[T];
        int maxN = 0;

        for (int t = 0; t < T; t++) {
            ns[t] = Integer.parseInt(br.readLine().trim());
            if (ns[t] > maxN) {
                maxN = ns[t];
            }
        }

        int[] dp = new int[maxN + 1];
        dp[0] = 1;

        int[] coins = { 1, 2, 3 };
        for (int c : coins) {
            for (int i = c; i <= maxN; i++) {
                dp[i] += dp[i - c];
            }
        }

        for (int n : ns) {
            out.append(dp[n]).append('\n');
        }

        System.out.print(out);
    }
}