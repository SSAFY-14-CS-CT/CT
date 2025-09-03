import java.io.*;
import java.util.*;

public class Main {

    static int T, N;
    static int[] hx, hy, hd; // 집 좌표와 허용 거리
    static boolean[][] isHouse; // 집이 있는 좌표 금지
    static int[] cx, cy; // 후보 충전소 좌표들
    static int C; // 후보 개수
    static int[][] dist; // dist[c][h] = 후보 c에서 집 h까지 맨해튼 거리
    static int[] coverMask; // coverMask[c] = 후보 c가 커버하는 집 비트마스크
    static final int SHIFT = 15; // [-15..15] -> [0..30]
    static final int INF = 1_000_000_000;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        T = Integer.parseInt(br.readLine().trim());
        for (int tc = 1; tc <= T; tc++) {
            N = Integer.parseInt(br.readLine().trim());
            hx = new int[N];
            hy = new int[N];
            hd = new int[N];
            isHouse = new boolean[31][31];

            for (int i = 0; i < N; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                hx[i] = Integer.parseInt(st.nextToken());
                hy[i] = Integer.parseInt(st.nextToken());
                hd[i] = Integer.parseInt(st.nextToken());
                isHouse[hx[i] + SHIFT][hy[i] + SHIFT] = true; // 집 위치엔 설치 불가
            }

            buildCandidates();
            precompute();

            int all = (1 << N) - 1;

            // 1개 충전소로 가능한 최소 합
            int best1 = INF;
            for (int c = 0; c < C; c++) {
                if (coverMask[c] == all) {
                    int sum = 0;
                    for (int h = 0; h < N; h++)
                        sum += dist[c][h];
                    if (sum < best1)
                        best1 = sum;
                }
            }

            int answer;
            if (best1 != INF) {
                answer = best1; // 1개로 되면 반드시 1개만 사용
            } else {
                // 2개 충전소
                int best2 = INF;
                for (int i = 0; i < C; i++) {
                    for (int j = i + 1; j < C; j++) {
                        if ((coverMask[i] | coverMask[j]) != all)
                            continue; // 전체 커버 불가
                        int sum = 0;
                        for (int h = 0; h < N; h++) {
                            int d = dist[i][h];
                            int e = dist[j][h];
                            sum += (d < e ? d : e);
                        }
                        if (sum < best2)
                            best2 = sum;
                    }
                }
                answer = (best2 == INF) ? -1 : best2;
            }

            sb.append('#').append(tc).append(' ').append(answer).append('\n');
        }

        System.out.print(sb.toString());
    }

    // 후보 좌표 수집: [-15..15] 격자 중 집이 있는 칸 제외
    static void buildCandidates() {
        cx = new int[961];
        cy = new int[961];
        C = 0;
        for (int x = -15; x <= 15; x++) {
            for (int y = -15; y <= 15; y++) {
                if (!isHouse[x + SHIFT][y + SHIFT]) {
                    cx[C] = x;
                    cy[C] = y;
                    C++;
                }
            }
        }
    }

    // 거리/커버 마스크 미리 계산
    static void precompute() {
        dist = new int[C][N];
        coverMask = new int[C];
        for (int c = 0; c < C; c++) {
            int mask = 0;
            for (int h = 0; h < N; h++) {
                int d = Math.abs(hx[h] - cx[c]) + Math.abs(hy[h] - cy[c]);
                dist[c][h] = d;
                if (d <= hd[h])
                    mask |= (1 << h);
            }
            coverMask[c] = mask;
        }
    }
}
