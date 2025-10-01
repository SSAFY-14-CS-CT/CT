import java.io.*;
import java.util.*;

public class MayoneJY {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N, K;
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        int[] map = new int[N];
        for(int i = 0; i < N; i++){
            map[i] = Integer.parseInt(br.readLine());
        }
        
        int[] dp = new int[K+1];
        int INF = 100001;
        Arrays.fill(dp, INF);
        dp[0] = 0;
        for(int coin : map){
            if(coin > K) continue;
            for(int j = coin; j <= K; j++){
                dp[j] = Math.min(dp[j-coin] + 1, dp[j]);
            }
        }

        System.out.println(dp[K] == INF ? -1 : dp[K]);
    }
}
