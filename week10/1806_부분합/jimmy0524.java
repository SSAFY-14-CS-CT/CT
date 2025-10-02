import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int S = Integer.parseInt(st.nextToken());
        int[] arr = new int[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        int left = 0;
        int right = 0;
        int sum = arr[0];
        int answer = Integer.MAX_VALUE;
        while (left < N) {
            if (sum < S) {
                right++;
                if (right == N) break;
                sum += arr[right];
            } else {
                answer = Math.min(answer, right - left + 1);
                sum -= arr[left++];
            }
        }

        if (answer == Integer.MAX_VALUE) answer = 0;
        System.out.println(answer);
    }
}
