import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static int n;
    static int[][] board;
    static int maxBlock = 0;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력 받기
        n = Integer.parseInt(br.readLine());
        board = new int[n][n];

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        dfs(0);  // 최대 5번 이동
        System.out.println(maxBlock);
    }

    // 백트래킹 (DFS)
    public static void dfs(int depth) {
        if (depth == 5) {
            updateMax();
            return;
        }

        int[][] backup = copyBoard();

        for (int dir = 0; dir < 4; dir++) {
            move(dir);
            dfs(depth + 1);
            restoreBoard(backup);
        }
    }

    // 현재 보드 상태의 가장 큰 값 갱신
    public static void updateMax() {
        for (int[] row : board) {
            for (int val : row) {
                maxBlock = Math.max(maxBlock, val);
            }
        }
    }

    // 보드 복사
    public static int[][] copyBoard() {
        int[][] newBoard = new int[n][n];
        for (int i = 0; i < n; i++) {
            newBoard[i] = board[i].clone();
        }
        return newBoard;
    }

    // 보드 복구
    public static void restoreBoard(int[][] backup) {
        for (int i = 0; i < n; i++) {
            board[i] = backup[i].clone();
        }
    }

    // 블록 이동 및 합치기
    public static void move(int dir) {
        switch (dir) {
            case 0: // 상
                for (int col = 0; col < n; col++) {
                    int idx = 0, last = 0;
                    for (int row = 0; row < n; row++) {
                        if (board[row][col] == 0) continue;

                        int cur = board[row][col];
                        board[row][col] = 0;

                        if (last == cur) {
                            board[idx - 1][col] *= 2;
                            last = 0;
                        } else {
                            last = cur;
                            board[idx++][col] = cur;
                        }
                    }
                }
                break;
            case 1: // 하
                for (int col = 0; col < n; col++) {
                    int idx = n - 1, last = 0;
                    for (int row = n - 1; row >= 0; row--) {
                        if (board[row][col] == 0) continue;

                        int cur = board[row][col];
                        board[row][col] = 0;

                        if (last == cur) {
                            board[idx + 1][col] *= 2;
                            last = 0;
                        } else {
                            last = cur;
                            board[idx--][col] = cur;
                        }
                    }
                }
                break;
            case 2: // 좌
                for (int row = 0; row < n; row++) {
                    int idx = 0, last = 0;
                    for (int col = 0; col < n; col++) {
                        if (board[row][col] == 0) continue;

                        int cur = board[row][col];
                        board[row][col] = 0;

                        if (last == cur) {
                            board[row][idx - 1] *= 2;
                            last = 0;
                        } else {
                            last = cur;
                            board[row][idx++] = cur;
                        }
                    }
                }
                break;
            case 3: // 우
                for (int row = 0; row < n; row++) {
                    int idx = n - 1, last = 0;
                    for (int col = n - 1; col >= 0; col--) {
                        if (board[row][col] == 0) continue;

                        int cur = board[row][col];
                        board[row][col] = 0;

                        if (last == cur) {
                            board[row][idx + 1] *= 2;
                            last = 0;
                        } else {
                            last = cur;
                            board[row][idx--] = cur;
                        }
                    }
                }
                break;
        }
    }
}