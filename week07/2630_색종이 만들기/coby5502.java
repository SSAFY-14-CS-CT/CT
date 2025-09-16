import java.io.*;
import java.util.*;

/**
 * BOJ 2630 - 색종이 만들기
 * - 크기 N(2^k)의 정사각 보드에서 하얀(0), 파란(1) 종이 수를 구하는 문제
 * - 현재 영역이 단색인지 검사 → 단색이면 해당 색 카운트 증가 → 종료
 * - 단색이 아니면 4분할하여 재귀 탐색
 * - 출력: 하얀 종이 수, 파란 종이 수 (각각 한 줄)
 */
public class Main {
    static int N;
    static int[][] board;
    static int whiteCount = 0;
    static int blueCount = 0;

    public static void main(String[] args) throws Exception {
        // ---------- 입력 ----------
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine().trim());
        board = new int[N][N];

        for (int r = 0; r < N; r++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int c = 0; c < N; c++) {
                board[r][c] = Integer.parseInt(st.nextToken()); // 0:흰, 1:파랑
            }
        }

        // ---------- 분할 정복 ----------
        cut(0, 0, N);

        // ---------- 출력 ----------
        System.out.println(whiteCount);
        System.out.println(blueCount);
    }

    /**
     * (sr, sc)를 왼쪽 위 꼭짓점으로 하고 한 변 길이가 size인 정사각형을 처리.
     * - 단색이면 카운트 증가 후 종료
     * - 아니면 4등분하여 재귀
     */
    static void cut(int sr, int sc, int size) {
        if (isMonochrome(sr, sc, size)) {
            if (board[sr][sc] == 0)
                whiteCount++;
            else
                blueCount++;
            return;
        }

        int half = size / 2;
        // 좌상
        cut(sr, sc, half);
        // 우상
        cut(sr, sc + half, half);
        // 좌하
        cut(sr + half, sc, half);
        // 우하
        cut(sr + half, sc + half, half);
    }

    /**
     * 현재 영역이 모두 같은 색(단색)인지 검사
     * 같으면 true, 아니면 false
     */
    static boolean isMonochrome(int sr, int sc, int size) {
        int color = board[sr][sc];
        for (int r = sr; r < sr + size; r++) {
            for (int c = sc; c < sc + size; c++) {
                if (board[r][c] != color)
                    return false;
            }
        }
        return true;
    }
}