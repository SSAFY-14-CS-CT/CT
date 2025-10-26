import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken()); // 접시 수
        int d = Integer.parseInt(st.nextToken()); // 초밥 종류 수 (1..d)
        int k = Integer.parseInt(st.nextToken()); // 연속해서 먹는 접시 수
        int c = Integer.parseInt(st.nextToken()); // 쿠폰 초밥 종류

        int[] belt = new int[N];
        for (int i = 0; i < N; i++) belt[i] = Integer.parseInt(br.readLine().trim());

        int[] cnt = new int[d + 1];
        int unique = 0;

        // 초기 창 [0..k-1]
        for (int i = 0; i < k; i++) {
            if (cnt[belt[i]]++ == 0) unique++;
        }

        int answer = unique + (cnt[c] == 0 ? 1 : 0);

        // 창을 한 칸씩 N-1번 이동 (원형이라 총 N개의 창을 본다)
        for (int i = 1; i < N; i++) {
            // 왼쪽에서 나가는 접시
            int out = belt[i - 1];
            if (--cnt[out] == 0) unique--;

            // 오른쪽에서 새로 들어오는 접시 (원형)
            int in = belt[(i + k - 1) % N];
            if (cnt[in]++ == 0) unique++;

            // 쿠폰 고려
            int cur = unique + (cnt[c] == 0 ? 1 : 0);
            if (cur > answer) answer = cur;

            // 최댓값이 k+1을 넘을 수는 없고, 이 값이 나오면 더 볼 필요 없이 최댓값
            // (하지만 조기 종료는 선택사항이므로 생략)
        }

        System.out.println(answer);
    }
}