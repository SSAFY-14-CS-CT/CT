package week06.전기자동차충전소;

import java.io.*;
import java.util.*;

public class MayoneJY {
    static class House {
        int x, y, d;
        House(int x, int y, int d) {
            this.x = x; this.y = y; this.d = d;
        }
    }

    static int N;
    static List<House> houses;
    static int minAnswer;
    static boolean[][] houseMap = new boolean[31][31]; // 좌표 보정: -15 ~ 15 → 0 ~ 30

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            N = Integer.parseInt(br.readLine());
            houses = new ArrayList<>();
            houseMap = new boolean[31][31];

            for (int i = 0; i < N; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());
                int d = Integer.parseInt(st.nextToken());
                houses.add(new House(x, y, d));
                houseMap[x + 15][y + 15] = true; // 집 위치 표시
            }

            minAnswer = Integer.MAX_VALUE;

            // 1개 충전소 배치
            for (int i = -15; i <= 15; i++) {
                for (int j = -15; j <= 15; j++) {
                    if (houseMap[i + 15][j + 15]) continue; // 집 위에는 설치 불가
                    if (canServeAll(i, j)) {
                        minAnswer = Math.min(minAnswer, calcTotal(i, j));
                    }
                }
            }

            // 2개 충전소 배치
            for (int i1 = -15; i1 <= 15; i1++) {
                for (int j1 = -15; j1 <= 15; j1++) {
                    if (houseMap[i1 + 15][j1 + 15]) continue;
                    for (int i2 = i1; i2 <= 15; i2++) {
                        for (int j2 = -15; j2 <= 15; j2++) {
                            if (i1 == i2 && j2 <= j1) continue; // 중복 방지
                            if (houseMap[i2 + 15][j2 + 15]) continue;

                            if (canServeAll(i1, j1, i2, j2)) {
                                minAnswer = Math.min(minAnswer, calcTotal(i1, j1, i2, j2));
                            }
                        }
                    }
                }
            }

            if (minAnswer == Integer.MAX_VALUE) minAnswer = -1;
            sb.append("#").append(t).append(" ").append(minAnswer).append("\n");
        }

        System.out.print(sb);
    }

    // 모든 집이 해당 충전소에 의해 커버 가능한지 확인 (1개 충전소)
    static boolean canServeAll(int cx, int cy) {
        for (House h : houses) {
            int dist = Math.abs(h.x - cx) + Math.abs(h.y - cy);
            if (dist > h.d) return false;
        }
        return true;
    }

    // 모든 집이 2개의 충전소 중 가까운 곳에서 커버 가능한지 확인
    static boolean canServeAll(int cx1, int cy1, int cx2, int cy2) {
        for (House h : houses) {
            int dist1 = Math.abs(h.x - cx1) + Math.abs(h.y - cy1);
            int dist2 = Math.abs(h.x - cx2) + Math.abs(h.y - cy2);
            int dist = Math.min(dist1, dist2);
            if (dist > h.d) return false;
        }
        return true;
    }

    // 거리 합 계산 (1개 충전소)
    static int calcTotal(int cx, int cy) {
        int sum = 0;
        for (House h : houses) {
            sum += Math.abs(h.x - cx) + Math.abs(h.y - cy);
        }
        return sum;
    }

    // 거리 합 계산 (2개 충전소)
    static int calcTotal(int cx1, int cy1, int cx2, int cy2) {
        int sum = 0;
        for (House h : houses) {
            int dist1 = Math.abs(h.x - cx1) + Math.abs(h.y - cy1);
            int dist2 = Math.abs(h.x - cx2) + Math.abs(h.y - cy2);
            sum += Math.min(dist1, dist2);
        }
        return sum;
    }
}
