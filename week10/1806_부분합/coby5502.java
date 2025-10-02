import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        long S = Long.parseLong(st.nextToken());

        int[] a = new int[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            a[i] = Integer.parseInt(st.nextToken());
        }

        int ans = N + 1;
        int L = 0;
        long sum = 0;

        for (int R = 0; R < N; R++) {
            sum += a[R];
            while (sum >= S) {
                ans = Math.min(ans, R - L + 1);
                sum -= a[L++];
            }
        }

        System.out.println(ans == N + 1 ? 0 : ans);
    }
}