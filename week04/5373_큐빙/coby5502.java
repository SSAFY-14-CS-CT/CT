import java.io.*;
import java.util.*;

public class Main {

    // Face indices
    static final int U = 0, D = 1, F = 2, B = 3, L = 4, R = 5;

    // cube[face][row][col]
    static char[][][] cube = new char[6][3][3];

    // Default colors by face order: U, D, F, B, L, R
    static final char[] COLORS = { 'w', 'y', 'r', 'o', 'g', 'b' };

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine().trim());

        while (T-- > 0) {
            initCube();

            int N = Integer.parseInt(br.readLine().trim());
            StringTokenizer st = new StringTokenizer(br.readLine());

            while (st.hasMoreTokens()) {
                applyTurn(st.nextToken());
            }

            printUp(); // 문제 요구: 윗면 출력(너의 기존 좌표계 출력 형식 유지)
        }
    }

    /* --------------------------- I/O helpers --------------------------- */

    // 너의 기존 출력 형식을 보존: cube[U][j][2 - i]
    private static void printUp() {
        StringBuilder sb = new StringBuilder(12);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) sb.append(cube[U][j][2 - i]);
            sb.append('\n');
        }
        System.out.print(sb);
    }

    /* --------------------------- Cube setup --------------------------- */

    private static void initCube() {
        for (int f = 0; f < 6; f++) {
            for (int r = 0; r < 3; r++) {
                Arrays.fill(cube[f][r], COLORS[f]);
            }
        }
    }

    /* ---------------------------- Turning ----------------------------- */

    private static void applyTurn(String cmd) {
        char faceChar = cmd.charAt(0);
        boolean clockwise = (cmd.charAt(1) == '+');

        switch (faceChar) {
            case 'U': alter(U, L, F, R, B, clockwise); break;
            case 'D': alter(D, B, R, F, L, clockwise); break;
            case 'F': alter(F, U, L, D, R, clockwise); break;
            case 'B': alter(B, R, D, L, U, clockwise); break;
            case 'L': alter(L, F, U, B, D, clockwise); break;
            case 'R': alter(R, D, B, U, F, clockwise); break;
        }
    }

    /**
     * 한 면(f)을 회전시키고, 그 면에 접하는 네 테두리(U/L/D/R 방향에 해당)를 순환시킨다.
     *
     * 인접 엣지 순환 규칙(네 좌표계 기준으로 고정):
     * - u: cube[u][i][0]              (i = 0..2, 위쪽 면의 왼쪽 세로줄)
     * - l: cube[l][0][2 - i]          (i = 0..2, 왼쪽 면의 위쪽 가로줄, 역방향)
     * - d: cube[d][2][i]              (i = 0..2, 아래쪽 면의 아래 가로줄)
     * - r: cube[r][2 - i][2]          (i = 0..2, 오른쪽 면의 오른쪽 세로줄, 역방향)
     *
     * 위 규칙은 네가 작성한 코드의 좌표계/방향을 그대로 보존한다.
     */
    private static void alter(int f, int u, int l, int d, int r, boolean clockwise) {
        // 1) 대상 면 회전
        rotateFace(f, clockwise);

        // 2) 인접 엣지 순환
        char[] buf = new char[3]; // 임시 버퍼(백업)

        if (clockwise) {
            // 백업: u의 세로줄(왼쪽 컬럼)
            for (int i = 0; i < 3; i++) buf[i] = cube[u][i][0];

            // l의 가로줄(위쪽, 역방향) -> u의 세로줄
            for (int i = 0; i < 3; i++) cube[u][i][0] = cube[l][0][2 - i];

            // d의 가로줄(아래쪽) -> l의 가로줄(위쪽, 역방향)
            for (int i = 0; i < 3; i++) cube[l][0][2 - i] = cube[d][2][i];

            // r의 세로줄(오른쪽, 역방향) -> d의 가로줄(아래쪽)
            for (int i = 0; i < 3; i++) cube[d][2][i] = cube[r][2 - i][2];

            // (백업분) u -> r의 세로줄(오른쪽, 역방향 위치에 맞춰)
            for (int i = 0; i < 3; i++) cube[r][2 - i][2] = buf[i];

        } else {
            // 반시계: 순환 방향 반대로
            for (int i = 0; i < 3; i++) buf[i] = cube[u][i][0];

            // r -> u
            for (int i = 0; i < 3; i++) cube[u][i][0] = cube[r][2 - i][2];

            // d -> r
            for (int i = 0; i < 3; i++) cube[r][2 - i][2] = cube[d][2][i];

            // l -> d
            for (int i = 0; i < 3; i++) cube[d][2][i] = cube[l][0][2 - i];

            // u(백업) -> l
            for (int i = 0; i < 3; i++) cube[l][0][2 - i] = buf[i];
        }
    }

    /**
     * 3x3 면을 시계/반시계로 회전.
     * dst[j][2 - i] = src[i][j]  (시계)
     * dst[2 - j][i] = src[i][j]  (반시계)
     */
    private static void rotateFace(int f, boolean clockwise) {
        char[][] src = cube[f];
        char[][] dst = new char[3][3];

        if (clockwise) {
            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++)
                    dst[j][2 - i] = src[i][j];
        } else {
            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++)
                    dst[2 - j][i] = src[i][j];
        }
        cube[f] = dst; // 교체(깊은 반영)
    }
}