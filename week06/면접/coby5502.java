import java.io.*;
import java.util.*;

public class Main {

    static int T, N, M, K;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        T = Integer.parseInt(br.readLine().trim());
        for (int tc = 1; tc <= T; tc++) {
            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken()); // 문제 수
            M = Integer.parseInt(st.nextToken()); // 맞춘 개수
            K = Integer.parseInt(st.nextToken()); // 연속 K일 때 트리거

            sb.append('#').append(tc).append(' ').append(solve(N, M, K)).append('\n');
        }
        System.out.print(sb.toString());
    }

    static int solve(int N, int M, int K) {
        int wrong = N - M; // 오답 수
        int segments = wrong + 1; // 정답 덩어리(구간) 수
        int capacity = segments * (K - 1); // 트리거 없이 담을 수 있는 최대 정답 수

        // 필요한 최소 트리거 개수 t = ceil(excess / K)
        int t = 0;
        if (M > capacity) {
            int excess = M - capacity;
            t = (excess + K - 1) / K;
        }

        // 트리거 t번을 앞쪽에 몰아 최솟값 만들기
        int score = 0;
        for (int i = 0; i < t; i++) {
            score += (K - 1); // K-1개 정답: +1씩
            score = (score + 1) << 1; // K번째 정답: +1 후 전체 2배
        }

        // 남은 정답(트리거 없이): +1씩
        score += (M - t * K);

        return score;
    }
}