import java.io.*;
import java.util.*;

public class Main {
    final static int BOARD_SIZE = 4;
    final static int FISHES = BOARD_SIZE * BOARD_SIZE;
    final static int DIRECTIONS = 8;
    static int[] dx = { -1, -1, 0, 1, 1, 1, 0, -1 };
    static int[] dy = { 0, -1, -1, -1, 0, 1, 1, 1 };

    static Fish[] fishes = new Fish[FISHES + 1];
    static boolean[] blooded = new boolean[FISHES + 1];
    static int sum, max;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        for (int i = 0; i <= FISHES; i++) {
            fishes[i] = new Fish(0, 0, 0);
        }

        for (int i = 0; i < BOARD_SIZE; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < BOARD_SIZE; j++) {
                int num = Integer.parseInt(st.nextToken());
                int dir = Integer.parseInt(st.nextToken()) - 1;
                fishes[num] = new Fish(dir, i, j);

                if (i == 0 && j == 0) {
                    blooded[num] = true; // 먹힌 고기의 번호 처리
                    fishes[0].dir = dir; // 상어는 0번째 인덱스로 고정, 방향 설정
                    sum = num;
                }
            }
        }

        dfs();

        System.out.println(max);
    }

    public static void dfs() {
        // 물고기 이동
        for (int num = 1; num <= FISHES; num++) {
            if (blooded[num])
                continue;
            fishMoving(num);

            // System.out.println(num);
            // printBoard();
        }

        boolean isBlooding = false;

        // 상어 이동
        for (int i = 0; i < BOARD_SIZE; i++) {
            int nx = fishes[0].x + dx[fishes[0].dir] * i;
            int ny = fishes[0].y + dy[fishes[0].dir] * i;

            if (inBounds(nx, ny)) {
                int bloodFishNum = getFishNum(nx, ny);
                if (bloodFishNum == -1)
                    continue;
                isBlooding = true;

                Fish[] copy = new Fish[FISHES + 1];
                for (int k = 0; k <= FISHES; k++) {
                    Fish f = fishes[k];
                    copy[k] = new Fish(f.dir, f.x, f.y);
                }

                fishes[0] = new Fish(fishes[bloodFishNum].dir, nx, ny);
                blooded[bloodFishNum] = true;
                sum += bloodFishNum;

                dfs();

                for (int k = 0; k <= FISHES; k++) {
                    fishes[k] = new Fish(copy[k].dir, copy[k].x, copy[k].y);
                }

                blooded[bloodFishNum] = false;
                sum -= bloodFishNum;
            }
        }

        if (!isBlooding) {
            max = Math.max(max, sum);
            return;
        }
    }

    public static void fishMoving(int currentNum) {
        int currentDir = fishes[currentNum].dir;
        for (int i = currentDir; i < currentDir + DIRECTIONS; i++) {
            int dir = i % DIRECTIONS;
            int cx = fishes[currentNum].x;
            int cy = fishes[currentNum].y;
            int nx = cx + dx[dir];
            int ny = cy + dy[dir];

            if (inBounds(nx, ny) && !checkShark(nx, ny)) {
                int nextNum = getFishNum(nx, ny);
                if (nextNum != -1) {
                    fishes[nextNum].x = cx;
                    fishes[nextNum].y = cy;
                }
                fishes[currentNum] = new Fish(dir, nx, ny);

                break;
            }
        }
    }

    public static boolean inBounds(int x, int y) {
        if (x >= 0 && x < BOARD_SIZE && y >= 0 && y < BOARD_SIZE)
            return true;
        return false;
    }

    public static boolean checkShark(int x, int y) {
        if (fishes[0].x == x && fishes[0].y == y)
            return true;
        return false;
    }

    public static int getFishNum(int x, int y) {
        for (int num = 1; num <= FISHES; num++) {
            if (blooded[num])
                continue;
            if (fishes[num].x == x && fishes[num].y == y) {
                return num;
            }
        }

        return -1;
    }

    public static void printBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.print(getFishNum(i, j) + " ");
            }
            System.out.println();
        }
        System.out.println("--------------------------");
    }

    static class Fish {
        int dir, x, y;

        public Fish(int dir, int x, int y) {
            this.dir = dir;
            this.x = x;
            this.y = y;
        }
    }
}