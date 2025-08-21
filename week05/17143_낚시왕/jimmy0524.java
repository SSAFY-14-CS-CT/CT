import java.util.*;
import java.io.*;

public class Main {
    static int[] dr = {0, -1, 1, 0, 0};
    static int[] dc = {0, 0, 0, 1, -1};
    static int answer;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int R = Integer.parseInt(st.nextToken());
        int C = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        Shark[] sharks = new Shark[M];
        int[][] board = new int[R + 1][C + 1];
        for (int i = 0; i <= R; i++) {
            Arrays.fill(board[i], -1);
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            int s = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            int z = Integer.parseInt(st.nextToken());
            sharks[i] = new Shark(r, c, s, d, z);
            board[r][c] = i;
        }

        for (int c = 1; c <= C; c++) {
            // 상어 낚시
            for (int r = 1; r <= R; r++) {
                if (board[r][c] != -1) {
                    int index = board[r][c];
                    board[r][c] = -1;
                    answer += sharks[index].z;
                    break;
                }
            }

            // 상어 이동
            int[][] newBoard = new int[R + 1][C + 1];
            for (int i = 0; i <= R; i++) {
                Arrays.fill(newBoard[i], -1);
            }

            for (int r = 1; r <= R; r++) {
                for (int c2 = 1; c2 <= C; c2++) {
                    if (board[r][c2] != -1) {
                        int nowIndex = board[r][c2];
                        Shark nowShark = sharks[nowIndex];

                        int nextR = nowShark.r;
                        int nextC = nowShark.c;
                        int nowDir = nowShark.d;

                        int move = nowShark.s;
                        if (nowDir <= 2) {
                            move %= (R - 1) * 2;
                        } else {
                            move %= (C - 1) * 2;
                        }

                        for (int i = 0; i < move; i++) {
                            nextR += dr[nowDir];
                            nextC += dc[nowDir];

                            switch (nowDir) {
                                case 1:
                                    if (nextR == 0) {
                                        nextR = 2;
                                        nowDir = 2;
                                    }
                                    break;
                                case 2:
                                    if (nextR == R + 1) {
                                        nextR = R - 1;
                                        nowDir = 1;
                                    }
                                    break;
                                case 3:
                                    if (nextC == C + 1) {
                                        nextC = C - 1;
                                        nowDir = 4;
                                    }
                                    break;
                                case 4:
                                    if (nextC == 0) {
                                        nextC = 2;
                                        nowDir = 3;
                                    }
                                    break;
                            }
                        }

                        nowShark.r = nextR;
                        nowShark.c = nextC;
                        nowShark.d = nowDir;

                        if (newBoard[nextR][nextC] != -1) {
                            int existIndex = newBoard[nextR][nextC];
                            if (sharks[existIndex].z < nowShark.z) {
                                newBoard[nextR][nextC] = nowIndex;
                            }
                        } else {
                            newBoard[nextR][nextC] = nowIndex;
                        }
                    }
                }
            }
            board = newBoard;
        }
        System.out.println(answer);
    }

    static class Shark {
        int r, c, s, d, z;

        Shark(int r, int c, int s, int d, int z) {
            this.r = r;
            this.c = c;
            this.s = s;
            this.d = d;
            this.z = z;
        }
    }
}
